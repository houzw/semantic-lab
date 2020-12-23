package org.egc.semantic.vocab;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.geosparql.implementation.vocabulary.GeoSPARQL_URI;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shared.PrefixMapping;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.VCARD4;
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

    public static void initThemeNamespaces(PrefixMapping prefixMapping) {
        RdfUtils.ensurePrefix(prefixMapping, "gcmd", GeoVocabulary.GCMD_NS);
        RdfUtils.ensurePrefix(prefixMapping, "qudt", GeoVocabulary.UNIT_NS);
        RdfUtils.ensurePrefix(prefixMapping, "gemet", InspireVocabulary.GEMET_NS);
        RdfUtils.ensurePrefix(prefixMapping, "theme", InspireVocabulary.THEME_NS);
        RdfUtils.ensurePrefix(prefixMapping, "file", InspireVocabulary.FT_NS);
        RdfUtils.ensurePrefix(prefixMapping, "country", InspireVocabulary.COUNTRY_NS);
        RdfUtils.ensurePrefix(prefixMapping, "dist", InspireVocabulary.DISTRIBUTION_TYPE_NS);
    }

    public static void initDCATNamespaces(PrefixMapping prefixMapping) {
        RdfUtils.ensurePrefix(prefixMapping, "dcat", org.apache.jena.vocabulary.DCAT.NS);
        RdfUtils.ensurePrefix(prefixMapping, "prov", DCAT2.PROV_NS);
        RdfUtils.ensurePrefix(prefixMapping, "foaf", FOAF.NS);
        RdfUtils.ensurePrefix(prefixMapping, "geo", GeoSPARQL_URI.GEO_URI);
        RdfUtils.ensurePrefix(prefixMapping, "sf", GeoSPARQL_URI.SF_URI);
        RdfUtils.ensurePrefix(prefixMapping, "ds", DataSourceOnt.NS);
        RdfUtils.ensurePrefix(prefixMapping, "data", DataOnt.NS);
    }

    public static String getURI() {
        return NS;
    }

    public static final Resource Attribution = DCAT.createResource(PROV_NS + "Attribution");


    public static final Property bbox = DCAT.createProperty(NS, "bbox");


    public static final Property agent = DCAT.createProperty(PROV_NS, "agent");
    public static final Property qualifiedAttribution = DCAT.createProperty(PROV_NS, "qualifiedAttribution");

    public static final Property organizationName = DCAT.createProperty("http://www.w3.org/2006/vcard/ns#", "organization-name");

    public static final Property locn_geometry = DCAT.createProperty(LOCN_NS, "geometry");


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

    public static Resource createContactPoint(Model model, String name, String email) {
        Resource r = model.createResource();
        r.addProperty(RDF.type, VCARD4.Kind);
        r.addProperty(VCARD4.hasEmail, model.createTypedLiteral("<mailto:" + email + ">"));
        r.addProperty(VCARD4.organization_name, model.createTypedLiteral(name));
        return r;
    }
}
