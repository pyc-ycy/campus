//IntelliJ IDEA
//campus
//SysUserRepository
//2020/5/5
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.dao;

import com.pyc.campus.domain.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SysUserRepository extends JpaRepository<SysUser,Long> {
    SysUser findByUsername(String username);
    @Modifying
    @Transactional
    @Query("update SysUser s set s.password=?1 where s.username=?2")
    int updatePassword(String password, String username);
    @Modifying
    @Transactional
    @Query("delete from SysUser where username=?1")
    void delByUsername(String username);
}
