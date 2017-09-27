package org.semlab.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * mapper 扫描配置
 * 直接在MyBatisConfig中使用注解@MapperScan
 * @author houzhiwei
 * @date 2016/10/1 19:47.
 */
@Configuration
@AutoConfigureAfter(MyBatisConfig.class)
public class MyBatisMapperScannerConfig
{
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer()
    {
        MapperScannerConfigurer config = new MapperScannerConfigurer();
        config.setSqlSessionFactoryBeanName("sqlSessionFactory");
        config.setBasePackage("org.semlab.dao");
        return config;
    }
}