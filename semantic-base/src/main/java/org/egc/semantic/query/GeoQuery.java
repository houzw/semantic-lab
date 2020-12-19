package org.egc.semantic.query;

import org.apache.jena.query.ResultSet;
import org.apache.jena.rdf.model.Model;
import org.locationtech.jts.geom.Geometry;

/**
 * SPARQL query based on GEOSPARQL
 * https://jena.apache.org/documentation/geosparql/index.html
 * https://github.com/galbiston/geosparql-jena
 * @author houzhiwei
 * @date 2020/10/24 16:22
 */
public interface GeoQuery {
    ResultSet within(double lat, double lng, Model model);
}
