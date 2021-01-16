//IntelliJ IDEA
//campus
//WebSecurityConfig
//2020/5/5
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.config;

import com.pyc.campus.security.CustomUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Bean
    UserDetailsService customUserService(){
        return new CustomUserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService()).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement()
                .maximumSessions(1).expiredUrl("/login");
        http.authorizeRequests()
                .antMatchers("/campus").permitAll()
                .antMatchers("/aboutLearn").permitAll()
                .antMatchers("/aboutEnglish").permitAll()
                .antMatchers("/aboutMath").permitAll()
                .antMatchers("/aboutPhilosophy").permitAll()
                .antMatchers("/sign").permitAll()
                .antMatchers("/sign?**").permitAll()
                .antMatchers("/toCheckFrozen").permitAll()
                .antMatchers("/static/**").permitAll()
                .antMatchers("/static/css/bootstrap.min.css").permitAll()
                .antMatchers("/static/js/bootstrap.min.js").permitAll()
                .antMatchers("/static/**/**").permitAll()
                .antMatchers("/static/**/**/**").permitAll()
                .antMatchers("/images/**/**").permitAll()
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .failureUrl("/login?error")
                .successForwardUrl("/home")
                .defaultSuccessUrl("/home")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/campus").invalidateHttpSession(true)
                .permitAll();
    }

}
