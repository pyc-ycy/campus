package com.pyc.campus.service.impl;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.Student;
import com.pyc.campus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file StudentServiceImpl
 * @pack com.pyc.campus.service.impl
 * @date 2021/1/29
 * @time 14:46
 * @E-mail 2923616405@qq.com
 **/

@Service
public class StudentServiceImpl implements StudentService {

    final
    StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Page<Student> getStudentList(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return studentRepository.findAll(pageable);
    }

    @Override
    public Page<Student> getStudentListByStuIdLike(int pageNum, int pageSize, String like) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return studentRepository.query01(like,pageable);
    }
}
