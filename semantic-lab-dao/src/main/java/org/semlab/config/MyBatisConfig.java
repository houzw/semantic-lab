package org.semlab.config;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.github.pagehelper.PageHelper;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * MyBatis配置类
 *
 * @author houzhiwei
 * @date 2016/10/1 13:24
 */
@Configuration
@MapperScan(basePackages = "org.semlab.dao")
@ConfigurationProperties(prefix = "db", locations = "classpath*:config/db.yml")
@EnableTransactionManagement
public class MyBatisConfig implements TransactionManagementConfigurer
{
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
    //    从默认的application.properties中获取配置
    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "datasource.my", locations = "classpath*:/config/db.yml")
    public DataSource dataSource() throws Exception
    {
        Properties props = new Properties();
        props.put("driverClassName", env.getProperty("driverClassName"));
        props.put("url", env.getProperty("url"));
        props.put("username", env.getProperty("username"));
        props.put("password", env.getProperty("password"));
        return DruidDataSourceFactory.createDataSource(props);
    }

    @Autowired
    DataSource dataSource;

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception
    {
        final SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        PageHelper pageHelper = new PageHelper();

//        bean.setPlugins(new Interceptor[]{pageHelper});//分页插件

//        bean.setTypeAliasesPackage(alias);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        //xml path
        bean.setMapperLocations(resolver.getResources("classpath*:org/semlab/xml/*.xml"));
        return bean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplateBean(SqlSessionFactory sqlSessionFactory)
    {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager()
    {
        return new DataSourceTransactionManager(dataSource);
    }
}
