package org.egc.semantic.validation;

import org.apache.jena.graph.Graph;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.shacl.ShaclValidator;
import org.apache.jena.shacl.Shapes;
import org.apache.jena.shacl.ValidationReport;
import org.apache.jena.shacl.lib.ShLib;
import org.apache.jena.util.FileUtils;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.rules.RuleUtil;
import org.topbraid.shacl.util.ModelPrinter;
import org.topbraid.shacl.validation.ValidationUtil;
import org.topbraid.shacl.vocabulary.SH;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * https://jena.apache.org/documentation/shacl/index.html
 * https://github.com/TopQuadrant/shacl
 * https://dzone.com/articles/using-jena-and-shacl-to-validate-rdf-data
 *
 * @author houzhiwei
 * @date 2020/10/10 14:46
 */
public class ShaclValidationImpl implements ShaclValidation {

    @Override
    public ValidationReport validate(String shapesFile, String dataFile) {
        Graph shapesGraph = RDFDataMgr.loadGraph(shapesFile);
        Graph dataGraph = RDFDataMgr.loadGraph(dataFile);
        Shapes shapes = Shapes.parse(shapesGraph);
        return ShaclValidator.get().validate(shapes, dataGraph);
    }

    @Override
    public String report2Str(ValidationReport report) {
        ShLib.printReport(report);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        RDFDataMgr.write(baos, report.getModel(), Lang.TTL);
        String s = "";
        try {
            s = baos.toString(StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        System.out.println(s);
        return s;
    }

    @Override
    public String report2Str(Resource report) {
        // Print violations
        boolean conforms = report.getProperty(SH.conforms).getBoolean();
        String s = ModelPrinter.get().print(report.getModel());
        System.out.println("conforms:" + conforms);
        System.out.println(s);
        return s;
    }

    /**
     * Loads an example SHACL-AF (rules) file and execute it against the data.
     */
    public void validateWithRuleUseTopbraid(String dataGraph) throws Exception {
        FileInputStream is = org.apache.commons.io.FileUtils.openInputStream(new File(dataGraph));

        // Load the main data model that contains rule(s)
        Model dataModel = JenaUtil.createMemoryModel();
        dataModel.read(is, "urn:dummy", FileUtils.langTurtle);

        // Perform the rule calculation, using the data model
        // also as the rule model - you may have them separated
        Model result = RuleUtil.executeRules(dataModel, dataModel, null, null);

        // you may want to add the original data, to make sense of the rule results
        result.add(dataModel);

        // Print rule calculation results
        System.out.println(ModelPrinter.get().print(result));
    }

    @Override
    public Resource validateUseTopbraid(String shapesGraph, String dataGraph) throws IOException {
        // Load the main data model
        Model shapeModel = JenaUtil.createMemoryModel();
        Model dataModel = JenaUtil.createMemoryModel();
        FileInputStream is = org.apache.commons.io.FileUtils.openInputStream(new File(shapesGraph));
        FileInputStream dis = org.apache.commons.io.FileUtils.openInputStream(new File(dataGraph));
        shapeModel.read(is, "urn:dummy", FileUtils.langTurtle);
        dataModel.read(dis, "urn:dummy", FileUtils.langTurtle);
        // Perform the validation of everything, using the data model and shapes model
        return ValidationUtil.validateModel(dataModel, shapeModel, true);
    }

    @Override
    public Resource validateUseTopbraid(String dataGraph) throws IOException {
        // Load the main data model
        Model dataModel = JenaUtil.createMemoryModel();
        FileInputStream dis = org.apache.commons.io.FileUtils.openInputStream(new File(dataGraph));
        dataModel.read(dis, "urn:dummy", FileUtils.langTurtle);
        // Perform the validation of everything, using the data model and shapes model
        return ValidationUtil.validateModel(dataModel, dataModel, true);
    }
}
