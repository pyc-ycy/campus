//IntelliJ IDEA
//campus
//CustomUserService
//2020/5/5
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.security;

import com.pyc.campus.bean.OptPassword;
import com.pyc.campus.dao.SaveUserPasswordEncodeRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.dao.SysUserRepository;
import com.pyc.campus.domain.SaveUserPasswordEncode;
import com.pyc.campus.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserService implements UserDetailsService {

    @Autowired
    SysUserRepository sysUserRepository;
    @Autowired
    StudentRepository studentRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserRepository.findByUsername(username);
        boolean key = studentRepository.findFrozenByStudentID(username);
        if (key){
            throw new UsernameNotFoundException("账号处于冻结状态");
        }
        else if(user == null){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return user;
    }
}
