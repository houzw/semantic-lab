package org.egc.semantic.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.SKOS;
import org.egc.semantic.rdf.RdfUtils;
import org.egc.semantic.utils.StringUtil;
import org.egc.semantic.vocab.DataSourceOnt;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author houzhiwei
 * @date 2020/12/8 17:10
 */
public class DCATDataService {

    public static final String NS = DataSourceOnt.NS;

    private Resource serviceType;
    private Resource ogcServiceType;
    private Resource spatialDataServiceType;
    private Resource serviceStandard;
    private String endpointDescriptionUrl;
    private String description;
    private String endpointUrl;
    private String title;
    private List<String> keywords;
    private List<Resource> servedDatasets;
    private Model model;
    private Resource service;
    private Resource publisher;
    private BigDecimal spatialResolutionInMeters;
    private String temporalResolution;
    private List<Resource> themes;

    /**
     * Instantiates a new Dcat data service.
     *
     * @param model       the model
     * @param endpointUrl the endpoint url, the root location or primary endpoint of the service (a Web-resolvable IRI).
     * @param localName   the local name
     * @param title       the title
     */
    public DCATDataService(Model model, @NotBlank String endpointUrl, String localName, String title) {
        this.endpointUrl = endpointUrl;
        this.title = title;
        if (model == null) {
            this.model = RdfUtils.createDefaultModel();
            this.model.setNsPrefix("ds", NS);
        } else {
            this.model = model;
        }
        if (StringUtils.isNotBlank(localName)) {
            this.service = this.model.createResource(NS + localName.replaceAll(" ", "_"));
        } else {
            this.service = this.model.createResource();
        }
        this.service.addProperty(RDF.type, DCAT.DataService);
        this.service.addProperty(DCAT.endpointURL, this.model.createResource(endpointUrl));
        this.service.addProperty(DCTerms.title, this.model.createTypedLiteral(title));
    }

    public Resource getService() {
        return service;
    }

    public Resource getOgcServiceType() {
        return ogcServiceType;
    }

    public void setOgcServiceType(Resource ogcServiceType) {
        this.ogcServiceType = ogcServiceType;
        this.service.addProperty(DCTerms.type, ogcServiceType);
    }

    public Resource getSpatialDataServiceType() {
        return spatialDataServiceType;
    }

    /**
     * Sets inspire spatial data service type.
     *
     * @param spatialDataServiceType the spatial data service type
     */
    public void setSpatialDataServiceType(Resource spatialDataServiceType) {
        this.spatialDataServiceType = spatialDataServiceType;
        this.service.addProperty(DCTerms.type, spatialDataServiceType);
    }

    public Resource getServiceType() {
        return serviceType;
    }

    /**
     * Sets service type
     *
     * @param serviceType the service type
     */
    public void setServiceType(Resource serviceType) {
        this.serviceType = serviceType;
        this.service.addProperty(DCTerms.type, serviceType);
    }

    public Resource getServiceStandard() {
        return serviceStandard;
    }

    public void setServiceStandard(Resource serviceStandard) {
        this.serviceStandard = serviceStandard;
        this.service.addProperty(DCTerms.conformsTo, serviceStandard);
    }

    public String getEndpointDescriptionUrl() {
        return endpointDescriptionUrl;
    }

    /**
     * <pre/>
     * Sets endpoint description url.
     * An endpoint description may be expressed in a machine-readable form, such as an OpenAPI (Swagger) description ,
     * an OGC GetCapabilities response, a SPARQL Service Description, an [OpenSearch] or [WSDL20] document,
     * a Hydra API description [HYDRA], else in text or some other informal mode if a formal representation is not possible.
     *
     * @param endpointDescriptionUrl the endpoint description url
     */
    public void setEndpointDescriptionUrl(String endpointDescriptionUrl) {
        this.endpointDescriptionUrl = endpointDescriptionUrl;
        this.service.addProperty(DCAT.endpointDescription, model.createResource(endpointDescriptionUrl));
    }

    public String getEndpointUrl() {
        return endpointUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.service.addProperty(DCTerms.title, model.createLiteral(title, "en"));
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(String... keywords) {
        this.keywords = Arrays.asList(keywords);
        for (String keyword : keywords) {
            this.service.addProperty(DCTerms.subject, model.createTypedLiteral(keyword));
        }
    }

    public List<Resource> getServedDatasets() {
        return servedDatasets;
    }

    public void setServedDatasets(DCATDataset... servedDatasets) {
        this.servedDatasets = new ArrayList<>();
        for (DCATDataset sd : servedDatasets) {
            Resource ds = sd.getDataset();
            this.service.addProperty(DCAT.servesDataset, ds);
            this.servedDatasets.add(ds);
        }
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public BigDecimal getSpatialResolutionInMeters() {
        return spatialResolutionInMeters;
    }

    public void setSpatialResolutionInMeters(BigDecimal spatialResolutionInMeters) {
        this.spatialResolutionInMeters = spatialResolutionInMeters;
    }

    public String getTemporalResolution() {
        return temporalResolution;
    }

    public void setTemporalResolution(String temporalResolution) {
        this.temporalResolution = temporalResolution;
        this.service.addProperty(DCAT.temporalResolution, model.createTypedLiteral(temporalResolution, XSDDatatype.XSDduration));
    }

    public List<Resource> getThemes() {
        return themes;
    }

    public void setThemes(Resource... themes) {
        this.themes = Arrays.asList(themes);
        for (Resource theme : themes) {
            this.service.addProperty(DCTerms.subject, model.createTypedLiteral(theme));
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description, String lang) {
        this.description = description;
        this.service.addProperty(DCTerms.description, model.createLiteral(description, StringUtil.lang(lang)));

    }

    public Resource getPublisher() {
        return publisher;
    }

    public void setPublisher(Resource publisher) {
        this.publisher = publisher;
        this.service.addProperty(DCTerms.publisher, publisher);

    }

    public void setPublisher(String publisherName, String lang) {
        Resource resource = model.createResource(DataSourceOnt.NS + publisherName);
        resource.addProperty(RDF.type, FOAF.Agent);
        resource.addProperty(SKOS.prefLabel, model.createLiteral(publisherName, StringUtil.lang(lang)));
        resource.addProperty(FOAF.name, model.createLiteral(publisherName, StringUtil.lang(lang)));
        this.publisher = resource;
        this.service.addProperty(DCTerms.publisher, resource);
    }
}
