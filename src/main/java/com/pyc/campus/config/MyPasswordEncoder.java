//IntelliJ IDEA
//campus
//MyPasswordEncoder
//2020/5/5
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.config;

import org.springframework.security.crypto.password.PasswordEncoder;

public class MyPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence charSequence) {
        return charSequence.toString();
    }

    @Override
    public boolean matches(CharSequence charSequence, String s) {
        return s.equals(charSequence.toString());
    }
}
