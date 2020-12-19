package org.egc.semantic.vocab;

import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.egc.semantic.rdf.OntologyUtils;
import org.egc.semantic.rdf.RdfUtils;

/**
 * @author houzhiwei
 * @date 2020/12/6 16:56
 */
public class ProcessOnt {
    private static final Model PROCESS = RdfUtils.createDefaultModel();

    public static final String NS = "http://www.egc.org/ont/process#";
    public static final String SOFT_NS = "http://www.egc.org/ont/soft#";

    public static String getURI() {
        return NS;
    }

    public static Model getModel() {
        return PROCESS;
    }


    //objectProperty
    public static final Property serviceType = PROCESS.createProperty(NS, "serviceType");
    public static final Property isToolOfAlgorithm = PROCESS.createProperty(NS, "isToolOfAlgorithm");
    public static final Property isToolOfSoftware = PROCESS.createProperty(NS, "isToolOfSoftware");

    //datatypeProperty
    public static final Property accessURLTemplate = PROCESS.createProperty(NS, "accessURLTemplate");


    /******************************************
     *               media-types              *
     *****************************************/


    public static Resource createResourceByName(String localName) {
        return PROCESS.createResource(NS + localName);
    }

    public static Resource createResource(String uri) {
        return PROCESS.createResource(uri);
    }

    public static OntModel getOntModel() {
        return OntologyUtils.toOntModel(PROCESS);
    }

}
