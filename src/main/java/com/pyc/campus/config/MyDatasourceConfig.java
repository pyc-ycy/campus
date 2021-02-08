package com.pyc.campus.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file MyDatasourceConfig
 * @pack com.pyc.campus.config
 * @date 2021/2/8
 * @time 8:40
 * @E-mail 2923616405@qq.com
 **/

@Configuration
public class MyDatasourceConfig {

    @ConfigurationProperties("spring.datasource")
    @Bean
    public DataSource dataSource(){
        return new DruidDataSource();
    }


    @Bean
    public ServletRegistrationBean<StatViewServlet> servletServletRegistrationBean(){
        StatViewServlet servlet = new StatViewServlet();
        return new ServletRegistrationBean<>(servlet, "/druid/*");
    }
}
