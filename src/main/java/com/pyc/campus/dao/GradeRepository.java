//IntelliJ IDEA
//campus
//GradeRepository
//2020/6/12
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.dao;

import com.pyc.campus.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    List<Grade> findAllByTermAndStudentID(String term,String studentId);
    List<Grade> findAllByStudentID(String studentId);
    @RestResource(path="findAllByName", rel="findAllByName")
    List<Grade> findAllByName(@Param("name")String name);
    // 按成绩降序排列
    //@Query("select Grade from Grade g where g.studentID=?1 order by g.grade desc ")
    List<Grade> findAllByStudentIDOrderByGradeDesc(String stuID);
    //按成绩升序排列
    List<Grade> findAllByStudentIDOrderByGradeAsc(String stuID);
    // 查找最低成绩
    @Query("select min(g.grade) from Grade g where g.studentID=?1")
    int findMinGrade(String stuID);
    // 查找最高成绩
    @Query("select max(g.grade) from Grade g where g.studentID=?1")
    int findMaxGrade(String stuID);
    // 查找总学分
    @Query("select sum(g.credit) from Grade g where g.studentID=?1")
    int findSumCredit(String stuID);
    // 查找总平均绩点
    @Query("select avg(g.gpa) from Grade g where g.studentID=?1")
    float findAvgGPA(String stuID);

}
