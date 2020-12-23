package org.egc.semantic.dto;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.geosparql.implementation.vocabulary.Geo;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.ResourceFactory;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.*;
import org.egc.semantic.rdf.RdfUtils;
import org.egc.semantic.utils.StringUtil;
import org.egc.semantic.vocab.DCAT2;
import org.egc.semantic.vocab.DataOnt;
import org.egc.semantic.vocab.DataSourceOnt;
import org.egc.semantic.vocab.GeoDCAT;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The type Dcat dataset.
 *
 * @author houzhiwei
 * @date 2020 /12/8 16:07
 */
public class DCATDataset {

    public static final String NS = DataSourceOnt.NS;

    private String identifier;
    private String title;
    private String prefLabel;
    private String description;
    private Resource owner;

    private String versionInfo;
    private String landingPageUrl;
    private String descriptionUrl;
    private List<String> keywords;
    private BigDecimal spatialResolutionInMeters;
    private BigDecimal spatialResolutionInDegrees;
    private Resource distribution;
    private Resource creator;
    private Resource contactPoint;
    private Resource language;
    //"yyyy-MM-dd"
    private Literal issued;
    //xsd:duration
    private String temporalResolution;
    //"yyyy-MM-dd"
    private Literal modified;
    private String citation;
    private Resource temporal;
    private List<Resource> themes;
    //dct:Location or identifier
    private Resource spatialIdentifier;
    private Resource spatialBbox;
    private Resource publisher;
    private Resource provenance;
    private Literal startDate;
    private Literal endDate;

    public Model getModel() {
        return model;
    }

    private Model model;

    public Resource getDataset() {
        return dataset;
    }

    private Resource dataset;

    /**
     * Instantiates a new Dcat dataset.
     *
     * @param model       the model
     * @param localName   the local name， uri localName, identifier
     * @param title       the title
     * @param description the description
     */
    public DCATDataset(Model model, @NotBlank String localName, @NotBlank String title, String description) {
        this.title = title;
        this.description = description;
        if (model == null) {
            this.model = RdfUtils.createDefaultModel();
        } else {
            this.model = model;
        }
        DCAT2.initDCATNamespaces(model);
        DCAT2.initThemeNamespaces(model);
        this.dataset = this.model.createResource(NS + localName.replaceAll(" ", "_"));
        this.dataset.addProperty(RDF.type, DCAT.Dataset);
        this.dataset.addProperty(DCTerms.description, this.model.createLiteral(description, "en"));
        this.dataset.addProperty(DCTerms.title, this.model.createTypedLiteral(title, XSDDatatype.XSDstring));
    }

    public String getIdentifier() {
        return identifier;
    }

    /**
     * Sets identifier or coverageId or feature TypeName.
     *
     * @param identifier the identifier
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        this.dataset.addProperty(DCTerms.identifier, model.createTypedLiteral(identifier, XSDDatatype.XSDstring));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.dataset.addProperty(DCTerms.title, model.createTypedLiteral(title, XSDDatatype.XSDstring));
    }

    public String getPrefLabel() {
        return prefLabel;
    }

    /**
     * Sets pref label.
     *
     * @param prefLabel the pref label
     * @param lang      the lang, such as en, zh, ...
     */
    public void setPrefLabel(String prefLabel, String lang) {
        this.prefLabel = prefLabel;
        prefLabel = StringUtils.isBlank(prefLabel) ? this.title : prefLabel;
        this.dataset.addProperty(SKOS.prefLabel, model.createLiteral(prefLabel, lang));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description, String lang) {
        this.description = description;
        this.dataset.addProperty(DCTerms.description, model.createLiteral(description, lang));
    }

    public String getVersionInfo() {
        return versionInfo;
    }

    public void setVersionInfo(String versionInfo) {
        this.versionInfo = versionInfo;
        this.dataset.addProperty(OWL2.versionInfo, model.createTypedLiteral(versionInfo, XSDDatatype.XSDstring));
    }

    public String getLandingPageUrl() {
        return landingPageUrl;
    }

    /**
     * landing page url. e.g., getCapabilities, swagger API
     *
     * @param landingPageUrl the landing page url
     */
    public void setLandingPageUrl(String landingPageUrl) {
        this.landingPageUrl = landingPageUrl;
        Resource doc = model.createResource(landingPageUrl);
        doc.addProperty(RDF.type, FOAF.Document);
        this.dataset.addProperty(DCAT.landingPage, doc);
    }

    public String getDescriptionUrl() {
        return descriptionUrl;
    }

    public void setDescriptionUrl(String descriptionUrl) {
        this.descriptionUrl = descriptionUrl;
        Resource doc = model.createResource(descriptionUrl);
        doc.addProperty(RDF.type, FOAF.Document);
        this.dataset.addProperty(GeoDCAT.describedBy, doc);
    }

    public String getCitation() {
        return citation;
    }

    public void setCitation(String citation) {
        this.citation = citation;
        this.dataset.addProperty(DCTerms.bibliographicCitation, model.createTypedLiteral(citation));
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(String... keywords) {
        this.keywords = new ArrayList<>(Arrays.asList(keywords));
        for (String keyword : keywords) {
            this.dataset.addProperty(DCAT.keyword, ResourceFactory.createStringLiteral(keyword));
        }
    }

    public BigDecimal getSpatialResolutionInMeters() {
        return spatialResolutionInMeters;
    }

    public void setSpatialResolutionInMeters(BigDecimal spatialResolutionInMeters) {
        this.spatialResolutionInMeters = spatialResolutionInMeters;
        this.dataset.addProperty(DCAT.spatialResolutionInMeters, model.createTypedLiteral(spatialResolutionInMeters, XSDDatatype.XSDdecimal));
    }

    public BigDecimal getSpatialResolutionInDegrees() {
        return spatialResolutionInDegrees;
    }

    public void setSpatialResolutionInDegrees(BigDecimal spatialResolutionInDegrees) {
        this.spatialResolutionInDegrees = spatialResolutionInDegrees;
        this.dataset.addProperty(GeoDCAT.spatialResolutionInDegrees, model.createTypedLiteral(spatialResolutionInDegrees, XSDDatatype.XSDdecimal));
    }

    public Resource getDistribution() {
        return distribution;
    }

    public void setDistribution(DCATDistribution distribution) {
        this.distribution = distribution.getDistribution();
        this.dataset.addProperty(DCAT.distribution, distribution.getDistribution());
    }

    public Resource getCreator() {
        return creator;
    }

    public void setCreator(Resource creator) {
        this.creator = creator;
        this.dataset.addProperty(DCTerms.creator, creator);
    }

    public Resource getContactPoint() {
        return contactPoint;
    }

    public void setContactPoint(Resource contactPoint) {
        this.contactPoint = contactPoint;
    }

    public void setContactPoint(String name, String email) {
        this.contactPoint = DCAT2.createContactPoint(this.model, name, email);
        this.dataset.addProperty(DCAT.contactPoint, this.contactPoint);
    }

    public Resource getLanguage() {
        return language;
    }

    public void setLanguage(Resource language) {
        this.language = language;
    }

    public Literal getStartDate() {
        return startDate;
    }

    public void setStartDate(Literal startDate) {
        this.startDate = startDate;
        this.dataset.addProperty(DCAT.startDate, startDate);
    }

    /**
     * @param year  年，必须大于 1900
     * @param month 月，大于 0
     * @param day   日，大于 0
     */
    public void setStartDate(int year, int month, int day) {
        this.startDate = RdfUtils.dateLiteral(year, month, day);
        this.dataset.addProperty(DCAT.startDate, this.startDate);
    }

    public Literal getEndDate() {
        return endDate;
    }

    public void setEndDate(Literal endDate) {
        this.endDate = endDate;
        this.dataset.addProperty(DCAT.endDate, endDate);
    }


    /**
     * @param year  年，必须大于 1900
     * @param month 月，大于 0。小于等于 0 将会设置为 0
     * @param day   日，大于 0。小于等于 0 将会设置为 0
     */
    public void setEndDate(int year, int month, int day) {
        this.endDate = RdfUtils.dateLiteral(year, month, day);
        this.dataset.addProperty(DCAT.endDate, this.endDate);
    }

    public void setCreator(String creatorName, String lang) {
        Resource resource = model.createResource(DataSourceOnt.NS + creatorName);
        resource.addProperty(RDF.type, FOAF.Agent);
        resource.addProperty(SKOS.prefLabel, model.createLiteral(creatorName, StringUtil.lang(lang)));
        resource.addProperty(FOAF.name, model.createLiteral(creatorName, StringUtil.lang(lang)));
        this.creator = resource;
        this.dataset.addProperty(DCTerms.creator, resource);
    }

    public Literal getIssued() {
        return issued;
    }

    /**
     * Sets issued.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     */
    public void setIssued(int year, int month, int day) {
        this.issued = RdfUtils.dateLiteral(year, month, day);
        this.dataset.addProperty(DCTerms.issued, this.issued);
    }

    public String getTemporalResolution() {
        return temporalResolution;
    }

    /**
     * Sets temporal resolution, e.g., "P1D".
     *
     * @param temporalResolution the temporal resolution
     */
    public void setTemporalResolution(String temporalResolution) {
        this.temporalResolution = temporalResolution;
        this.dataset.addProperty(DCAT.temporalResolution, model.createTypedLiteral(temporalResolution, XSDDatatype.XSDduration));
    }

    public Literal getModified() {
        return modified;
    }

    /**
     * Sets modified.
     *
     * @param year  the year
     * @param month the month
     * @param day   the day
     */
    public void setModified(int year, int month, int day) {
        this.modified = RdfUtils.dateLiteral(year, month, day);
        this.dataset.addProperty(DCTerms.modified, modified);
    }

    public Resource getTemporal() {
        return temporal;
    }

    public void setTemporal(Resource temporal) {
        this.temporal = temporal;
    }

    public List<Resource> getThemes() {
        return themes;
    }

    public void setThemes(Resource... themes) {
        this.themes = new ArrayList<>(Arrays.asList(themes));
        for (Resource theme : themes) {
            this.dataset.addProperty(DCAT.theme, theme);
        }
    }

    public Resource getSpatialIdentifier() {
        return spatialIdentifier;
    }

    /**
     * Sets spatial identifier.
     * GCMD location
     * http://publications.europa.eu/resource/authority/country
     * http://publications.europa.eu/resource/authority/place
     * http://publications.europa.eu/resource/authority/continent
     * The geonames URI sets. http://www.geonames.org/
     *
     * @param spatialIdentifier the spatial identifier
     */
    public void setSpatialIdentifier(Resource spatialIdentifier) {
        this.spatialIdentifier = spatialIdentifier;
        this.dataset.addProperty(DCTerms.spatial, spatialIdentifier);
    }

    public Resource getSpatialBbox() {
        return spatialBbox;
    }

    //临时
    private String spatialStr;

    public String getSpatialString() {
        return spatialStr;
    }

    public void setSpatialBbox(Model model, double minx, double miny, double maxx, double maxy, int epsg) {
        Resource location = DataOnt.spatialWktLocation(model, minx, miny, maxx, maxy, epsg);
        this.dataset.addProperty(DCTerms.spatial, location);
        this.dataset.addProperty(Geo.HAS_DEFAULT_GEOMETRY_PROP, location);
        this.spatialStr = location.getProperty(RDF.type).toString() + ";" + location.getProperty(DCAT2.locn_geometry).toString();
        this.spatialBbox = location;
    }

    public void setSpatialBbox(Model model, double minx, double miny, double maxx, double maxy) {
        setSpatialBbox(model, minx, miny, maxx, maxy, 4326);
    }

    public Resource getOwner() {
        return owner;
    }

    /**
     * Sets owner(qualifiedAttribution).
     *
     * @param ownerName the owner name
     * @param lang      the lang
     */
    public void setOwner(String ownerName, String lang) {
        this.owner = DCAT2.getResourceOwner(model, ownerName, lang);
        this.dataset.addProperty(DCAT2.qualifiedAttribution, this.owner);
    }

    public Resource getPublisher() {
        return publisher;
    }

    public void setPublisher(Resource publisher) {
        this.publisher = publisher;
        this.dataset.addProperty(DCTerms.publisher, publisher);
    }

    public Resource getProvenance() {
        return provenance;
    }

    /**
     * Sets provenance.
     *
     * @param provenance the provenance, a DCTerms.ProvenanceStatement
     */
    public void setProvenance(Resource provenance) {
        this.provenance = provenance;
        this.dataset.addProperty(DCTerms.provenance, provenance);
    }
}
