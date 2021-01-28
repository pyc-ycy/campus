package com.pyc.campus.controller;

import com.pyc.campus.dao.GradeRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.Grade;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Student;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file GradeController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 11:46
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class GradeController {

    final GradeRepository gradeRepository;

    final StudentRepository studentRepository;

    public GradeController(GradeRepository gradeRepository,StudentRepository studentRepository){
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/desc")
    public String desc(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        if(gradeRepository.findAllByStudentID(currentStudentId)!=null){
            try {

                List<Grade> gradeListDesc = gradeRepository.findAllByStudentIDOrderByGradeDesc(currentStudentId);
                model.addAttribute("gradeItems", gradeListDesc);
                int minGrade = gradeRepository.findMinGrade(currentStudentId);
                model.addAttribute("minGrade", minGrade);
                int maxGrade = gradeRepository.findMaxGrade(currentStudentId);
                int sumCredit = gradeRepository.findSumCredit(currentStudentId);
                float avgGPA = gradeRepository.findAvgGPA(currentStudentId);
                model.addAttribute("maxGrade", maxGrade);
                model.addAttribute("sumCredit", sumCredit);
                model.addAttribute("avgGPA",avgGPA);


            } catch (Exception e) {
                System.out.println(e.getMessage());
                model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("gradeItems", null);
            }
        }else {
            model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("gradeItems", null);
        }

        return "page/QueryGrade";
    }
    @RequestMapping("/asc")
    public String asc(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        if(gradeRepository.findAllByStudentID(currentStudentId)!=null){
            try {
                List<Grade> gradeListAsc = gradeRepository.findAllByStudentIDOrderByGradeAsc(currentStudentId);
                int minGrade = gradeRepository.findMinGrade(currentStudentId);
                int maxGrade = gradeRepository.findMaxGrade(currentStudentId);
                int sumCredit = gradeRepository.findSumCredit(currentStudentId);
                float avgGPA = gradeRepository.findAvgGPA(currentStudentId);
                model.addAttribute("minGrade", minGrade);
                model.addAttribute("maxGrade", maxGrade);
                model.addAttribute("sumCredit", sumCredit);
                model.addAttribute("avgGPA",avgGPA);
                model.addAttribute("gradeItems", gradeListAsc);
            } catch (Exception e) {
                System.out.println(e.getMessage());
                model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("gradeItems", null);
            }
        } else {
            model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("gradeItems", null);
        }
        return "page/QueryGrade";
    }

    @RequestMapping("/toQueryGrade")
    public String toQueryGrade(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        if(gradeRepository.findAllByStudentID(currentStudentId)!=null){
            try {
                List<Grade> gradeList = gradeRepository.findAllByStudentID(currentStudentId);
                int minGrade = gradeRepository.findMinGrade(currentStudentId);
                int maxGrade = gradeRepository.findMaxGrade(currentStudentId);
                int sumCredit = gradeRepository.findSumCredit(currentStudentId);
                float avgGPA = gradeRepository.findAvgGPA(currentStudentId);
                model.addAttribute("minGrade", minGrade);
                model.addAttribute("maxGrade", maxGrade);
                model.addAttribute("sumCredit", sumCredit);
                model.addAttribute("avgGPA",avgGPA);
                model.addAttribute("gradeItems", gradeList);
            }catch (Exception e){
                System.out.println(e.getMessage());
                model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
                model.addAttribute("gradeItems", null);
            }
        }else {
            model.addAttribute("minGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("maxGrade", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("sumCredit", "未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("avgGPA","未查到你的课程成绩，亲您联系科任老师！");
            model.addAttribute("gradeItems", null);
        }
        return "page/QueryGrade";
    }
    @RequestMapping("/queryByTerm")
    public String queryByTerm(Model model, HttpSession session, @Param("term")String term){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        List<Grade> gradeLists = gradeRepository.findAllByTermAndStudentID(term, currentStudentId);
        model.addAttribute("gradeItems", gradeLists);
        return "page/QueryGrade";
    }

    @RequestMapping("/toImportGrade")
    public String toImportGrade(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        Msg msg = new Msg("","","");
        model.addAttribute("msg", msg);
        return "page/ImportGrade";
    }
}
