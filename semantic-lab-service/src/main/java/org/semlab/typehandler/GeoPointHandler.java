package org.semlab.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.postgis.PGgeometry;
import org.postgis.Point;
import org.postgresql.util.PGobject;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO
 * postgis点 Point 类型转换
 * ST_Point(float x_lon, float y_lat);
 *
 * @author houzhiwei
 * @date 2017/3/31 22:51
 */
public class GeoPointHandler extends BaseTypeHandler<Point>
{
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Point parameter, JdbcType jdbcType) throws SQLException
    {
        PGobject pGobject = new PGobject();
        pGobject.setValue(parameter.getValue());
        pGobject.setType(parameter.getTypeString());
        ps.setObject(i, pGobject);
    }

    @Override
    public Point getNullableResult(ResultSet rs, String columnName) throws SQLException
    {
        PGgeometry value = (PGgeometry) rs.getObject(columnName);
        value.getGeoType();
//        Geometry.POINT;
//        (Point)value.getGeometry();
        return null;
    }

    @Override
    public Point getNullableResult(ResultSet rs, int columnIndex) throws SQLException
    {
        return null;
    }

    @Override
    public Point getNullableResult(CallableStatement cs, int columnIndex) throws SQLException
    {
        return null;
    }
}
