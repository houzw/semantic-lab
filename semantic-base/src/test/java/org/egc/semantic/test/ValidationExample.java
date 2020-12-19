package org.egc.semantic.test;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.util.FileUtils;
import org.topbraid.jenax.util.JenaUtil;
import org.topbraid.shacl.util.ModelPrinter;
import org.topbraid.shacl.validation.ValidationUtil;

/**
 * Description:
 * <pre>
 * https://github.com/TopQuadrant/shacl/blob/master/src/test/java/org/topbraid/shacl/ValidationExample.java
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/5/18 16:43
 */
public class ValidationExample {
    void validateTest() {
        // Load the main data model
        Model dataModel = JenaUtil.createMemoryModel();
        dataModel.read(ValidationExample.class.getResourceAsStream("/sh/tests/core/property/class-001.test.ttl"),
                       "urn:dummy", FileUtils.langTurtle);

        // Perform the validation of everything, using the data model
        // also as the shapes model - you may have them separated
        Resource report = ValidationUtil.validateModel(dataModel, dataModel, true);

        // Print violations
        System.out.println(ModelPrinter.get().print(report.getModel()));
    }
}
