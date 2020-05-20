//IntelliJ IDEA
//campus
//SysUserRepository
//2020/5/5
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.dao;

import com.pyc.campus.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByUsername(String username);
}
