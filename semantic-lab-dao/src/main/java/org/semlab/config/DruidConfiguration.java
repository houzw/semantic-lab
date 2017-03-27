package org.semlab.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * TODO 测试
 *
 * @author houzhiwei
 * @date 2017/3/26 21:27
 * http://www.w2bc.com/article/225170
 */
@Configuration
public class DruidConfiguration
{
    private static final Logger log = LoggerFactory.getLogger(DruidConfiguration.class);
    @Bean
    public ServletRegistrationBean druidServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),"/druid/*");
        //or:
        //@WebServlet(urlPatterns = "/druid/*",
        //       initParams={@WebInitParam(name="",value="")}
        //public class DruidStatViewServlet extends StatViewServlet {}//空

        //添加初始化参数：initParams
        //白名单：
        //servletRegistrationBean.addInitParameter("allow","127.0.0.1");
        //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        //servletRegistrationBean.addInitParameter("deny","192.168.1.73");
        //登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername","admin");
        servletRegistrationBean.addInitParameter("loginPassword","123456");
        //是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable","false");
        return servletRegistrationBean;
    }

    /**
     * 配置过滤器
     **/
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        //or:
        //@WebFilter(filterName="druidWebStatFilter",urlPatterns="/*",
        //        initParams={
        //                @WebInitParam(name="exclusions",value="*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")// 忽略资源
        //        })
        //public class DruidStatFilter extends WebStatFilter {}//空
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        //过滤地址
        filterRegistrationBean.addUrlPatterns("/*");
        //不需要参与过滤的地址或者文件
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    @Bean
    public DataSource druidDataSource(@Value("${spring.datasource.driverClassName}") String driver,
                                      @Value("${spring.datasource.url}") String url,
                                      @Value("${spring.datasource.username}") String username,
                                      @Value("${spring.datasource.password}") String password,
                                      @Value("${spring.datasource.initialSize}")int initialSize,
                                      @Value("${spring.datasource.minIdle}") int minIdle,
                                      @Value("${spring.datasource.maxActive}") int maxActive) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(driver);
        druidDataSource.setUrl(url);
        druidDataSource.setUsername(username);
        druidDataSource.setPassword(password);
        druidDataSource.setInitialSize(initialSize);
        druidDataSource.setMinIdle(minIdle);
        druidDataSource.setMaxActive(maxActive);

        try {
            druidDataSource.setFilters("stat, wall");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return druidDataSource;
    }
}
