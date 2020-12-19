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
import org.egc.semantic.vocab.DCAT2;
import org.egc.semantic.vocab.DataOnt;
import org.egc.semantic.vocab.DataSourceOnt;
import org.egc.semantic.vocab.GeoDCAT;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * @author houzhiwei
 * @date 2020/12/8 17:10
 */
public class DCATDistribution {

    private String description;
    private String identifier;
    private Resource accessService;
    private String accessUrl;
    private String downloadUrl;
    private BigDecimal spatialResolutionInMeters;
    private BigDecimal spatialResolutionInDegrees;
    //xsd:duration
    private String temporalResolution;
    private Resource temporal;
    private String title;
    private String prefLabel;
    private String mediaType;
    private String format;
    private String compressFormat;
    private Resource distributionType;
    private String pageUrl;
    private String describedBy;
    private Resource describedByType;
    private Model model;
    private Resource dist;

    /**
     * Instantiates a new Dcat distribution.
     *
     * @param model     the model
     * @param accessUrl the access url
     * @param localName the local name
     * @param title     the title
     */
    public DCATDistribution(Model model, @NotBlank String accessUrl, String localName, String title) {
        this.accessUrl = accessUrl;
        if (model == null) {
            this.model = RdfUtils.createDefaultModel();
            this.model.setNsPrefix("ds", NS);
        } else {
            this.model = model;
        }
        if (StringUtils.isNotBlank(localName)) {
            this.dist = this.model.createResource(NS + localName.replaceAll(" ", "_"));
        } else {
            this.dist = this.model.createResource();
        }
        if (StringUtils.isNotBlank(title)) {
            this.dist.addProperty(SKOS.prefLabel, this.model.createLiteral(title, StringUtil.lang("en")));
            this.dist.addProperty(DCTerms.title, this.model.createTypedLiteral(title));
        }
        this.dist.addProperty(RDF.type, DCAT.Distribution);
    }

    public BigDecimal getSpatialResolutionInMeters() {
        return spatialResolutionInMeters;
    }

    public void setSpatialResolutionInMeters(BigDecimal spatialResolutionInMeters) {
        this.spatialResolutionInMeters = spatialResolutionInMeters;
        this.dist.addProperty(DCAT2.spatialResolutionInMeters, model.createTypedLiteral(spatialResolutionInMeters, XSDDatatype.XSDdecimal));
    }

    public BigDecimal getSpatialResolutionInDegrees() {
        return spatialResolutionInDegrees;
    }

    public void setSpatialResolutionInDegrees(BigDecimal spatialResolutionInDegrees) {
        this.spatialResolutionInDegrees = spatialResolutionInDegrees;
        this.dist.addProperty(GeoDCAT.spatialResolutionInDegrees, model.createTypedLiteral(spatialResolutionInDegrees, XSDDatatype.XSDdecimal));
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
        this.dist.addProperty(DCAT2.temporalResolution, model.createTypedLiteral(temporalResolution, XSDDatatype.XSDduration));
    }

    public Resource getTemporal() {
        return temporal;
    }

    public void setTemporal(Resource temporal) {
        this.temporal = temporal;
    }


    public Resource getDistributionType() {
        return distributionType;
    }

    public void setDistributionType(Resource distributionType) {
        this.distributionType = distributionType;
        this.dist.addProperty(DCTerms.type, distributionType);

    }

    public String getPageUrl() {
        return pageUrl;
    }

    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl;
        Resource page = model.createResource(pageUrl);
        page.addProperty(RDF.type, FOAF.Document);
        this.dist.addProperty(FOAF.page, page);
    }

    public void setModel(Model model) {
        this.model = model;
    }


    public static final String NS = DataSourceOnt.NS;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
        this.dist.addProperty(DCTerms.identifier, model.createTypedLiteral(identifier));
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description, String lang) {
        this.description = description;
        this.dist.addProperty(DCTerms.description, model.createLiteral(description, StringUtil.lang(lang)));
    }

    public Resource getAccessService() {
        return accessService;
    }

    /**
     * <pre/>
     * Sets access service.
     * SHOULD be used to link to a description of a dcat:DataService that can provide access to this distribution.
     *
     * @param dataService the access service
     */
    public void setAccessService(DCATDataService dataService) {
        this.accessService = dataService.getService();
        this.dist.addProperty(DCAT2.accessService, dataService.getService());
    }

    /**
     * <pre/>
     * Gets access url.
     * dcat:accessURL matches the property-chain dcat:accessService/dcat:endpointURL
     *
     * @return the access url
     */
    public String getAccessUrl() {
        return accessUrl;
    }

    /**
     * <pre/>
     * Sets access url.
     * SHOULD be used for the URL of a service or location that can provide access to this distribution, typically through a Web form, query or API call.
     * If the distribution(s) are accessible only through a landing page (i.e. direct download URLs are not known), then the landing page URL associated
     * with the dcat:Dataset SHOULD be duplicated as access URL on a distribution
     *
     * @param accessUrl the access url
     */
    public void setAccessUrl(String accessUrl) {
        this.accessUrl = accessUrl;
        this.dist.addProperty(DCAT.accessURL, model.createResource(accessUrl));
    }

    public String getDescribedBy() {
        return describedBy;
    }

    /**
     * Sets described by url. e.g., "http://www.agency.gov/api/vegetables/swagger.json"
     *
     * @param describedByUrl the described by
     */
    public void setDescribedBy(String describedByUrl) {
        this.describedBy = describedByUrl;
        this.dist.addProperty(GeoDCAT.describedBy, model.createResource(describedByUrl));

    }

    public Resource getDescribedByType() {
        return describedByType;
    }

    /**
     * Sets described by type. e.g., "application/swagger+json"
     *
     * @param describedByType the described by type
     */
    public void setDescribedByType(Resource describedByType) {
        this.describedByType = describedByType;
        this.dist.addProperty(GeoDCAT.describedByType, describedByType);

    }

    /**
     * <pre/>
     * Gets download url.
     *
     * @return the download url
     */
    public String getDownloadUrl() {
        return downloadUrl;
    }

    /**
     * <pre/>
     * Sets download url. 可直接下载数据的地址，不是 Web服务 地址
     * dcat:downloadURL is preferred for direct links to downloadable resources.
     * SHOULD be used for the URL at which this distribution is available directly, typically through a HTTP Get request.
     * The URL of the downloadable file in a given format. E.g. CSV file or RDF file.
     * The format is indicated by the distribution's dct:format and/or dcat:mediaType
     *
     * @param downloadUrl the download url
     */
    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
        this.dist.addProperty(DCAT.downloadURL, model.createResource(downloadUrl));
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
        this.dist.addProperty(DCTerms.title, model.createTypedLiteral(title));
    }

    public String getPrefLabel() {
        return prefLabel;
    }

    public void setPrefLabel(String prefLabel, String lang) {
        this.prefLabel = prefLabel;
        this.dist.addProperty(SKOS.prefLabel, model.createLiteral(prefLabel, StringUtil.lang(lang)));
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
        this.dist.addProperty(DCAT.mediaType, DataOnt.createMediaType(mediaType));
    }

    public String getFormat() {
        return format;
    }

    /**
     * Sets format (mediaType).
     *
     * @param formatMediaType the format media type
     */
    public void setFormat(String formatMediaType) {
        this.format = format;
        this.dist.addProperty(DCTerms.format, DataOnt.createMediaType(formatMediaType));
    }

    /**
     * Sets format / mediaType.
     *
     * @param format the format
     */
    public void setFormat(Resource format) {
        this.format = format.getLocalName();
        this.dist.addProperty(DCTerms.format, format);
    }

    public String getCompressFormat() {
        return compressFormat;
    }

    public void setCompressFormat(String compressFormatMediaType) {
        this.compressFormat = compressFormatMediaType;
        this.dist.addProperty(DCTerms.format, DataOnt.createMediaType(compressFormatMediaType));
    }

    public void setCompressFormat(Resource compressFormat) {
        this.compressFormat = compressFormat.getLocalName();
        this.dist.addProperty(DCAT2.compressFormat, compressFormat);
    }

    public static String getNS() {
        return NS;
    }

    public Model getModel() {
        return model;
    }


    public Resource getDistribution() {
        return dist;
    }


}
