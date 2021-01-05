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

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findPasswordByStudentID(String studentID);
    Student findNameByStudentID(String studentID);
    Student findAllByStudentID(String studentID);
    @Query("select s.frozen from Student s where s.studentID=?1")
    Boolean findFrozenByStudentID(String studentID);
    @Modifying
    @Transactional
    @Query("update Student s set s.frozen=?1 where s.studentID=?2")
    int saveFrozen(Boolean frozen,String studentID);
    @Modifying
    @Transactional
    @Query("update Student s set s.name=?1,s.weChat=?2, s.QQ=?3 where s.studentID=?4")
    int update(String name, String WeChat, String QQ, String studentID);
    @Modifying
    @Transactional
    @Query("update Student s set s.password=?1 where s.studentID=?2")
    int saveChangePWD(String password, String studentID);
    @Modifying
    @Transactional
    @Query("update Student s set s.onlineStatus=?1 where s.studentID=?2")
    void setOnlineStatus(Boolean onlineStatus, String studentID);

    @Query("select s from Student s where s.studentID=?1")
    Student getOnlineStatus(String studentID);
    // 根据Student ID前缀查询
    @Modifying
    @Transactional
    @Query("select s from Student s where s.studentID like ?1")
    List<Student> query01(String classPrefix);
    @Modifying
    @Transactional
    @Query("delete from Student  where studentID=?1")
    void delByStudentID(String studentId);
}
