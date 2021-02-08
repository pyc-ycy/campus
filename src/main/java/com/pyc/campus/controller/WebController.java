//IntelliJ IDEA
//campus
//WebController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.bean.OptPassword;
import com.pyc.campus.dao.SaveUserPasswordEncodeRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.dao.SysUserRepository;
import com.pyc.campus.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    final StudentRepository studentRepository;
    final
    SysUserRepository sysUserRepository;

    final SaveUserPasswordEncodeRepository saveUserPasswordEncodeRepository;
    public WebController(SysUserRepository sysUserRepository,
                         StudentRepository studentRepository,
                         SaveUserPasswordEncodeRepository saveUserPasswordEncodeRepository) {
        this.sysUserRepository = sysUserRepository;
        this.saveUserPasswordEncodeRepository = saveUserPasswordEncodeRepository;
        this.studentRepository = studentRepository;
    }
    @RequestMapping("/campus")
    public String campus() {
        return "page/Index";
    }

    @RequestMapping("/toCheckPassword")
    public String toCheckPassword(Model model){
        Msg msg = new Msg("提示","请先进行密码验证","");
        model.addAttribute("msg",msg);
        return "page/CheckPassword";
    }

    @RequestMapping("/my/check")
    public String checkPassword(Model model,HttpSession session,
                                @RequestParam("stuID")String stuID,
                                @RequestParam("password")String password){
        SaveUserPasswordEncode sp = saveUserPasswordEncodeRepository.findAllByStuID(stuID);
        String decodePassword = "";
        try{
            OptPassword op = new OptPassword();
            decodePassword = op.decrypt(sp.getEncodePassword());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(stuID + ", " + password + ", " + decodePassword);
        Msg msg;
        if(decodePassword.equals(password)){
            session.setAttribute("loginUser",stuID);
            msg = new Msg("提示","密码校验正确，请重新输入并单击登陆按钮进行登陆","");
            model.addAttribute("msg",msg);
            return "page/Login";
        }else {
            msg = new Msg("提示","密码校验错误！","");
            model.addAttribute("msg",msg);
            return "page/CheckPassword";
        }

    }
    @RequestMapping("/home")
    public String home(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        Msg msg = new Msg(
                "欢迎",
                s.getName()+",同学，欢迎使用 Campus！",
                ""
        );
        model.addAttribute("msg", msg);
        return "page/Home";
    }

    @RequestMapping("/learn")
    public String learn(Model model,HttpSession session){
        return "page/Learn";
    }
    @RequestMapping("/toEnglish")
    public String toEnglish(Model model,HttpSession session){
        return "page/English";
    }
    @RequestMapping("/toMath")
    public String toMath(Model model,HttpSession session){
        return "page/Math";
    }
    @RequestMapping("/toPhilosophy")
    public String toPhilosophy(Model model,HttpSession session){
        return "page/Philosophy";
    }

    @RequestMapping("/saveChangePWD")
    public String saveChangePWD(Model model,HttpSession session,
                                @Param("password")String password){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        int key = studentRepository.saveChangePWD(password,s.getStudentID());
        String encodePassword = "";
        try{
            OptPassword op = new OptPassword();
            encodePassword = op.encrypt(password);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        if(key!=0){
            int r = saveUserPasswordEncodeRepository.updateEncodePasswordByStuID(encodePassword,currentStudentId);
            int t = sysUserRepository.updatePassword(password,s.getStudentID());
            if(t>0 && r>0){
                return "page/Index";
            }else {
                Msg msg = new Msg("提示！","修改密码出错，请重新尝试！！！","");
                model.addAttribute("msg",msg);
                return "page/ChangePWD";
            }
        }else{
            Msg msg = new Msg("提示！","修改密码出错，请重新尝试！！！","");
            model.addAttribute("msg",msg);
            return "page/ChangePWD";
        }
    }
    @RequestMapping("/signUP")
    public String signUP(Model model,
                         @Param("studentID") String studentID,
                         @Param("password") String password,
                         @Param("username") String username,
                         @Param("weChat") String weChat,
                         @RequestParam(value = "QQ", required = false) String qq) {
        Student student = studentRepository.findPasswordByStudentID(studentID);
        if (student != null) {
            Msg msg = new Msg("注册结果","错误，"+studentID+",已被注册，请联系管理员进行解决","额外信息");
            model.addAttribute("msg",msg);
            return "page/Sign";
        }
        Msg msg = new Msg("注册结果","恭喜"+studentID+",你成功注册，请使用刚刚注册的学号和密码进行登录","额外信息");
        Student s = new Student(username, studentID, password, weChat, qq,0);
        String encodePassword = "";
        try{
            OptPassword op = new OptPassword();
            encodePassword = op.encrypt(password);
        }catch (Exception e){
            e.printStackTrace();
        }
        SaveUserPasswordEncode sp = new SaveUserPasswordEncode(studentID,encodePassword);
        saveUserPasswordEncodeRepository.save(sp);
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

    @RequestMapping("/sign")
    public String sign(Model model) {
        Msg msg = new Msg("欢迎注册","请输入你的学号和对应的密码以及其他相关信息","");
        model.addAttribute("msg",msg);
        return "page/Sign";
    }

    @RequestMapping("/login")
    public String login() {
        return "page/Login";
    }
    /*@RequestMapping("/toCheckFrozen")
    public String toCheckFrozen(Model model)
    {
        Msg msg = new Msg("提示","在登陆之前先输入学号确认是否处于冻结状态","");
        model.addAttribute("msg",msg);
        return "page/CheckFrozen";
    }
    @RequestMapping("/checkFrozen")
    public String checkFrozen(Model model,@Param("studentID")String studentID)
    {
        Student s = studentRepository.findAllByStudentID(studentID);
        if(s!=null) {
            boolean t = studentRepository.findFrozenByStudentID(studentID);
            if (t) {
                Msg msg = new Msg("提示", "你的账号处于冻结状态", "");
                model.addAttribute("msg", msg);
                return "page/CheckFrozen";
            }
            Msg msg = new Msg("欢迎登录", "请输入你的注册学号和对应的密码", "");
            model.addAttribute("msg", msg);
            return "page/Login";
        }
        Msg msg = new Msg("提示", "该账号未注册！", "");
        model.addAttribute("msg", msg);
        return "page/CheckFrozen";
    }*/
    /*@RequestMapping("/test")
    public List<Grade> test(@Param("stuId")String studentId) {
        return gradeRepository.findAllByStudentID(studentId);
    }*/


}
