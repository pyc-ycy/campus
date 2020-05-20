//IntelliJ IDEA
//campus
//DataController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Controller
public class DataController {
    @Autowired
    StudentRepository studentRepository;

    @RequestMapping("q1")
    public Student q1(String name,String studentID){
        return studentRepository.findByNameAndStudentID(name,studentID);
    }

//    @RequestMapping("/loginUp")
//    public String loginUp(@Param("studentID")String studentID,
//                          @Param("password")String password){
//        Student student = studentRepository.findPasswordByStudentID(studentID);
//        String temp = student.getPassword();
//        if(password.equals(temp)){
//            return "page/Home.html";
//        }
//        return "page/Login.html";
//    }
}
