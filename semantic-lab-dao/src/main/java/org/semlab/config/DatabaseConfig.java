package org.semlab.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * 数据库配置 实验性质
 * 参考：http://www.cnblogs.com/softidea/p/5699969.html
 *
 * @Author houzhiwei
 * @Date 2016/10/1 19:45.
 */
//@Configuration
//将其他属性文件数据注入到Environment中。
//或者使用
@ConfigurationProperties(prefix = "spring.datasource", locations="classpath:config/db.yml")
//@PropertySource("classpath*:config/db.properties") //不支持yml文件
//@EnableTransactionManagement
public class DatabaseConfig implements EnvironmentAware
{
    private RelaxedPropertyResolver propertyResolver;
    private static Logger log = LoggerFactory.getLogger(DatabaseConfig.class);
    @Override
    public void setEnvironment(Environment environment)
    {
        this.propertyResolver = new RelaxedPropertyResolver(environment, "db");
    }

    //若有多个数据库，用@Primary指定主数据库
    @Bean(destroyMethod = "close", initMethod = "init")
    public DataSource dataSource()
    {
        log.debug("Configruing DataSource");
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(propertyResolver.getProperty("url"));
        dataSource.setDriverClassName(propertyResolver.getProperty("driverClass"));
        dataSource.setUsername(propertyResolver.getProperty("username"));
        dataSource.setPassword(propertyResolver.getProperty("password"));
        return dataSource;
    }
}
