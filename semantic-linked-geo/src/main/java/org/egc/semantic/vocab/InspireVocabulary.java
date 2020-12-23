package org.egc.semantic.vocab;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.SKOS;

/**
 * @author houzhiwei
 * @date 2020/12/6 21:51
 */
public class InspireVocabulary {

    private static final Model VOCAB = ModelFactory.createDefaultModel();

    public static final String THEME_NS = "http://inspire.ec.europa.eu/theme/";
    public static final String TC_NS = "https://inspire.ec.europa.eu/metadata-codelist/TopicCategory/";
    public static final String FT_NS = "http://publications.europa.eu/resource/authority/file-type/";
    public static final String LICENCE_NS = "http://publications.europa.eu/resource/authority/licence/";
    //General Multilingual Environmental Thesaurus
    public static final String GEMET_NS = "https://www.eionet.europa.eu/gemet/concept/";
    public static final String DISTRIBUTION_TYPE_NS = "http://publications.europa.eu/resource/authority/distribution-type/";

    public static final Resource WEB_SERVICE = createDistributionType("WEB_SERVICE");
    public static final Resource DOWNLOADABLE_FILE = createDistributionType("DOWNLOADABLE_FILE");
    public static final Resource VISUALIZATION = createDistributionType("VISUALIZATION");
    /**
     * 临时数据
     */
    public static final Resource PROVISIONAL_DATA = createDistributionType("OP_DATPRO");
    //e.g., RSS feed
    public static final Resource FEED_INFO = createDistributionType("FEED_INFO");

    public static final Resource COM_REUSE = VOCAB.createResource("http://publications.europa.eu/resource/authority/licence/COM_REUSE");
    public static final Resource CC_BY = VOCAB.createResource(LICENCE_NS + "CC_BY");
    public static final Resource owner = VOCAB.createResource("http://inspire.ec.europa.eu/metadata-codelist/ResponsiblePartyRole/owner");


    public static final Resource grid = createSpatialRepresentationType("grid");

    /*****************************************
     *            TopicCategory              *
     *****************************************/

    public static final Resource topic_environment = createTopicCategory("environment");
    public static final Resource topic_geoscientificInformation = createTopicCategory("geoscientificInformation");
    public static final Resource topic_location = createTopicCategory("location");
    public static final Resource topic_elevation = createTopicCategory("elevation");
    public static final Resource topic_inlandWaters = createTopicCategory("inlandWaters");

    /**
     * Service that enables copies of spatial data sets, or parts of such sets,
     * to be downloaded and, where practicable, accessed directly.
     */
    public static final Resource serviceType_Download = createSpatialDataServiceType("download");
    /**
     * Service that makes it possible, as a minimum, to display, navigate, zoom in and out, pan or overlay viewable spatial
     * data sets and to display legend information and any relevant content of metadata
     */
    public static final Resource serviceType_View = createSpatialDataServiceType("view");
    /**
     * Services making it possible to search for spatial data sets and services on the basis of the content
     * of the corresponding metadata and to display the content of the metadata.
     */
    public static final Resource serviceType_Discovery = createSpatialDataServiceType("discovery");
    /**
     * Service that enables spatial data sets to be transformed with a view to achieving interoperability.
     */
    public static final Resource serviceType_Transformation = createSpatialDataServiceType("transformation");
    /**
     * Service that allows defining both the data inputs and data outputs expected by the spatial service and a workflow or service
     * chain combining multiple services. It also allows defining the external web service interface of the workflow or service chain.
     */
    public static final Resource serviceType_Invoke = createSpatialDataServiceType("invoke");
    /**
     * Other Service
     */
    public static final Resource serviceType_Other = createSpatialDataServiceType("other");
    public static final Resource serviceCategory_CoverageAccessService = createSpatialDataServiceCategory("infoCoverageAccessService");

    public static Resource createDistributionType(String type) {
        return VOCAB.createResource(DISTRIBUTION_TYPE_NS + type);
    }

    public static Resource createTopicCategory(String localName) {
        return VOCAB.createResource(TC_NS + localName);
    }

    public static final String SpatialRepresentation_NS = "https://inspire.ec.europa.eu/metadata-codelist/SpatialRepresentationTypeCode/";

    public static Resource createSpatialRepresentationType(String localName) {
        return VOCAB.createResource(SpatialRepresentation_NS + localName);
    }

    public static final String SpatialDataService_NS = "https://inspire.ec.europa.eu/metadata-codelist/SpatialDataServiceType/";

    public static Resource createSpatialDataServiceType(String localName) {
        return VOCAB.createResource(SpatialDataService_NS + localName);
    }

    public static final String SpatialDataServiceCategory_NS = "https://inspire.ec.europa.eu/metadata-codelist/SpatialDataServiceCategory/";

    public static Resource createSpatialDataServiceCategory(String localName) {
        return VOCAB.createResource(SpatialDataServiceCategory_NS + localName);
    }

    /******************************************
     *                 theme                  *
     *****************************************/

    public static final Resource DEM = createTheme("el");
    public static final Resource Soil = createTheme("so");
    public static final Resource Land_cover = createTheme("lc");
    public static final Resource Land_use = createTheme("lu");
    public static final Resource Meteorological_geographical_features = createTheme("mf");
    public static final Resource SpatialScope_Global = createTheme("global");
    public static final Resource SpatialScope_National = createTheme("national");

    public static final Resource gemet_meteorology = createGemetTheme("meteorology", 5197);
    public static final Resource gemet_soil = createGemetTheme("soil", 7843);
    public static final Resource gemet_altitude = createGemetTheme("altitude", 10140);


    public static Resource createTheme(String localName) {
        return VOCAB.createResource(THEME_NS + localName);
    }

    public static Resource createGemetTheme(String localName, int id) {
        Resource resource = VOCAB.createResource(GEMET_NS + id);
        resource.addProperty(SKOS.prefLabel, localName);
        return resource;
    }

    public static Resource createSpatialScope(String localName) {
        return VOCAB.createResource("http://inspire.ec.europa.eu/metadata-codelist/SpatialScope/" + localName);
    }

    /******************************************
     *                file-type               *
     *****************************************/
    public static final Resource RDF_XML = createFileType("RDF_XML");
    public static final Resource XML = createFileType("XML");
    public static final Resource CSV = createFileType("CSV");

    public static Resource createFileType(String localName) {
        return VOCAB.createResource(FT_NS + localName);
    }

    /**
     * The constant CHN for China.
     */
    public static final Resource Country_CHN = createCountry("CHN");
    public static final String COUNTRY_NS = "http://publications.europa.eu/resource/authority/country/";

    /**
     * Create country resource.
     * search https://op.europa.eu/en/web/eu-vocabularies/at-dataset/-/resource/dataset/atu?target=About
     * http://publications.europa.eu/resource/authority/country list
     *
     * @param localName the local name
     * @return the resource
     */
    public static Resource createCountry(String localName) {
        return VOCAB.createResource(COUNTRY_NS + localName);
    }

    public static Resource createLanguage(String lang) {
        Resource l = VOCAB.createResource("http://publications.europa.eu/resource/authority/language/" + lang);
        l.addProperty(RDF.type, DCTerms.LinguisticSystem);
        return l;
    }

    public static final Resource langENG = createLanguage("ENG");
    public static final Resource langCHN = createLanguage("CMN");

    public static Resource createLicense(String licence) {
        Resource l = VOCAB.createResource("http://publications.europa.eu/resource/authority/licence/" + licence);
        l.addProperty(RDF.type, DCTerms.LinguisticSystem);
        return l;
    }

}
