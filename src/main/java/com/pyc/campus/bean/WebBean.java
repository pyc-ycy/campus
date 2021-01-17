package com.pyc.campus.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file WebBean
 * @pack com.pyc.campus.bean
 * @date 2021/1/17
 * @time 11:18
 * @E-mail 2923616405@qq.com
 **/


@EnableScheduling
@ComponentScan("com.pyc.campus.bean")
public class WebBean implements WebMvcConfigurer {
    @Bean
    public MultipartResolver multipartResolver(){
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(1000000);
        return multipartResolver;
    }
}
