package org.semlab.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import javax.sql.DataSource;

/**
 * MyBatis配置类: mybatis-spring方式
 * 区别于mybatis-spring-boot-starter方式
 * @author houzhiwei
 * @date 2016/10/1 13:24
 * @date 2017/5/24
 */
@Configuration
@MapperScan(basePackages = "org.semlab.dao",sqlSessionFactoryRef = "sqlSessionFactory")
@AutoConfigureAfter(DataSourcesConfig.class)
public class MyBatisConfig
{
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);

    private static final String DEFAULT_TYPE_ALIASES_PACKAGE = "org.semlab.model";
    private static final String DEFAULT_MAPPER_LOCATION = "classpath*:org/semlab/mapper/*.xml";

    //从默认的application.yml中获取配置
    @Autowired
    private Environment env;

    @Autowired
    @Qualifier(value = "masterDataSource")
    DataSource dataSource;

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        PageHelper pageHelper = new PageHelper();
        // Properties properties = new Properties();
        // properties.setProperty("reasonable", "true");
        // pageHelper.setProperties(properties);
        //bean.setPlugins(new Interceptor[]{pageHelper});//分页插件

        bean.setTypeAliasesPackage(env.getProperty("mybatis.type-aliases-package",DEFAULT_TYPE_ALIASES_PACKAGE));
        //xml
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //mapper path
        String mapperLocations = env.getProperty("mybatis.mapper-locations",DEFAULT_MAPPER_LOCATION);
        bean.setMapperLocations(resolver.getResources(mapperLocations));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateBean(SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}
