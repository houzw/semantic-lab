package org.egc.semantic.vocab;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;

/**
 * @author houzhiwei
 * @date 2020/12/6 16:56
 * @see org.eclipse.rdf4j.model.vocabulary.DCAT
 * @see org.apache.jena.vocabulary.DCAT
 */
public class GeoDCAT {
    private static final Model DCAT = ModelFactory.createDefaultModel();

    public static final String NS = "http://data.europa.eu/930/";

    public static String getURI() {
        return NS;
    }

    public static final Resource GEODCATAP_NAMESPACE = DCAT.createResource(NS);

    public static final Property describedBy = DCAT.createProperty(DataOnt.NS, "describedBy");
    public static final Property describedByType = DCAT.createProperty(DataOnt.NS, "describedByType");

    public static final Property spatialResolutionAsScale = DCAT.createProperty(NS, "spatialResolutionAsScale");
    public static final Property spatialResolutionInDegrees = DCAT.createProperty(NS, "spatialResolutionInDegrees");
    public static final Property spatialResolutionAsVerticalDistanceInMeters = DCAT.createProperty(NS, "spatialResolutionAsVerticalDistanceInMeters");


}
