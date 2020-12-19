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
import java.util.Calendar;
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
    //"yyyy-MM-dd"
    private String issued;
    //xsd:duration
    private String temporalResolution;
    //"yyyy-MM-dd"
    private String modified;
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
        this.dataset.addProperty(DCAT2.spatialResolutionInMeters, model.createTypedLiteral(spatialResolutionInMeters, XSDDatatype.XSDdecimal));
    }

    public BigDecimal getSpatialResolutionInDegrees() {
        return spatialResolutionInDegrees;
    }

    public void setSpatialResolutionInDegrees(BigDecimal spatialResolutionInDegrees) {
        this.spatialResolutionInDegrees = spatialResolutionInDegrees;
        this.dataset.addProperty(GeoDCAT.spatialResolutionInDegrees, model.createTypedLiteral(spatialResolutionInMeters, XSDDatatype.XSDdecimal));
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

    public Literal getStartDate() {
        return startDate;
    }

    public void setStartDate(Literal startDate) {
        this.startDate = startDate;
        this.dataset.addProperty(DCAT2.startDate, startDate);
    }

    /**
     * @param year  年，必须大于 1900
     * @param month 月，大于 0
     * @param day   日，大于 0
     */
    public void setStartDate(int year, int month, int day) {
        this.startDate = model.createTypedLiteral(setCalendar(year, month, day), XSDDatatype.XSDdate);
        this.dataset.addProperty(DCAT2.startDate, this.startDate);
    }

    public Literal getEndDate() {
        return endDate;
    }

    public void setEndDate(Literal endDate) {
        this.endDate = endDate;
        this.dataset.addProperty(DCAT2.endDate, endDate);
    }

    /**
     * @param year  年，必须大于 1900
     * @param month 月，大于 0。 小于等于 0 将会设置为 0
     * @param day   日，大于 0。小于等于 0 将会设置为 0
     * @return Calendar
     */
    private Calendar setCalendar(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        month = month < 1 ? 0 : month - 1;
        day = Math.max(day, 1);
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, day);
        return c;
    }

    /**
     * @param year  年，必须大于 1900
     * @param month 月，大于 0。小于等于 0 将会设置为 0
     * @param day   日，大于 0。小于等于 0 将会设置为 0
     */
    public void setEndDate(int year, int month, int day) {
        this.endDate = model.createTypedLiteral(setCalendar(year, month, day), XSDDatatype.XSDdate);
        this.dataset.addProperty(DCAT2.endDate, this.endDate);
    }

    public void setCreator(String creatorName, String lang) {
        Resource resource = model.createResource(DataSourceOnt.NS + creatorName);
        resource.addProperty(RDF.type, FOAF.Agent);
        resource.addProperty(SKOS.prefLabel, model.createLiteral(creatorName, StringUtil.lang(lang)));
        resource.addProperty(FOAF.name, model.createLiteral(creatorName, StringUtil.lang(lang)));
        this.creator = resource;
        this.dataset.addProperty(DCTerms.creator, resource);
    }

    public String getIssued() {
        return issued;
    }

    /**
     * Sets issued.
     *
     * @param issued the issued date in format "yyyy-MM-dd"
     */
    public void setIssued(String issued) {
        this.issued = issued;
        this.dataset.addProperty(DCTerms.issued, RdfUtils.dateLiteral(issued));
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
        this.dataset.addProperty(DCAT2.temporalResolution, model.createTypedLiteral(temporalResolution, XSDDatatype.XSDduration));
    }

    public String getModified() {
        return modified;
    }

    /**
     * Sets modified.
     *
     * @param modified the modified date in format "yyyy-MM-dd"
     */
    public void setModified(String modified) {
        this.modified = modified;
        this.dataset.addProperty(DCTerms.modified, RdfUtils.dateLiteral(modified));
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
