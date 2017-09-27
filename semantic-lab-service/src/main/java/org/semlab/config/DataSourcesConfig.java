package org.semlab.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * 数据库配置
 * 参考：http://www.cnblogs.com/softidea/p/5699969.html
 *
 * @Author houzhiwei
 * @Date 2016/10/1 19:45.
 */
@Configuration
@EnableTransactionManagement
public class DataSourcesConfig implements TransactionManagementConfigurer
{
    private static Logger log = LoggerFactory.getLogger(DataSourcesConfig.class);

    @Primary
    @Bean(name = "masterDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.master-db")
    public DataSource druidDataSource()
    {
        return new DruidDataSource();
    }

    //multiple datasources

    @Bean(name = "clusterDruidDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.cluster-db")
    public DataSource serverDruidDataSource()
    {
        return new DruidDataSource();
    }

    @Autowired
    @Qualifier("masterDataSource")
    DataSource masterDataSource;

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager()
    {
        return new DataSourceTransactionManager(masterDataSource);
    }
}
