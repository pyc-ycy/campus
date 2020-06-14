//IntelliJ IDEA
//campus
//MailConfig
//2020/6/14
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfig {
    @Bean
    public JavaMailSender getMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.163.com");
        mailSender.setUsername("15014366986@163.com");
        mailSender.setPassword("17845wy");
        mailSender.setPort(25);
        mailSender.setProtocol("smtp");
        return mailSender;
    }
}
