//IntelliJ IDEA
//campus
//WebMVCConfig
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/aboutLearn").setViewName("page/AboutLearn");
        registry.addViewController("/aboutEnglish").setViewName("page/AboutEnglish");
        registry.addViewController("/aboutMath").setViewName("page/AboutMath");
        registry.addViewController("/aboutPhilosophy").setViewName("page/AboutPhilosophy");
        //registry.addViewController("/publicChatRoom").setViewName("PublicChatRoom");
        registry.addViewController("/chat").setViewName("page/PrivateChat");
        registry.addViewController("/signError").setViewName("page/SignError");
    }
}
