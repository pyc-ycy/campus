package com.pyc.campus.dao;

import com.pyc.campus.domain.SaveUserPasswordEncode;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
