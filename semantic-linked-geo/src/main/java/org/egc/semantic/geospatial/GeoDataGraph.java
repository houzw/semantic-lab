package org.egc.semantic.geospatial;

import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.Resource;
import org.egc.commons.gis.RasterMetadata;
import org.egc.commons.gis.VectorMetadata;

/**
 * @author houzhiwei
 * @date 2020/12/6 15:03
 */
public interface GeoDataGraph {

    String DATA_NAMESPACE = "http://www.egc.org/ont/data#";
    String PROCESS_NAMESPACE = "http://www.egc.org/ont/process#";
    String UNIT_NAMESPACE = "http://qudt.org/vocab/unit/";

    Model initDataGraphModel();

    Resource rasterDataGraph(Model model, RasterMetadata metadata, String parameterId, String identifier, Resource dataTheme);

    Resource vectorDataGraph(Model model, VectorMetadata metadata, String parameterId, String identifier, Resource dataTheme);

    Resource applicationContextGraph(Model model, String geoModelName, String purpose, String climateType, String climateInput,
                                 String spatialScale, String surfaceInput, String timeStep, String timeScale);
}
