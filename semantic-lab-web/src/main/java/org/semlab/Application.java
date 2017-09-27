package org.semlab;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * TODO
 * Spring-boot 项目主类
 *
 * @author houzhiwei
 * @date 2016/10/1 21:05.
 */
//根package下可以取代 @Configuration & @ComponentScan & @EnableAutoConfiguration
@ServletComponentScan//扫描到自己编写的servlet和filter
@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties
@MapperScan(basePackages = "org.semlab.mapper") //包含了配置类 MyBatisConfig
public class Application extends SpringBootServletInitializer
{
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
        return builder.sources(Application.class);
    }

    public static void main(String[] args)
    {
        SpringApplication.run(Application.class);
        //1.5.2中不能在添加args
        //SpringApplication.run(Application.class,args);
    }
}
