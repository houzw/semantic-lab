package org.egc.semantic.validation;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shacl.ValidationReport;
import org.topbraid.shacl.vocabulary.SH;

import java.io.IOException;

/**
 * Description:
 * <pre>
 *
 * </pre>
 *
 * @author houzhiwei
 * @date 2019/5/18 16:40
 */
public interface ShaclValidation {

    /**
     * Is model conforms.
     *
     * @param validatedResource the validated resource via {@link #validate(Model, String)}
     * @return true or false
     */
    default boolean isModelConforms(Resource validatedResource) {
        return validatedResource.getProperty(SH.conforms).getBoolean();
    }

    /**
     * Gets validated model.
     *
     * @param validatedResource the validated resource
     * @return the validated model
     */
    default Model getValidatedModel(Resource validatedResource) {
        return validatedResource.getModel();
    }

    /**
     * validate ontology using SHACL defined shapes
     *
     * @param dataGraph     the data Graph
     * @param shapeFilePath the shape file path
     * @return validated resource
     */
    Resource validate(Model dataGraph, String shapeFilePath);

    /**
     * Write validation report to file.
     *
     * @param validatedResource the validated resource
     * @param reportFilePath    the report file path
     * @throws IOException the io exception
     */
    void writeReportToFile(Resource validatedResource, String reportFilePath) throws IOException;

    /**
     * Write report to string.
     *
     * @param validatedResource the validated resource
     * @return the report string
     */
    String writeReportToString(Resource validatedResource);

    /**
     * Gets SHACL shape graph.
     * https://stackoverflow.com/questions/50724332/shacl-with-jena-how-to-get-the-model-after-sparql-construct-from-the-shape
     *
     * @param infModel   the (RDFS/OWL) inferred model
     * @param shRuleFile the SHACL rule shape file (.ttl)
     * @return the model contain new triples
     */
    Model getInferredModel(InfModel infModel, String shRuleFile);

    /**
     * validate ontology using SHACL defined shapes
     *
     * @param shapesGraph the shapes graph
     * @param dataGraph   the data graph
     * @return boolean validation report
     */
    ValidationReport validate(String shapesGraph, String dataGraph);

    ValidationReport validate(Model shapesGraph, Model dataGraph);

    String report2Str(ValidationReport report);

    String report2Str(Resource report);

    Resource validateUseTopbraid(String shapesGraph, String dataGraph) throws IOException;

    /**
     * Perform the validation of everything, using the data model
     * also as the shapes model
     *
     * @param dataGraph the data graph
     * @return the resource
     * @throws IOException the io exception
     */
    Resource validateUseTopbraid(String dataGraph) throws IOException;
}
