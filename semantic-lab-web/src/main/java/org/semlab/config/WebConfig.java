package org.semlab.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * TODO
 *
 * @author houzhiwei
 * @date 2017/3/27 9:01
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "org.semlab")
public class WebConfig extends WebMvcConfigurerAdapter
{
}
