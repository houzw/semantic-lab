package org.egc;

import org.apache.jena.geosparql.implementation.SRSInfo;
import org.apache.jena.geosparql.implementation.WKTLiteralFactory;
import org.apache.jena.geosparql.implementation.datatype.WKTDatatype;
import org.apache.jena.geosparql.implementation.vocabulary.Geo;
import org.apache.jena.rdf.model.*;
import org.apache.jena.riot.Lang;
import org.egc.semantic.geospatial.GlobalLinkedOpenData;
import org.egc.semantic.utils.OntFileUtils;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author houzhiwei
 * @date 2020/12/9 16:58
 */
public class DCATTest {

    @Test
    public void test() throws IOException {
        Model model = GlobalLinkedOpenData.getInstance().getModel();
        OntFileUtils.save(model, "J:/demos/ttl/dswm.ttl", Lang.TURTLE);
    }

    @Test
    public void testWkt() {
        //LINESTRING
        Literal box = WKTLiteralFactory.createBox(0.2, 0.2, 2d, 2d);
        System.out.println(box);
    }

    @Test
    public void testWkt2() {
        Model MODEL = ModelFactory.createDefaultModel();
        Resource res = MODEL.createResource("http://example.org/GeometryA");
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("POINT(1.0 1.0)", WKTDatatype.INSTANCE));
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("POINT(2.0 2.0)", WKTDatatype.INSTANCE));
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("POINT(3.0 3.0)", WKTDatatype.INSTANCE));
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("<http://www.opengis.net/def/crs/EPSG/0/4326> POINT(1.0 1.0)", WKTDatatype.INSTANCE));
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("<http://www.opengis.net/def/crs/EPSG/0/4326> POINT(2.0 2.0)", WKTDatatype.INSTANCE));
        MODEL.addLiteral(res, Geo.HAS_SERIALIZATION_PROP, ResourceFactory.createTypedLiteral("<http://www.opengis.net/def/crs/EPSG/0/27700> POINT(1.0 1.0)", WKTDatatype.INSTANCE));
    }

    @Test
    public void testEpsg() {
        SRSInfo info = new SRSInfo(4326);
    }

    @Test
    public void testSis2() throws SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
        Connection conn = DriverManager.getConnection("jdbc:derby:classpath:SIS_DATA/Databases/SpatialMetadata");
    }

    @Test
    public void testDate() {
        setEndDate(2008, 1, -1);
    }

    public void setEndDate(int year, int month, int day) {
        Calendar instance = Calendar.getInstance();
        if (month < 0 && day < 0) {
            instance.set(Calendar.YEAR, year);
            instance.set(Calendar.MONTH, 0);
            instance.set(Calendar.DAY_OF_MONTH, 1);
        } else if (day < 0) {
            instance.set(Calendar.YEAR, year);
            instance.set(Calendar.MONTH, month - 1);
            instance.set(Calendar.DAY_OF_MONTH, 1);
        } else {
            instance.set(Calendar.YEAR, year);
            instance.set(Calendar.MONTH, month-1);
            instance.set(Calendar.DAY_OF_MONTH, day);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(format.format(instance.getTime()));
    }
}
