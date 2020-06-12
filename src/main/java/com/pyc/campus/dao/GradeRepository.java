//IntelliJ IDEA
//campus
//GradeRepository
//2020/6/12
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.dao;

import com.pyc.campus.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByTermAndStudentID(String term,String studentId);
    List<Grade> findAllByStudentID(String studentId);
}
