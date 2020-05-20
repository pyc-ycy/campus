//IntelliJ IDEA
//campus
//DataController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.dao;

import com.pyc.campus.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByNameAndStudentID(String name,String studentID);
    Student findPasswordByStudentID(String studentID);
}
