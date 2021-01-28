package com.pyc.campus.dao;

import com.pyc.campus.domain.SaveUserPasswordEncode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file SaveUserPasswordEncodeRepository
 * @pack com.pyc.campus.dao
 * @date 2021/1/18
 * @time 11:01
 * @E-mail 2923616405@qq.com
 **/
public interface SaveUserPasswordEncodeRepository extends JpaRepository<SaveUserPasswordEncode, Long> {
    SaveUserPasswordEncode findAllByStuID(String stuID);
    @Modifying
    @Transactional
    @Query("update SaveUserPasswordEncode sp set sp.encodePassword=?1 where sp.stuID=?2")
    int updateEncodePasswordByStuID(String encodePassword, String stuID);
}
