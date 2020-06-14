//IntelliJ IDEA
//campus
//WebController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.config.MailConfig;
import com.pyc.campus.dao.GradeRepository;
import com.pyc.campus.dao.NewsRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.dao.SysUserRepository;
import com.pyc.campus.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.parameters.P;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.pyc.campus.config.MailConfig;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    final
    StudentRepository studentRepository;
    final
    SysUserRepository sysUserRepository;

    public WebController(StudentRepository studentRepository, SysUserRepository sysUserRepository,
                         NewsRepository newsRepository, GradeRepository gradeRepository) {
        this.studentRepository = studentRepository;
        this.sysUserRepository = sysUserRepository;
        this.newsRepository = newsRepository;
        this.gradeRepository = gradeRepository;
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
        model.addAttribute("curUse",s);
        return "page/Home";
    }
    @RequestMapping("/learn")
    public String learn(Model model,HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "page/Learn";
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
    @RequestMapping("/saveChangePWD")
    public String saveChangePWD(Model model,HttpSession session,
                                @Param("password")String password){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        int key = studentRepository.saveChangePWD(password,s.getStudentID());
        if(key!=0){
            int t = sysUserRepository.updatePassword(password,s.getStudentID());
            if(t>0){
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
    @RequestMapping("/signUP")
    public String signUP(Model model,
                         @Param("studentID") String studentID,
                         @Param("password") String password,
                         @Param("username") String username,
                         @Param("weChat") String weChat,
                         @RequestParam(value = "QQ", required = false) String qq) {
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
        return "page/Index";
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

    final NewsRepository newsRepository;
    @RequestMapping("/news")
    public String news(Model model,HttpSession session)
    {
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        List<News> news = newsRepository.findAll();
        model.addAttribute("news",news);
        return "page/BrowseNews";
    }
    @RequestMapping("/admin")
    public String admin(Model model,HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "page/AdminPage";
    }
    @RequestMapping("/publishNews")
    public String publishNews(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "page/PublishNews";
    }
    @RequestMapping("/saveNews")
    public String saveNews(Model model, HttpSession session,
                           @Param("title")String title,
                           @Param("content")String content){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        long i=0;
        List<News> list = newsRepository.findAll();
        for(News l:list){
            if(l != null)
            {
                i++;
            }
        }
        long nextId = i+1;
        News news = new News(nextId,title,content);
        newsRepository.save(news);
        return "/page/PublishNews";
    }
    @RequestMapping("/toQueryGrade")
    public String toQueryGrade(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        List<Grade> gradeList = gradeRepository.findAllByStudentID(currentStudentId);
        model.addAttribute("gradeItems", gradeList);
        return "page/QueryGrade";
    }
    final GradeRepository gradeRepository;
    @RequestMapping("/queryByTerm")
    public String queryByTerm(Model model, HttpSession session, @Param("term")String term){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
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
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        Msg msg = new Msg("提示","导入成功","");
        model.addAttribute("msg",msg);
        return "page/ImportGrade";
    }
    @RequestMapping("/toUpQuestion")
    public String toUpQuestion(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        Msg msg = new Msg("","","");
        model.addAttribute("msg", msg);
        return "page/UpQuestion";
    }

    @RequestMapping("/upQuestion")
    public String upQuestion(Model model, HttpSession session,
                             @Param("mail")String mail, @Param("name")String name,
                             @Param("title")String title, @Param("content")String content,@Param("reward")String reward){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        try{
            MailConfig mailConfig = new MailConfig();
            JavaMailSender sender = mailConfig.getMailSender();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("15014366986@163.com");
            mailMessage.setTo("2923616405@qq.com");
            mailMessage.setSubject("问题悬赏申请");
            String text = "发布者："+name+"，发布者邮箱："+mail+",问题标题："+title+",问题内容："+content+",悬赏："+reward;
            mailMessage.setText(text);
            sender.send(mailMessage);
            Msg msg = new Msg("提示","申请成功，请等待管理员审核通过","");
            model.addAttribute("msg",msg);
            return "page/UpQuestion";
        }catch (Exception e){
            Msg msg = new Msg("提示","发生错误，请重新尝试，谢谢","");
            model.addAttribute("msg",msg);
            System.out.println(e.getMessage());
            return "page/UpQuestion";
        }
    }
}
