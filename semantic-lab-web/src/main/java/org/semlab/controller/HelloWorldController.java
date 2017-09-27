package org.semlab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/3/26 17:41
 */
@RestController
public class HelloWorldController
{
    @Autowired
    @Qualifier("druidDataSource")
    DataSource dataSource;

    @Autowired
    private Environment env;

    @GetMapping("/hello")
    public  String index()
    {
        String mapperLocations = env.getProperty("mybatis.pagehelper.reasonable");
        System.out.println(mapperLocations);
//        return dataSource.toString();
//        return settings.getHelperDialect();
        return dataSource.toString();
    }
}
