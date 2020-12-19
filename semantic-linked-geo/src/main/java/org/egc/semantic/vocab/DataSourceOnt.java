package org.egc.semantic.vocab;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
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
public class DataSourceOnt {
    private static final Model DATA = RdfUtils.createDefaultModel();


    public static final String NS = "http://www.egc.org/ont/datasource#";
    public static final String MT_NS = "http://www.iana.org/assignments/media-types/";

    public static String getURI() {
        return NS;
    }

    public static Model getModel() {
        return DATA;
    }


    //objectProperty
    public static final Property srsURI = DATA.createProperty(NS, "srsURI");

    //datatypeProperty
    public static final Property srsName = DATA.createProperty(NS, "srsName");


    public static final Resource CRS27 = createOgcCrs("CRS27");

    public static Resource createOgcCrs(String localName) {
        return DATA.createResource("http://www.opengis.net/def/crs/OGC/1.3/" + localName);
    }

    public static Resource createEpsg(int epsgCode) {
        return DATA.createResource("http://www.opengis.net/def/crs/EPSG/0/" + epsgCode);
    }

    /******************************************
     *               media-types              *
     *****************************************/
    public static final Resource GeoSpatialDataset = DATA.createResource(NS + "GeoSpatialDataset");
    public static final Resource RasterDataset = DATA.createResource(NS + "RasterDataset");
    public static final Resource VectorDataset = DATA.createResource(NS + "VectorDataset");
    public static final Resource ElevationDataset = DATA.createResource(NS + "ElevationDataset");
    public static final Resource LandCoverDataset = DATA.createResource(NS + "LandCoverDataset");
    public static final Resource SoilDataset = DATA.createResource(NS + "SoilDataset");


    public static Resource createMediaTypes(String localName) {
        return DATA.createResource(MT_NS + localName);
    }

    public static Resource createResourceByName(String localName) {
        return DATA.createResource(NS + localName);
    }

    public static Resource createResource(String uri) {
        return DATA.createResource(uri);
    }

    public static OntModel getOntModel() {
        return OntologyUtils.toOntModel(DATA);
    }

    public static Individual createDataFormat(String format) {
        OntModel ontModel = getOntModel();
        OntClass formatCls = ontModel.createClass(NS + "DataFormat");
        return ontModel.createIndividual(NS + format, formatCls);
    }

}
