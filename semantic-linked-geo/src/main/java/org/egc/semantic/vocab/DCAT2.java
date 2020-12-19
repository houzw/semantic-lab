package org.egc.semantic.vocab;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.egc.semantic.rdf.RdfUtils;

/**
 * @author houzhiwei
 * @date 2020/12/6 16:56
 * @see org.eclipse.rdf4j.model.vocabulary.DCAT
 * @see org.apache.jena.vocabulary.DCAT
 */
public class DCAT2 {
    private static final Model DCAT = ModelFactory.createDefaultModel();

    public static final String NS = "http://www.w3.org/ns/dcat#";
    public static final String PROV_NS = "http://www.w3.org/ns/prov#";
    public static final String ADMS_NS = "http://www.w3.org/ns/adms#";
    public static final String SCHEMA_NS = "http://schema.org/";
    public static final String VCARD_NS = "http://www.w3.org/2006/vcard/ns#";
    public static final String DQV_NS = "http://www.w3.org/ns/dqv#";
    public static final String LOCN_NS = "http://www.w3.org/ns/locn#";


    public static void initDCATNamespaces(PrefixMapping prefixMapping) {
        RdfUtils.ensurePrefix(prefixMapping, "dcat", org.apache.jena.vocabulary.DCAT.NS);
        RdfUtils.ensurePrefix(prefixMapping, "prov", DCAT2.PROV_NS);
        RdfUtils.ensurePrefix(prefixMapping, "foaf", FOAF.NS);
        RdfUtils.ensurePrefix(prefixMapping, "gcmd", GeoVocabulary.GCMD_NS);
        RdfUtils.ensurePrefix(prefixMapping, "ds", DataSourceOnt.NS);
        RdfUtils.ensurePrefix(prefixMapping, "data", DataOnt.NS);
    }

    public static String getURI() {
        return NS;
    }

    //jena 中的 dcat 版本较低，暂时没有与 web service 相关的定义，因此需要自行定义
    public static final Resource DataService = DCAT.createResource(NS + "DataService");
    public static final Resource Attribution = DCAT.createResource(PROV_NS + "Attribution");


    public static final Property bbox = DCAT.createProperty(NS, "bbox");
    public static final Property servesDataset = DCAT.createProperty(NS, "servesDataset");
    public static final Property spatialResolutionInMeters = DCAT.createProperty(NS, "spatialResolutionInMeters");
    public static final Property startDate = DCAT.createProperty(NS, "startDate");
    public static final Property endDate = DCAT.createProperty(NS, "endDate");
    public static final Property theme = org.apache.jena.vocabulary.DCAT.theme;
    public static final Property keyword = org.apache.jena.vocabulary.DCAT.keyword;
    public static final Property endpointURL = DCAT.createProperty(NS, "endpointURL");
    public static final Property endpointDescription = DCAT.createProperty(NS, "endpointDescription");
    public static final Property accessService = DCAT.createProperty(NS, "accessService");
    public static final Property temporalResolution = DCAT.createProperty(NS, "temporalResolution");
    public static final Property compressFormat = DCAT.createProperty(NS, "compressFormat");


    public static final Property agent = DCAT.createProperty(PROV_NS, "agent");
    public static final Property qualifiedAttribution = DCAT.createProperty(PROV_NS, "qualifiedAttribution");

    public static final Property organizationName = DCAT.createProperty("http://www.w3.org/2006/vcard/ns#", "organization-name");

    public static final Property locn_geometry = DCAT.createProperty(LOCN_NS, "qualifiedAttribution");


    public static Resource getResourceOwner(Model model, String owner, String lang) {
        Resource ownerResource = model.createResource();
        ownerResource.addProperty(RDF.type, Attribution);
        ownerResource.addProperty(DCTerms.type, InspireVocabulary.owner);

        Resource agent = model.createResource();
        agent.addProperty(RDF.type, FOAF.Organization);
        lang = StringUtils.isBlank(lang) ? "en" : lang;
        agent.addProperty(FOAF.name, model.createLiteral(owner, lang));

        ownerResource.addProperty(DCAT2.agent, agent);
        return ownerResource;
    }

    public static Resource createProvenance(Model model, String provenance, String lang) {
        Resource prov = model.createResource();
        lang = StringUtils.isBlank(lang) ? "en" : lang;
        prov.addProperty(RDF.type, DCTerms.ProvenanceStatement);
        prov.addProperty(DCTerms.description, model.createTypedLiteral(provenance, lang));
        return prov;
    }
}
