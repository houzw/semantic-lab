package org.egc.semantic.geospatial;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.SKOS;
import org.egc.semantic.rdf.RdfUtils;
import org.egc.semantic.vocab.DCAT2;
import org.egc.semantic.vocab.DataSourceOnt;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author houzhiwei
 * @date 2020/12/8 14:32
 */
public class DataSourceGraph {
    private Model model;
    String title, description;
    String NS = DataSourceOnt.NS;

    public DataSourceGraph() {
        model = RdfUtils.createDefaultModel();
        model.setNsPrefix("ds", DataSourceOnt.getURI());

    }

    /**
     * Create dataset resource.
     *
     * @param identifier     the identifier
     * @param prefLabel      the pref label
     * @param datasetType    the dataset type
     * @param description    the description
     * @param themes         the themes
     * @param landingPageDoc the landing page foaf:Document, e.g., getCapabilities, swagger API
     * @param keywords       the keywords
     * @param citation       the citation
     * @param versionInfo    the version info
     * @return the resource
     */
    public Resource createDataset(String identifier, String prefLabel, Resource datasetType,
                                  String description, List<Resource> themes, Resource landingPageDoc,
                                  List<String> keywords, BigDecimal resolution, String citation, String versionInfo) {
        Resource dataset = model.createResource(NS + identifier);
       /* model.add(dataset, RDF.type, datasetType);
        model.add(dataset, SKOS.prefLabel, model.createLiteral(prefLabel, "en"));
        model.add(dataset, DCTerms.description, model.createLiteral(description, "en"));
        model.add(dataset, DCTerms.bibliographicCitation, model.createTypedLiteral(citation));
        model.add(dataset, OWL2.versionInfo, model.createTypedLiteral(versionInfo));
        model.add(dataset, DCAT.landingPage, landingPageDoc);
        model.add(dataset, GeoDCAT.spatialResolutionInMeters, model.createTypedLiteral(resolution, XSDDatatype.XSDdecimal));
        for (Resource theme : themes) {
            model.add(dataset, DCAT.theme, theme);
        }
        for (String keyword : keywords) {
            model.add(dataset, DCAT.keyword, model.createLiteral(keyword));
        }*/
        return dataset;
    }
/*
    public Resource createServiceDistribution(Resource dataset, Resource serviceType, Resource serviceStandard) {
        Resource dist = model.createResource();
        model.add(dist, RDF.type, DCAT.Distribution);
        model.add(dist, DCTerms.title, model.createLiteral(title, "en"));
        model.add(dist, DCTerms.description, model.createLiteral(description, "en"));
        model.add(dist, DCTerms.type, serviceType);
        model.add(dist, DCTerms.type, InspireVocabulary.WEB_SERVICE);
        model.add(dist, DCTerms.conformsTo, serviceStandard);
        model.add(dataset, DCAT.distribution, dist);
        return dataset;
    }*/
/*
    public void createDataService(String title, String serviceEndpoint, Resource serviceType, Resource serviceStandard) {
        Resource service = model.createResource(NS + title.replaceAll(" ", "_"));
        model.add(service, RDF.type, GeoDCAT.DataService);
        model.add(service, GeoDCAT.endpointURL, model.createResource(serviceEndpoint));
        model.add(service, DCTerms.title, model.createLiteral(title, "en"));
        model.add(service, GeoDCAT.endpointDescription, model.createLiteral(description, "en"));
        model.add(service, DCTerms.type, serviceType);
        model.add(service, DCTerms.conformsTo, serviceStandard);
    }*/

    public Resource createDocument(String getCapabilitiesUrl) {
        Resource doc = model.createResource(getCapabilitiesUrl);
        model.add(doc, RDF.type, FOAF.Document);
        return doc;
    }

    public Resource createDataProvider(String identifier, String title, String longTitle, String cnTitle) {
        Resource provider = model.createResource(NS + identifier);
        model.add(provider, RDF.type, FOAF.Agent);
        model.add(provider, SKOS.prefLabel, model.createLiteral(title, "en"));
        if (StringUtils.isNotBlank(cnTitle)) {
            model.add(provider, SKOS.prefLabel, model.createLiteral(cnTitle, "zh"));
        }
        model.add(provider, DCAT2.organizationName, model.createTypedLiteral(title));
        model.add(provider, FOAF.name, model.createTypedLiteral(title));
        if (StringUtils.isNotBlank(longTitle)) {
            model.add(provider, DCAT2.organizationName, model.createTypedLiteral(longTitle));
        }
        return provider;
    }
}
