package org.semlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * TODO
 *
 * @Author houzhiwei
 * @Date 2016/10/1 21:05.
 */
//Spring-boot 项目主类
//根package下可以取代 @Configuration & @ComponentScan & @EnableAutoConfiguration
@SpringBootApplication
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class,args);
    }
}
