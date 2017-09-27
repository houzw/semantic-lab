package org.semlab.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * TODO
 * 使用application.yml配置，不需要单独写配置类
 * @author houzhiwei
 * @date 2017/3/27 11:39
 */
@Deprecated
@Configuration
//不能是classpath*
//新版本的boot已移除Location配置。因此，所有配置最好配置到application.yml中
//@ConfigurationProperties(prefix = "mybatis", locations = "classpath:config/mybatis.yml")
public class MyBatisSettings
{
    private final static Logger log = LoggerFactory.getLogger(MyBatisSettings.class);
    private String mapperLocations;
    private String typeAliasesPackage;
    private String typeHandlersPackage;

    // 针对下述配置（yaml），不清楚如何采用ConfigurationProperties 处理多层配置。采用传统方法：
    // @Value("${mybatis.pagehelper.params}")
    // mybatis:
    //  pagehelper:
    //    params: count=countSql

    //    pageHelper
    private String params;
    private String helperDialect;
    private boolean reasonable;
    private boolean supportMethodsArguments;

    public boolean isSupportMethodsArguments()
    {
        return supportMethodsArguments;
    }

    public void setSupportMethodsArguments(boolean supportMethodsArguments)
    {
        this.supportMethodsArguments = supportMethodsArguments;
    }

    public String getMapperLocations()
    {
        return mapperLocations;
    }

    public void setMapperLocations(String mapperLocations)
    {
        this.mapperLocations = mapperLocations;
    }

    public String getTypeAliasesPackage()
    {
        return typeAliasesPackage;
    }

    public void setTypeAliasesPackage(String typeAliasesPackage)
    {
        this.typeAliasesPackage = typeAliasesPackage;
    }

    public String getTypeHandlersPackage()
    {
        return typeHandlersPackage;
    }

    public void setTypeHandlersPackage(String typeHandlersPackage)
    {
        this.typeHandlersPackage = typeHandlersPackage;
    }

    public String getParams()
    {
        return params;
    }

    public void setParams(String params)
    {
        this.params = params;
    }

    public String getHelperDialect()
    {
        return helperDialect;
    }

    public void setHelperDialect(String helperDialect)
    {
        this.helperDialect = helperDialect;
    }

    public boolean isReasonable()
    {
        return reasonable;
    }

    public void setReasonable(boolean reasonable)
    {
        this.reasonable = reasonable;
    }
}
