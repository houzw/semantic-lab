package org.egc.semantic.validation;

import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shacl.ValidationReport;

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
     * validate ontology using SHACL defined shapes
     *
     * @param shapesGraph the shapes graph
     * @param dataGraph   the data graph
     * @return boolean validation report
     */
    ValidationReport validate(String shapesGraph, String dataGraph);

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
