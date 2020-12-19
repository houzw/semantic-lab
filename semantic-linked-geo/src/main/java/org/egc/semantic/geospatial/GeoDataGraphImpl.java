package org.egc.semantic.geospatial;

import org.apache.commons.lang3.StringUtils;
import org.apache.jena.datatypes.xsd.XSDDatatype;
import org.apache.jena.geosparql.implementation.WKTLiteralFactory;
import org.apache.jena.geosparql.implementation.vocabulary.GeoSPARQL_URI;
import org.apache.jena.rdf.model.AnonId;
import org.apache.jena.rdf.model.Literal;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.shacl.vocabulary.SHACL;
import org.apache.jena.vocabulary.DCAT;
import org.apache.jena.vocabulary.DCTerms;
import org.apache.jena.vocabulary.RDF;
import org.egc.commons.gis.RasterMetadata;
import org.egc.commons.gis.VectorMetadata;
import org.egc.semantic.rdf.RdfUtils;
import org.egc.semantic.vocab.*;

//import org.apache.jena.geosparql.implementation.WKTLiteralFactory;
//import org.apache.jena.geosparql.implementation.vocabulary.GeoSPARQL_URI;

/**
 * @author houzhiwei
 * @date 2020/12/6 15:06
 */
public class GeoDataGraphImpl implements GeoDataGraph {

    @Override
    public Model initDataGraphModel() {
        Model model = RdfUtils.createDefaultModel();
        model.setNsPrefix("sh", SHACL.getURI());
        model.setNsPrefix("unit", UNIT_NAMESPACE);
        model.setNsPrefix("geo", GeoSPARQL_URI.GEO_URI);
        model.setNsPrefix("sf", GeoSPARQL_URI.SF_URI);
        model.setNsPrefix("data", DATA_NAMESPACE);
        model.setNsPrefix("process", PROCESS_NAMESPACE);
        model.setNsPrefix("dcat", DCAT.getURI());
        return model;
    }

    @Override
    public Resource rasterDataGraph(Model model, RasterMetadata metadata, String parameterId, String identifier, Resource dataTheme) {
        Resource sub = model.createResource(new AnonId(identifier));
        sub.addProperty(DCAT.theme, dataTheme);
        sub.addProperty(DCTerms.type, DataOnt.RasterData);
        if (StringUtils.isNotBlank(identifier)) {
            sub.addProperty(DCTerms.identifier, identifier);
        }
        sub.addProperty(DCTerms.subject, model.createTypedLiteral(dataTheme.getLocalName()));
        if (StringUtils.isNotBlank(parameterId)) {
            sub.addProperty(DataOnt.parameterId, model.createTypedLiteral(parameterId));
        }
        sub.addProperty(DataOnt.nodataValue, model.createTypedLiteral(metadata.getNodata()));
        if ("Degree".equalsIgnoreCase(metadata.getUnit())) {
            sub.addProperty(DataOnt.hasUOM, GeoVocabulary.degree);
            sub.addProperty(GeoDCAT.spatialResolutionInDegrees, model.createTypedLiteral(metadata.getPixelSize(), XSDDatatype.XSDdecimal));
        } else if ("Metre".equalsIgnoreCase(metadata.getUnit())) {
            sub.addProperty(DataOnt.hasUOM, GeoVocabulary.meter);
            sub.addProperty(DCAT2.spatialResolutionInMeters, model.createTypedLiteral(metadata.getPixelSize(), XSDDatatype.XSDdecimal));
        }
        if ("GTiff".equalsIgnoreCase(metadata.getFormat())) {
            sub.addProperty(DCTerms.format, DataOnt.IMAGE_TIFF);
        }
        sub.addProperty(DataOnt.dataFormat, DataOnt.createResourceByName(metadata.getFormat()));
        addExtent(model, sub, metadata.getMinX(), metadata.getMinY(), metadata.getMaxX(), metadata.getMaxY());
        sub.addProperty(DataOnt.hasCRSWkt, metadata.getCrsWkt());
        sub.addProperty(DataOnt.hasCRSProj4, metadata.getCrsProj4());
        sub.addProperty(DataOnt.isProjected, model.createTypedLiteral(metadata.isProjected()));
        sub.addProperty(DCAT2.bbox, bboxWkt(metadata.getMinX(), metadata.getMinY(), metadata.getMaxX(), metadata.getMaxY()));
        sub.addProperty(DataOnt.hasSRID, model.createTypedLiteral(metadata.getSrid()));
        sub.addProperty(DataOnt.hasSRS, GeoVocabulary.createEpsg(metadata.getSrid()));
        return sub;
    }

    private void addExtent(Model model, Resource sub, double minx, double miny, double maxx, double maxy) {
        model.add(sub, DataOnt.minX, model.createTypedLiteral(new Double(minx)));
        model.add(sub, DataOnt.westBoundingCoordinate, model.createTypedLiteral(new Double(minx)));
        model.add(sub, DataOnt.maxX, model.createTypedLiteral(new Double(maxx)));
        model.add(sub, DataOnt.eastBoundingCoordinate, model.createTypedLiteral(new Double(maxx)));
        model.add(sub, DataOnt.minY, model.createTypedLiteral(new Double(miny)));
        model.add(sub, DataOnt.southBoundingCoordinate, model.createTypedLiteral(new Double(miny)));
        model.add(sub, DataOnt.maxY, model.createTypedLiteral(new Double(maxy)));
        model.add(sub, DataOnt.northBoundingCoordinate, model.createTypedLiteral(new Double(maxy)));
//       model.add(sub, DataOnt.spatialExtent, model.createTypedLiteral(str(minx) + ',' + str(miny) + ',' + str(maxx) + ',' + str(maxy), datatype = XSD.string)))
    }

    /**
     * Bbox wkt literal.
     * use default crs EPSG:4326
     *
     * @param minx the minx
     * @param miny the miny
     * @param maxx the maxx
     * @param maxy the maxy
     * @return the literal
     */
    public static Literal bboxWkt(Double minx, Double miny, Double maxx, Double maxy) {
        //没有最后一项的话，生成的是 LINESTRING
        return WKTLiteralFactory.createBox(minx, miny, maxx, maxy, GeoVocabulary.EPSG_4326.getURI());
    }

    public static Literal bboxWkt(Double minx, Double miny, Double maxx, Double maxy, Resource srs) {
        return WKTLiteralFactory.createBox(minx, miny, maxx, maxy, srs.getURI());
    }

    @Override
    public Resource vectorDataGraph(Model model, VectorMetadata metadata, String parameterId, String identifier, Resource dataTheme) {
        Resource sub = model.createResource(new AnonId(identifier));
        if ("ESRI_Shapefile".equalsIgnoreCase(metadata.getFormat())) {
            sub.addProperty(DCTerms.format, DataOnt.APP_SHP);
        }
        sub.addProperty(DataOnt.dataFormat, DataOnt.createResourceByName(metadata.getFormat()));

        if (StringUtils.isNotBlank(parameterId)) {
            sub.addProperty(DataOnt.parameterId, model.createTypedLiteral(parameterId));
        }
        sub.addProperty(DCTerms.subject, model.createTypedLiteral(dataTheme.getLocalName()));
        sub.addProperty(DCAT.theme, dataTheme);

        sub.addProperty(RDF.type, DataOnt.VectorData);
        sub.addProperty(DCAT2.bbox, bboxWkt(metadata.getMinX(), metadata.getMinY(), metadata.getMaxX(), metadata.getMaxY()));
        sub.addProperty(RDF.type, GeoVocabulary.createSimpleFeature(metadata.getGeometry()));
        sub.addProperty(GeoVocabulary.asWKT, bboxWkt(metadata.getMinX(), metadata.getMinY(), metadata.getMaxX(), metadata.getMaxY()));
        sub.addProperty(DataOnt.hasCRSWkt, metadata.getCrsWkt());
        sub.addProperty(DataOnt.hasCRSProj4, metadata.getCrsProj4());
        sub.addProperty(DataOnt.isProjected, model.createTypedLiteral(metadata.isProjected()));
        return sub;
    }

    @Override
    public Resource applicationContextGraph(Model model, String geoModelName, String purpose, String climateType, String climateInput,
                                            String spatialScale, String surfaceInput, String timeStep, String timeScale) {
        Resource app = model.createResource(purpose);
        app.addProperty(RDF.type, ApplicationContextOnt.ApplicationContext);
        app.addProperty(ApplicationContextOnt.applicationPurpose, ApplicationContextOnt.createPurpose(purpose));
        app.addProperty(ApplicationContextOnt.adoptedModel, model.createResource("http://www.egc.org/ont/model#" + geoModelName));
        app.addProperty(ApplicationContextOnt.climateInput, ApplicationContextOnt.createResource(climateInput));
        app.addProperty(ApplicationContextOnt.climateType, ApplicationContextOnt.createResource(climateType));
        app.addProperty(ApplicationContextOnt.spatialScale, ApplicationContextOnt.createResource(spatialScale));
        app.addProperty(ApplicationContextOnt.underlyingSurfaceInput, ApplicationContextOnt.createUnderlyingSurfaceInput(surfaceInput));
        app.addProperty(ApplicationContextOnt.timestep, ApplicationContextOnt.createResource(timeStep));
        app.addProperty(ApplicationContextOnt.timeScale, ApplicationContextOnt.createResource(timeScale));
        return app;
    }
}
