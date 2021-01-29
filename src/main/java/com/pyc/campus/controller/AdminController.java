package com.pyc.campus.controller;

import com.pyc.campus.dao.GradeRepository;
import com.pyc.campus.dao.QuestionRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.dao.SysUserRepository;
import com.pyc.campus.domain.Grade;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Question;
import com.pyc.campus.domain.Student;
import com.pyc.campus.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file AdminController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 12:01
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class AdminController {
    final StudentRepository studentRepository;

    final GradeRepository gradeRepository;

    final QuestionRepository questionRepository;

    final SysUserRepository sysUserRepository;

    final StudentService studentService;
    public AdminController(StudentRepository studentRepository,
                           GradeRepository gradeRepository, SysUserRepository sysUserRepository,
                           QuestionRepository questionRepository, StudentService studentService){
        this.studentRepository = studentRepository;
        this.gradeRepository = gradeRepository;
        this.sysUserRepository = sysUserRepository;
        this.questionRepository = questionRepository;
        this.studentService = studentService;
    }
    @RequestMapping("/admin")
    public String admin(Model model,HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "page/AdminPage";
    }

    @RequestMapping("/delStuByStuId")
    public String delStuByStuId(Model model, HttpSession session,
                                @RequestParam(value = "studentId", required = false)String stuId,
                                @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        studentRepository.delByStudentID(stuId);
        sysUserRepository.delByUsername(stuId);
        Page<Student> students = studentService.getStudentList(pageNum,pageSize);
        model.addAttribute("students", students);
        model.addAttribute("prefix","/delStuByStuId");
        return "page/ManageUser";
    }

    @RequestMapping("/manageUser")
    public String findUserExceptCurUser(Model model,HttpSession session,
                                        @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                        @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<Student> students = studentService.getStudentList(pageNum,pageSize);
        model.addAttribute("prefix","/manageUser");
        model.addAttribute("students", students);
        return "page/ManageUser";
    }
    @RequestMapping("/findUserByStudentIDLike")
    public String findUserByStudentIDLike(Model model, HttpSession session,
                                          @RequestParam(value = "ClassPrefix", required = false)String classPrefix,
                                          @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                          @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<Student> students = studentService.getStudentListByStuIdLike(pageNum,pageSize,classPrefix);
        model.addAttribute("students", students);
        model.addAttribute("prefix","/findUserByStudentIDLike");
        return "page/ManageUser";
    }

    @RequestMapping("/publishQuestion")
    public String publishQuestion(Model model, HttpSession session,
                                  @Param("mail")String mail,
                                  @RequestParam(value = "PublisherName", required = false)String publisher,
                                  @RequestParam(value = "QuestionType", required = false)String type,
                                  @Param("content")String content, @Param("reward")String reward){
        try{
            Question question = new Question();
            question.setPublisher(publisher);
            question.setMail(mail);
            question.setType(type);
            question.setContent(content);
            question.setReward(reward);
            questionRepository.save(question);
            Msg msg = new Msg("提示", "发布成功！","");
            model.addAttribute("msg",msg);
            return "page/PublishQuestion";
        }catch (Exception e){
            System.out.println(e.getMessage());
            Msg msg = new Msg("提示","发生了一些错误，请重新尝试！","");
            model.addAttribute("msg",msg);
            return "page/PublishQuestion";
        }
    }

    @RequestMapping("/saveGrade")
    public String saveGrade(Model model, HttpSession session,
                            @Param("term")String term,@Param("CourseCode")String courseCode,
                            @Param("CourseName")String courseName, @Param("credit")int credit,
                            @Param("GPA")String GPA, @Param("LearnHour")String LearnHour,
                            @RequestParam(value = "StudentGrade", required = false)String studentGrade,
                            @RequestParam(value = "studentName", required = false)String name, @Param("studentID")String studentID)
    {
        System.out.println(name);
        float gpa = Float.parseFloat(GPA);
        int learnHour = Integer.parseInt(LearnHour);
        int grade = Integer.parseInt(studentGrade);
        Grade g = new Grade();
        g.setTerm(term);
        g.setCourseCode(courseCode);
        g.setCourseName(courseName);
        g.setCredit(credit);
        g.setGpa(gpa);
        g.setLearnHour(learnHour);
        g.setGrade(grade);
        g.setName(name);
        g.setStudentID(studentID);
        gradeRepository.save(g);
        Msg msg = new Msg("提示","导入成功","");
        model.addAttribute("msg",msg);
        return "page/ImportGrade";
    }
}
