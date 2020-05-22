//IntelliJ IDEA
//campus
//DataController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.dao;

import com.pyc.campus.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findByNameAndStudentID(String name,String studentID);
    Student findPasswordByStudentID(String studentID);
    Student findNameByStudentID(String studentID);
    @Modifying
    @Transactional
    @Query("update Student s set s.name=?1,s.weChat=?2, s.QQ=?3 where s.studentID=?4")
    int update(String name, String WeChat, String QQ, String studentID);
}
