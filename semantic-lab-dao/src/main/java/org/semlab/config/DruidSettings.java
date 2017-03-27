package org.semlab.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/3/27 8:32
 */
@Component
@ConfigurationProperties(prefix = "druid", locations = "classpath*:/config/druid.yml")
public class DruidSettings
{
    private int initialSize;
    private int maxActive;
    private int minIdle;
    private int maxWait;
    private int timeBetweenEvictionRunsMillis;
    private int minEvictableIdleTimeMillis;
    private String validationQuery;
    private boolean testWhileIdle;
    private boolean testOnBorrow;
    private boolean testOnReturn;
    private boolean poolPreparedStatements;
    private int maxPoolPreparedStatementPerConnectionSize;
    private List<String> filters;
    private boolean logAbandoned;

    //    region getters & setters
    public int getInitialSize()
    {
        return initialSize;
    }

    public void setInitialSize(int initialSize)
    {
        this.initialSize = initialSize;
    }

    public int getMaxActive()
    {
        return maxActive;
    }

    public void setMaxActive(int maxActive)
    {
        this.maxActive = maxActive;
    }

    public int getMinIdle()
    {
        return minIdle;
    }

    public void setMinIdle(int minIdle)
    {
        this.minIdle = minIdle;
    }

    public int getMaxWait()
    {
        return maxWait;
    }

    public void setMaxWait(int maxWait)
    {
        this.maxWait = maxWait;
    }

    public int getTimeBetweenEvictionRunsMillis()
    {
        return timeBetweenEvictionRunsMillis;
    }

    public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis)
    {
        this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
    }

    public int getMinEvictableIdleTimeMillis()
    {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis)
    {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public String getValidationQuery()
    {
        return validationQuery;
    }

    public void setValidationQuery(String validationQuery)
    {
        this.validationQuery = validationQuery;
    }

    public boolean isTestWhileIdle()
    {
        return testWhileIdle;
    }

    public void setTestWhileIdle(boolean testWhileIdle)
    {
        this.testWhileIdle = testWhileIdle;
    }

    public boolean isTestOnBorrow()
    {
        return testOnBorrow;
    }

    public void setTestOnBorrow(boolean testOnBorrow)
    {
        this.testOnBorrow = testOnBorrow;
    }

    public boolean isTestOnReturn()
    {
        return testOnReturn;
    }

    public void setTestOnReturn(boolean testOnReturn)
    {
        this.testOnReturn = testOnReturn;
    }

    public boolean isPoolPreparedStatements()
    {
        return poolPreparedStatements;
    }

    public void setPoolPreparedStatements(boolean poolPreparedStatements)
    {
        this.poolPreparedStatements = poolPreparedStatements;
    }

    public int getMaxPoolPreparedStatementPerConnectionSize()
    {
        return maxPoolPreparedStatementPerConnectionSize;
    }

    public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize)
    {
        this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
    }

    public List<String> getFilters()
    {
        return filters;
    }

    public void setFilters(List<String> filters)
    {
        this.filters = filters;
    }

    public boolean isLogAbandoned()
    {
        return logAbandoned;
    }

    public void setLogAbandoned(boolean logAbandoned)
    {
        this.logAbandoned = logAbandoned;
    }

//endregion
}
