package org.egc.semantic.vocab;

import openllet.jena.vocabulary.OWL2;
import org.apache.commons.lang3.StringUtils;
import org.apache.jena.geosparql.implementation.vocabulary.Geo;
import org.apache.jena.geosparql.implementation.vocabulary.GeoSPARQL_URI;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.egc.semantic.rdf.RdfUtils;


/**
 * https://gcmd.earthdata.nasa.gov/static/kms/
 *
 * @author houzhiwei
 * @date 2020/12/6 17:37
 */
public class GeoVocabulary {

    private static final Model GEO_VOCAB = RdfUtils.createDefaultModel();
    public static final String DATA_NS = "http://www.egc.org/ont/data#";
    public static final String PROCESS_NS = "http://www.egc.org/ont/process#";
    public static final String UNIT_NS = "http://qudt.org/vocab/unit/";
    public static final String GCMD_NS = "https://gcmdservices.gsfc.nasa.gov/kms/concept/";
    public static final String OGC_UoM_NS = "http://www.opengis.net/def/uom/OGC/1.0/";
    public static final String OGC_ST_NS = "http://www.opengis.net/def/serviceType/ogc/";
    public static final String ADMINGEO_NS = "http://data.ordnancesurvey.co.uk/ontology/admingeo/";
    public static final String GEOM_NS = "http://data.ordnancesurvey.co.uk/ontology/geometry/";
    public static final String GEONAMES_NS = "http://sws.geonames.org/";


    /****************************************
     *                  OGC                 *
     ***************************************/
    public static final Resource CRS84 = createOgcCrs("CRS84");
    public static final Resource URN_CRS84 = createUrnCrs("CRS84");
    public static final Resource CRS1 = createOgcCrs("CRS1");
    public static final Resource CRS27 = createOgcCrs("CRS27");

    public static final Resource EPSG_4326 = createEpsg(4326);

    public static Resource createOgcCrs(String localName) {
        return GEO_VOCAB.createResource("http://www.opengis.net/def/crs/OGC/1.3/" + localName);
    }

    public static Resource createUrnCrs(String localName) {
        return GEO_VOCAB.createResource("urn:ogc:def:crs:OGC:1.3:" + localName);
    }

    public static final Resource Polygon = createSimpleFeature("Polygon");

    public static Resource createSimpleFeature(String localName) {
        return GEO_VOCAB.createResource(GeoSPARQL_URI.SF_URI + localName);
    }

    public static Resource createEpsg(int epsgCode) {
        return GEO_VOCAB.createResource("http://www.opengis.net/def/crs/EPSG/0/" + epsgCode);
    }

    /****************************************
     *              GeoSPARQL               *
     ***************************************/
    public static final Property asWKT = Geo.AS_WKT_PROP;//GEO_VOCAB.createProperty(GeoSPARQL_URI.GEO_URI, "asWKT");
    public static final Property hasGeometry = GEO_VOCAB.createProperty(GeoSPARQL_URI.GEO_URI, "hasGeometry");
    public static final Property defaultGeometry = GEO_VOCAB.createProperty(GeoSPARQL_URI.GEO_URI, "defaultGeometry");


    public static final Property hectares = GEO_VOCAB.createProperty(GEOM_NS, "hectares");
    public static final Property extent = GEO_VOCAB.createProperty(GEOM_NS, "extent");

    /****************************************
     *                themes                 *
     ***************************************/

    public static final Resource GCMD_DIGITAL_ELEVATION = createGcmdTheme("digital_elevation");
    public static final Resource GCMD_DEM = createGcmdTheme("dem");
    public static final Resource GCMD_SOIL = createGcmdTheme("soil");
    public static final Resource GCMD_Landuse = createGcmdTheme("land use");
    public static final Resource GCMD_Landcover = createGcmdTheme("land_cover");
    public static final Resource GCMD_Precipitation = createGcmdTheme("precipitation");
    public static final Resource GCMD_ATMOSPHERE = createGcmdTheme("atmosphere","c47f6052-634e-40ef-a5ac-13f69f6f4c2a");

    public static Resource createGcmdTheme(String localName) {
        return GEO_VOCAB.createResource(GCMD_NS + replaceSpace(localName));
    }

    public static Resource createGcmdTheme(String localName, String id) {
        Resource r = GEO_VOCAB.createResource(GCMD_NS + replaceSpace(localName));
        if (StringUtils.isNotBlank(id)) {
            r.addProperty(OWL2.sameAs, GEO_VOCAB.createResource("https://gcmdservices.gsfc.nasa.gov/kms/concept/" + id));
            r.addProperty(DCTerms.identifier, id);
        }
        return r;
    }

    public static final Resource GCMD_GLOBAL_LAND = createGcmdLocation("GLOBAL LAND", "61cc17c8-4f06-4556-8117-8ba9bb329a3f");
    public static final Resource GCMD_GLOBAL = createGcmdLocation("GLOBAL", "51e3593f-4b42-4141-972e-96666c479f9c");
    public static final Resource GCMD_EASTERN_ASIA = createGcmdLocation("EASTERN ASIA", "a817d381-60e3-4f1b-aa38-041d83b67e70");
    public static final Resource GCMD_CHINA = createGcmdLocation("CHINA", "523afb73-9b4c-4478-97e2-a7d5e228e31c");
    public static final Resource GCMD_USA = createGcmdLocation("UNITED STATES OF AMERICA", "753f915c-70d4-49e1-9bd5-83e75f461e30");

    /**
     * Create gcmd location resource.<br/>
     * https://gcmd.earthdata.nasa.gov/kms/concepts/concept_scheme/locations/?format=xml
     *
     * @param localName the local name
     * @param id        the id
     * @return the resource
     */
    public static Resource createGcmdLocation(String localName, String id) {
        Resource r = GEO_VOCAB.createResource(GCMD_NS + replaceSpace(localName));
        if (StringUtils.isNotBlank(id)) {
            r.addProperty(OWL2.sameAs, GEO_VOCAB.createResource("https://gcmdservices.gsfc.nasa.gov/kms/concept/" + id));
            r.addProperty(DCTerms.identifier, id);
        }
        return r;
    }

    private static String replaceSpace(String name) {
        return name.trim().replaceAll(" ", "_");
    }

    /****************************************
     *                units                 *
     ***************************************/

    public static final Resource degree = createQudtUnit("DEG");
    public static final Resource meter = createQudtUnit("M");
    public static final Resource kilometer = createQudtUnit("KiloM");
    public static final Resource square_meter = createQudtUnit("M2");

    public static Resource createQudtUnit(String localName) {
        return GEO_VOCAB.createResource(UNIT_NS + localName);
    }

    public static final Resource ogc_meter = createOgcUnit("metre");
    public static final Resource ogc_degree = createOgcUnit("degree");

    public static Resource createOgcUnit(String localName) {
        return GEO_VOCAB.createResource(UNIT_NS + localName);
    }

    /****************************************
     *              serviceType                *
     *****************************************/

    public static final Resource wms = createOgcServiceType("wms");
    public static final Resource wfs = createOgcServiceType("wfs");
    public static final Resource wcs = createOgcServiceType("wcs");
    public static final Resource wps = createOgcServiceType("wps");

    public static final Resource wms_standard = createOgcServiceStandard("wms");
    public static final Resource wcs_standard = createOgcServiceStandard("wcs");
    public static final Resource wfs_standard = createOgcServiceStandard("wfs");
    public static final Resource wps_standard = createOgcServiceStandard("wps");

    public static Resource createOgcServiceType(String localName) {
        return GEO_VOCAB.createResource(OGC_ST_NS + localName);
    }

    public static Resource createOgcServiceStandard(String localName) {
        Resource type = GEO_VOCAB.createResource("https://www.ogc.org/standards/" + localName);
        type.addProperty(RDF.type, DCTerms.Standard);
        return type;
    }
}
