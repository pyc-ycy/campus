package com.pyc.campus.config;

import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.stereotype.Component;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file MysqlConfig
 * @pack com.pyc.campus.config
 * @date 2021/1/29
 * @time 10:27
 * @E-mail 2923616405@qq.com
 **/

@Component
@SuppressWarnings("decription")
public class MysqlConfig extends MySQL8Dialect {

    @Override
    public String getTableTypeString(){
        return "ENGINE=InnoDB DEFAULT CHARSET=utf8";
    }
}
