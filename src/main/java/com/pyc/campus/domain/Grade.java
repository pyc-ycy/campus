//IntelliJ IDEA
//campus
//Grade
//2020/6/12
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Grade {
    @Id
    @GeneratedValue
    long id;
    private String term;    // 学期
    private  String studentID;  // 学号
    private String name; // 学生姓名
    private String courseCode;  // 课程编号
    private String courseName;  // 课程名称
    private int grade;  // 课程成绩
    private float gpa;   // 绩点
    private int learnHour;  //学时
    private int credit; // 学分
    public Grade(){
        super();
    }
    public Grade(long id, String term, String studentID, String name,
                String courseCode, String courseName, int grade, float gpa, int learnHour, int credit){
        super();
        this.term=term;
        this.studentID=studentID;
        this.name=name;
        this.courseCode =courseCode;
        this.courseName =courseName;
        this.grade=grade;
        this.gpa=gpa;
        this.learnHour =learnHour;
        this.credit=credit;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getGpa() {
        return gpa;
    }

    public int getCredit() {
        return credit;
    }

    public int getGrade() {
        return grade;
    }

    public int getLearnHour() {
        return learnHour;
    }

    public long getId() {
        return id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getTerm() {
        return term;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public void setGpa(float gpa) {
        this.gpa = gpa;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public void setLearnHour(int learnHour) {
        this.learnHour = learnHour;
    }

    public void setTerm(String term) {
        this.term = term;
    }

}
