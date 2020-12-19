package org.egc.semantic.query.impl;

import org.apache.jena.arq.querybuilder.SelectBuilder;
import org.apache.jena.geosparql.configuration.GeoSPARQLConfig;
import org.apache.jena.geosparql.configuration.GeoSPARQLOperations;
import org.apache.jena.geosparql.implementation.GeometryWrapper;
import org.apache.jena.geosparql.implementation.datatype.WKTDatatype;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.Model;
import org.egc.semantic.query.GeoQuery;


/**
 * https://github.com/galbiston/geosparql-jena
 *
 * @author houzhiwei
 * @date 2020/10/24 22:11
 */
public class GeoQueryImpl implements GeoQuery {


    @Override
    public ResultSet within(double lat, double lng, Model model) {
        GeoSPARQLOperations.applyPrefixes(model);
        GeometryWrapper geometryWrapper = WKTDatatype.INSTANCE.parse("POINT(" + lat + " " + lng + ")");
        GeoSPARQLConfig.setupMemoryIndex();
        SelectBuilder sb = new SelectBuilder();
//        sb.addPrefix("geo", GeoSPARQL_URI.GEO_URI);
        sb.addValueVar("?o");
        sb.addWhere("?s", "geo:sfWithin", "?o");
        sb.addOrderBy("?o");
        try (QueryExecution qe = QueryExecutionFactory.create(sb.build(), model)) {
            ResultSet rs = qe.execSelect();
            ResultSetFormatter.outputAsTSV(rs);
            return rs;
        }
    }
}
