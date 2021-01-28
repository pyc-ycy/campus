package com.pyc.campus.controller;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Student;
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
 * @file StudentController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 11:40
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class StudentController {

    final
    StudentRepository studentRepository;

    public StudentController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/userCenter")
    public String userCenter(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        Msg msg = new Msg("","","");
        model.addAttribute("msg",msg);
        model.addAttribute("curUse",s);
        return "page/UserCenter";
    }

    @RequestMapping("/saveOnlineStatus")
    public String saveOnlineStatus(Model model, HttpSession session,
                                   @Param("onlineStatus")Boolean onlineStatus)
    {
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        studentRepository.setOnlineStatus(onlineStatus,currentStudentId);
        Msg msg = new Msg("","","");
        model.addAttribute("msg",msg);
        return "page/UserCenter";
    }
    @RequestMapping("/toBrowseFriendInfo")
    public String toBrowseFriendInfo(Model model, HttpSession session,
                                     @Param("toName")String toName){
        Student stu = studentRepository.findNameByStudentID(toName);
        Msg msg = new Msg("","","");
        model.addAttribute("msg",msg);
        model.addAttribute("stu",stu);
        return "page/BrowseFriendInfo";
    }

    @RequestMapping("/toChangePWD")
    public String toChangePWD(Model model,HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUser", s);
        Msg msg = new Msg("","","");
        model.addAttribute("msg",msg);
        return "page/ChangePWD";
    }
    @RequestMapping("/updateUserInfo")
    public String updateUserInfo(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        Msg msg = new Msg(
                "提示！",
                "学号作为用户标识ID，不允许更改",
                ""
        );
        model.addAttribute("msg", msg);
        return "page/UpdateUserInfo";
    }
    @RequestMapping("/updateUInfo")
    public String updateUInfo(Model model,HttpSession session,
                              @Param("username") String username,
                              @Param("weChat") String weChat,
                              @RequestParam(value = "TencentQQ", required = false) String qq){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        System.out.println("QQ:"+qq);
        int result = studentRepository.update(username,weChat,qq,currentStudentId);
        if(result==0){
            Msg msg = new Msg(
                    "提示",
                    "对不起，是我们的错，信息没有成功更新，请再试几次，或者连续网站管理员",
                    ""
            );
            model.addAttribute("msg",msg);
            return "page/UpdateUserInfo";
        }
        Msg msg = new Msg(
                "提示",
                "信息更新成功！",
                ""
        );
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        model.addAttribute("msg",msg);
        return "page/UserCenter";
    }

    @RequestMapping("/saveFrozenInTrue")
    public String saveFrozenInTrue(Model model,HttpSession session,
                                   @RequestParam(value = "studentId", required = false)String studentID){
        int t = studentRepository.saveFrozen(true,studentID);
        if(t!=0){
            System.out.println("更新成功！");
        }
        else
        {
            System.out.println("更新失败！");
        }
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "page/ManageUser";

    }

    @RequestMapping("/saveFrozenInFalse")
    public String saveFrozenInFalse(Model model,HttpSession session,
                                    @RequestParam(value = "studentId", required = false)String studentID){

        int t = studentRepository.saveFrozen(false,studentID);
        if(t!=0){
            System.out.println("更新成功！");
        }
        else
        {
            System.out.println("更新失败！");
        }
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "page/ManageUser";

    }
}
