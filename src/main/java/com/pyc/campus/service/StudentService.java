package com.pyc.campus.service;

import com.pyc.campus.domain.Student;
import org.springframework.data.domain.Page;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file StudentService
 * @pack com.pyc.campus.service
 * @date 2021/1/29
 * @time 14:44
 * @E-mail 2923616405@qq.com
 **/
public interface StudentService {
    Page<Student> getStudentList(int pageNum, int pageSize);

    Page<Student> getStudentListByStuIdLike(int pageNum, int pageSize, String like);
}
