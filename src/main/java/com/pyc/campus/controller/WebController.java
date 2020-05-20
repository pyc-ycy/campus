//IntelliJ IDEA
//campus
//WebController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.dao.SysUserRepository;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Student;
import com.pyc.campus.domain.SysRole;
import com.pyc.campus.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SysUserRepository sysUserRepository;

    @RequestMapping("/home")
    public String home(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Msg msg = new Msg(
                "欢迎",
                currentStudentId+",同学，欢迎使用 Campus！",
                ""
        );
        model.addAttribute("msg", msg);
        return "page/Home";
    }
    @RequestMapping("/signUP")
    public String signUP(Model model,
                         @Param("studentID") String studentID,
                         @Param("password") String password,
                         @Param("username") String username,
                         @Param("weChat") String weChat,
                         @Param("QQ") String qq) {
        Student student = studentRepository.findPasswordByStudentID(studentID);
        if (student != null) {
            return "page/SignError";
        }
        Msg msg = new Msg("注册结果","恭喜"+studentID+",你成功注册，请使用刚刚注册的学号和密码进行登录","额外信息");
        Student s = new Student(username, studentID, password, weChat, qq);
        studentRepository.save(s);
        int rs = (int) sysUserRepository.count();
        SysUser user = new SysUser();
        SysRole role = new SysRole();
        role.setId((long) 2);
        role.setName("ROLE_USER");
        List<SysRole> ls = new ArrayList<>();
        ls.add(role);
        user.setId((long) rs + 1);
        user.setRoles(ls);
        user.setUsername(studentID);
        user.setPassword(password);
        sysUserRepository.save(user);
        model.addAttribute("msg", msg);
        return "page/Login";
    }

    @RequestMapping("/")
    public String oppo() {
        return "page/Oppo";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        Msg msg = new Msg("欢迎登录","请输入你的注册学号和对应的密码","");
        model.addAttribute("msg",msg);
        return "page/Login";
    }

    @RequestMapping("/sign")
    public String sign(Model model) {
        Msg msg = new Msg("欢迎注册","请输入你的学号和对应的密码以及其他相关信息","");
        model.addAttribute("msg",msg);
        return "page/Sign";
    }
}
