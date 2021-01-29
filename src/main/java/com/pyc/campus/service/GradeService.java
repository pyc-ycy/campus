package com.pyc.campus.service;

import com.pyc.campus.domain.Grade;
import org.springframework.data.domain.Page;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file GradeService
 * @pack com.pyc.campus.service
 * @date 2021/1/29
 * @time 10:34
 * @E-mail 2923616405@qq.com
 **/
public interface GradeService {

    Page<Grade> getGradeListByStuID(int pageNum, int pageSize,String stuId);

    Page<Grade> getGradeListByStuIDDESCByGrade(int pageNum, int pageSize,String stuId);

    Page<Grade> getGradeListByStuIDASCByGrade(int pageNum, int pageSize,String stuId);

}
