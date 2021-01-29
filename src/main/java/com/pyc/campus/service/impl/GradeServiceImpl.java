package com.pyc.campus.service.impl;

import com.pyc.campus.dao.GradeRepository;
import com.pyc.campus.domain.Grade;
import com.pyc.campus.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file GradeServiceImpl
 * @pack com.pyc.campus.service.impl
 * @date 2021/1/29
 * @time 10:39
 * @E-mail 2923616405@qq.com
 **/

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeRepository gradeRepository;

    @Override
    public Page<Grade> getGradeListByStuID(int pageNum, int pageSize, String stuId) {
        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return gradeRepository.getAllByStudentID(stuId,pageable);
    }

    @Override
    public Page<Grade> getGradeListByStuIDDESCByGrade(int pageNum, int pageSize, String stuId) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return gradeRepository.getAllByStudentIDOrderByGradeDesc(stuId,pageable);
    }

    @Override
    public Page<Grade> getGradeListByStuIDASCByGrade(int pageNum, int pageSize, String stuId) {

        Pageable pageable = PageRequest.of(pageNum,pageSize);
        return gradeRepository.getAllByStudentIDOrderByGradeAsc(stuId,pageable);
    }
}
