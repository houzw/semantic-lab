package org.semlab.dao;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.io.Serializable;
import java.util.List;

/**
 * 测试：生成基于注解的mapper，不生成xml
 *
 * @Author houzhiwei
 * @Date 2016/10/1 13:59.
 */
public interface BaseMapper<T, I extends Serializable>
{
    @Delete("delete from t_user where id = #{id}")
    //t_user表名如何通用？
    int deleteByPrimaryKey(I id);

    @Insert("")
    int insert(T record);

    @Select("")
    T selectByPrimaryKey(I id);

    @Select(" select id, first_name, password, salt, email, last_name, reg_date, title, dept, research_field,locked from t_user")
    List<T> selectAll();

    @Update("")
    int updateByPrimaryKey(T record);
}
