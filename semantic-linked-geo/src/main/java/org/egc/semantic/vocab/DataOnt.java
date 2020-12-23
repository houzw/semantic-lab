package org.egc.semantic.vocab;

import org.apache.jena.geosparql.implementation.WKTLiteralFactory;
import org.apache.jena.geosparql.implementation.vocabulary.Geo;
import org.apache.jena.geosparql.implementation.vocabulary.GeoSPARQL_URI;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.egc.semantic.rdf.OntologyUtils;
import org.egc.semantic.rdf.RdfUtils;

/**
 * The type Data ont.
 *
 * @author houzhiwei
 * @date 2020 /12/6 16:56
 */
public class DataOnt {
    private static final Model DATA = RdfUtils.createDefaultModel();
    public static final String NS = "http://www.egc.org/ont/data#";
    public static final String MT_NS = "http://www.iana.org/assignments/media-types/";
    public static final String W3CGEO_NS = "http://www.w3.org/2003/01/geo/wgs84_pos#";

    public static String getURI() {
        return NS;
    }

    public static Model getModel() {
        return DATA;
    }

    public static final Resource NAMESPACE = DATA.createResource(NS);

    //objectProperty
    public static final Property srsURI = DATA.createProperty(NS, "srsURI");
    public static final Property hasSRS = DATA.createProperty(NS, "hasSRS");
    public static final Property hasUOM = DATA.createProperty(NS, "hasUOM");
    public static final Property dataFormat = DATA.createProperty(NS, "dataFormat");

    //datatypeProperty
    public static final Property srsName = DATA.createProperty(NS, "srsName");
    public static final Property hasEPSG = DATA.createProperty(NS, "hasEPSG");
    public static final Property hasSRID = DATA.createProperty(NS, "hasSRID");
    public static final Property hasCRSProj4 = DATA.createProperty(NS, "hasCRSProj4");
    public static final Property hasCRSWkt = DATA.createProperty(NS, "hasCRSWkt");
    public static final Property isProjected = DATA.createProperty(NS, "isProjected");
    public static final Property northBoundingCoordinate = DATA.createProperty(NS, "northBoundingCoordinate");
    public static final Property southBoundingCoordinate = DATA.createProperty(NS, "southBoundingCoordinate");
    public static final Property westBoundingCoordinate = DATA.createProperty(NS, "westBoundingCoordinate");
    public static final Property eastBoundingCoordinate = DATA.createProperty(NS, "eastBoundingCoordinate");
    public static final Property bboxAsWKT = DATA.createProperty(NS, "bboxAsWKT");
    public static final Property bboxAsGML = DATA.createProperty(NS, "bboxAsGML");
    public static final Property bandCount = DATA.createProperty(NS, "bandCount");
    public static final Property bandMaxValue = DATA.createProperty(NS, "bandMaxValue");
    public static final Property bandMinValue = DATA.createProperty(NS, "bandMinValue");
    public static final Property maxX = DATA.createProperty(NS, "maxX");
    public static final Property maxY = DATA.createProperty(NS, "maxY");
    public static final Property minX = DATA.createProperty(NS, "minX");
    public static final Property minY = DATA.createProperty(NS, "minY");
    public static final Property nodataValue = DATA.createProperty(NS, "nodataValue");
    public static final Property dataContent = DATA.createProperty(NS, "dataContent");
    public static final Property parameterId = DATA.createProperty(NS, "parameterId");
    /**
     * same as dct:identifier
     */
    public static final Property coverageId = DATA.createProperty(NS, "coverageId");

    //custom: readme file url

    public static final Resource RasterData = DATA.createResource(NS + "RasterData");
    public static final Resource VectorData = DATA.createResource(NS + "VectorData");

    public static final Resource GridRaster = DATA.createResource(NS + "GridRaster");
    public static final Resource GTiff = DATA.createResource(NS + "GTiff");
    public static final Resource GeoJSON = DATA.createResource(NS + "GeoJSON");
    public static final Resource ESRI_Shapefile = DATA.createResource(NS + "ESRI_Shapefile");

    /******************************************
     *               media-types              *
     *****************************************/
    public static final Resource TEXT_CSV = createMediaType("text/csv");
    public static final Resource TEXT_PLAIN = createMediaType("text/plain");
    public static final Resource TEXT_XML = createMediaType("text/xml");
    public static final Resource IMAGE_TIFF = createMediaType("image/tiff");
    public static final Resource APP_GEOTIFF = createMediaType("application/geotiff");
    public static final Resource APP_GZIP = createMediaType("application/gzip");
    public static final Resource APP_SWAGGER_JSON = createMediaType("application/swagger+json");
    public static final Resource APP_OPENAPI_JSON = createMediaType("application/vnd.oai.openapi+json");
    public static final Resource APP_OPENAPI_YAML = createMediaType("application/vnd.oai.openapi");
    public static final Resource APP_7Z = createMediaType("application/x-7z-compressed");
    public static final Resource APP_DBF = createMediaType("application/dbase");
    public static final Resource APP_EXE = createMediaType("application/vnd.microsoft.portable-executable");
    public static final Resource APP_ZIP = createMediaType("application/zip");
    /**
     * 用于表示 Web API， 如 rest api
     */
    public static final Resource FORMAT_API = DATA.createResource(NS + "API");
    public static final Resource FORMAT_OpenAPI = DATA.createResource(NS + "OpenAPI");
    //bil
    public static final Resource FORMAT_EHdr = DATA.createResource(NS + "EHdr");
    // 没有 iana 官网定义
    public static final Resource APP_ASCII_GRASS = createMediaType("application/image-ascii-grass");
    public static final Resource APP_OCTET_STREAM = createMediaType("application/octet-stream");
    public static final Resource APP_SHP = createMediaType("application/shp");
    public static final Resource APP_WKT = createMediaType("application/wkt");

    public static Resource spatialWktLocation(Model model, double minx, double miny, double maxx, double maxy, int epsg) {
        Resource spatialLocation = model.createResource();
        spatialLocation.addProperty(RDF.type, DCTerms.Location);
        Literal bbox;
        if (epsg >= 1024 && epsg <= 32768) {
            bbox = WKTLiteralFactory.createBox(minx, miny, maxx, maxy, GeoVocabulary.createEpsg(epsg).getURI());
        } else {
            bbox = WKTLiteralFactory.createBox(minx, miny, maxx, maxy, GeoVocabulary.CRS84.getURI());
        }
        spatialLocation.addProperty(DCAT2.locn_geometry, bbox);
        spatialLocation.addProperty(DCAT2.bbox, bbox);

        spatialLocation.addProperty(RDF.type, model.createResource(GeoSPARQL_URI.SF_URI + "Polygon"));
        spatialLocation.addProperty(Geo.AS_WKT_PROP, bbox);

        return spatialLocation;
    }


    /**
     * Spatial coverage of dataset based on geographic identifier. <br/>
     * use a URI from a controlled vocabulary, such as
     * <a href="https://op.europa.eu/en/web/eu-vocabularies/at-dataset/-/resource/dataset/atu?target=About">Administrative territorial unit</a>
     * <a href="http://publications.europa.eu/resource/authority/country">NAL countries in rdf</a>,
     * <a href="http://publications.europa.eu/resource/authority/place">NAL places in rdf</a>,
     * or <a href="http://www.geonames.org/"> geonames</a>
     * <br/><br/>
     * INSPIRE country identifier
     * {@link InspireVocabulary#createCountry(String)}
     *
     * @param geographicIdentifierUri the geographic identifier uri
     * @return the resource
     */
    public static Resource spatialIdentifier(String geographicIdentifierUri) {
        return DATA.createResource(geographicIdentifierUri);
    }

    public static Resource createMediaType(String localName) {
        Resource type = DATA.createResource(MT_NS + localName);
        type.addProperty(RDF.type, DCTerms.MediaType);
        return type;
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

    public static final Resource no_limitation = createAccessRights("no limitation");
    public static final Resource registration_required = createAccessRights("registration required");
    public static final Resource authorization_required = createAccessRights("authorization required");

    public static Resource createAccessRights(String rights) {
        Resource r = DATA.createResource();
        r.addProperty(RDF.type, DCTerms.RightsStatement);
        r.addProperty(RDFS.label, DATA.createLiteral(rights, "en"));
        return r;
    }

}
