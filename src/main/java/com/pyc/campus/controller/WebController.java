//IntelliJ IDEA
//campus
//WebController
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.bean.OptPassword;
import com.pyc.campus.config.MailConfig;
import com.pyc.campus.dao.*;
import com.pyc.campus.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class WebController {

    final
    StudentRepository studentRepository;
    final
    SysUserRepository sysUserRepository;
    final FriendListRepository friendListRepository;
    final SaveUserPasswordEncodeRepository saveUserPasswordEncodeRepository;
    public WebController(StudentRepository studentRepository, SysUserRepository sysUserRepository,
                         NewsRepository newsRepository, GradeRepository gradeRepository,
                         QuestionRepository questionRepository,
                         FriendListRepository friendListRepository,
                         SaveUserPasswordEncodeRepository saveUserPasswordEncodeRepository) {
        this.studentRepository = studentRepository;
        this.sysUserRepository = sysUserRepository;
        this.newsRepository = newsRepository;
        this.gradeRepository = gradeRepository;
        this.questionRepository = questionRepository;
        this.friendListRepository=friendListRepository;
        this.saveUserPasswordEncodeRepository = saveUserPasswordEncodeRepository;
    }
    @RequestMapping("toCheckPassword")
    public String toCheckPassword(Model model){
        Msg msg = new Msg("提示","请先进行密码验证","");
        model.addAttribute("msg",msg);
        return "page/CheckPassword";
    }
    @RequestMapping("/my/check")
    public String checkPassword(Model model,
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
            msg = new Msg("提示","密码校验正确，请重新输入并单击登陆按钮进行登陆","");
            model.addAttribute("msg",msg);
            return "page/Login";
        }else {
            msg = new Msg("提示","密码校验错误！","");
            model.addAttribute("msg",msg);
            return "page/CheckPassword";
        }

    }
    @RequestMapping("/desc")
    public String desc(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        List<Grade> gradeListDesc = gradeRepository.findAllByStudentIDOrderByGradeDesc(currentStudentId);
        int minGrade = gradeRepository.findMinGrade(currentStudentId);
        int maxGrade = gradeRepository.findMaxGrade(currentStudentId);
        int sumCredit = gradeRepository.findSumCredit(currentStudentId);
        float avgGPA = gradeRepository.findAvgGPA(currentStudentId);
        model.addAttribute("minGrade", minGrade);
        model.addAttribute("maxGrade", maxGrade);
        model.addAttribute("sumCredit", sumCredit);
        model.addAttribute("avgGPA",avgGPA);
        model.addAttribute("gradeItems", gradeListDesc);
        return "page/QueryGrade";
    }
    @RequestMapping("/asc")
    public String asc(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
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
        return "page/QueryGrade";
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
    @RequestMapping("/toAddFriend")
    public String toAddFriend(Model model, HttpSession session)
    {
        Msg msg = new Msg("注意","添加好友时请输入对方的学号，而不是姓名！","");
        model.addAttribute("msg",msg);
        return "page/AddFriend";
    }
    @RequestMapping("/toHelp")
    public String toHelp(Model model, HttpSession session){
        return "page/Help";
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
    @RequestMapping("/toMyFriend")
    public String toMyFriend(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        List<FriendList> tempFl = friendListRepository.toNameIsFalseByFromName(currentStudentId);
        List<FriendList> Fl = friendListRepository.findMyFriendsByFromName(currentStudentId);
        List<FriendList> Fl1 = friendListRepository.findMyFriendsByToName(currentStudentId);
        model.addAttribute("tempFl",tempFl);
        model.addAttribute("Fl",Fl);
        model.addAttribute("Fl1",Fl1);
        return "page/MyFriend";
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
    @RequestMapping("/addFriend")
    public String addFriend(Model model, HttpSession session,
                            @Param("fromName")String fromName,
                            @Param("toName")String toName){
        Student stu = studentRepository.findAllByStudentID(toName);
        if(stu==null)
        {
            Msg msg = new Msg("错误","你所添加的用户不存在,请检查输入是否错误","");
            model.addAttribute("msg",msg);
            return "page/AddFriend";
        }
        FriendList fl = new FriendList(fromName,toName);
        friendListRepository.save(fl);
        Msg msg = new Msg("OK","请耐心等待对方通过申请","");
        model.addAttribute("msg",msg);
        return "page/AddFriend";
    }
    @RequestMapping("/toVerifyFriend")
    public String toVerifyFriend(Model model, HttpSession session) {
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        List<FriendList> fl = friendListRepository.toNameIsFalseByToName(currentStudentId);
        int len = fl.size();
        Msg msg = new Msg("待验证好友申请数量：", "一共"+len+"个","");
        model.addAttribute("msg",msg);
        model.addAttribute("fl",fl);
        return "page/VerifyFriend";
    }
    @RequestMapping("/verifyFriend")
    public String verifyFried(Model model, HttpSession session,
                              @Param("fromName")String fromName){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        int result = friendListRepository.setStatus(fromName,currentStudentId);
        List<FriendList> fl = friendListRepository.toNameIsFalseByToName(currentStudentId);
        int len = fl.size();
        if(result!=0){
            Msg msg = new Msg("待验证好友申请数量：", "一共"+len+"个","");
            model.addAttribute("msg",msg);
            model.addAttribute("fl",fl);
            return "page/VerifyFriend";
        }
        Msg msg = new Msg("错误：", "请检查输入的学号","");
        model.addAttribute("msg",msg);
        model.addAttribute("fl",fl);
        return "page/VerifyFriend";
    }
    @RequestMapping("/toPrivateChat")
    public String toPrivateChat(Model model,HttpSession session,
                                @Param("toName")String toName){

        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student toUse = studentRepository.findNameByStudentID(toName);
        model.addAttribute("curUser", toUse);
        boolean s;
        FriendList s1 = friendListRepository.getStatus(currentStudentId,toName);
        FriendList s2 = friendListRepository.getStatus(toName,currentStudentId);
        if(s1!=null)
            s=s1.getStatus();
        else if (s2!=null)
            s=s2.getStatus();
        else
            s=false;
        model.addAttribute("friendStatus",s);
        if(s)
        {
            boolean stu = studentRepository.getOnlineStatus(toName).isOnlineStatus();
            model.addAttribute("onlineStatus",stu);
            if(stu)
            {
                Msg msg = new Msg("提示：","对方在线，当前只提供临时聊天，不保存聊天记录，见谅","");
                model.addAttribute("fromName",currentStudentId);
                model.addAttribute("toName",toName);
                model.addAttribute("msg",msg);
                return "page/PrivateChat";
            }
            Msg msg = new Msg("提示：","对方不在线，你所发送的消息，可能无法被及时回复","");
            model.addAttribute("fromName",currentStudentId);
            model.addAttribute("toName",toName);
            model.addAttribute("msg",msg);
            return "page/PrivateChat";
        }
        Msg msg = new Msg("提示：","对方不是你的好友，无法进行私聊","");
        model.addAttribute("fromName",currentStudentId);
        model.addAttribute("toName",toName);
        model.addAttribute("msg",msg);
        return "page/PrivateChat";
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
//@RequestMapping("/")
    @RequestMapping("/campus")
    public String campus() {
        return "page/Index";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        Msg msg = new Msg("欢迎登录","请输入你的注册学号和对应的密码,先进行密码校验方能进行登陆","");
        model.addAttribute("key", false);
        model.addAttribute("msg",msg);
        return "page/Login";
    }
    @RequestMapping("/toCheckFrozen")
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
    }
    @RequestMapping("/saveFrozenInTrue")
    public String saveFrozenInTrue(Model model,HttpSession session,
                                   @RequestParam(value = "studentId", required = false)String studentID){
        boolean frozen=true;
        System.out.println(studentID+" "+frozen);
        int t = studentRepository.saveFrozen(frozen,studentID);
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
        boolean frozen=false;
        System.out.println(studentID+" "+frozen);
        int t = studentRepository.saveFrozen(frozen,studentID);
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
    @RequestMapping("/publicChatRoom")
    public String publicChatRoom(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "PublicChatRoom";
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
        return "page/QueryGrade";
    }
    final GradeRepository gradeRepository;
    @RequestMapping("/queryByTerm")
    public String queryByTerm(Model model, HttpSession session, @Param("term")String term){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        List<Grade> gradeLists = gradeRepository.findAllByTermAndStudentID(term, currentStudentId);
        model.addAttribute("gradeItems", gradeLists);
        return "page/QueryGrade";
    }
    /*@RequestMapping("/test")
    public List<Grade> test(@Param("stuId")String studentId) {
        return gradeRepository.findAllByStudentID(studentId);
    }*/
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
        Msg msg = new Msg("提示","导入成功","");
        model.addAttribute("msg",msg);
        return "page/ImportGrade";
    }
    @RequestMapping("/toUpQuestion")
    public String toUpQuestion(Model model, HttpSession session){
        Msg msg = new Msg("","","");
        model.addAttribute("msg", msg);
        return "page/UpQuestion";
    }

    @RequestMapping("/upQuestion")
    public String upQuestion(Model model, HttpSession session,
                             @Param("mail")String mail, @Param("name")String name,
                             @Param("title")String title, @Param("content")String content,@Param("reward")String reward){
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
    @RequestMapping("/feedback")
    public String feedback(Model model, HttpSession session,
                           @Param("mail")String mail, @Param("name")String name,
                           @Param("title")String title, @Param("content")String content){
        try{
            MailConfig mailConfig = new MailConfig();
            JavaMailSender sender = mailConfig.getMailSender();
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom("15014366986@163.com");
            mailMessage.setTo("2923616405@qq.com");
            mailMessage.setSubject("用户反馈");
            String text = "反馈用户："+name+"，邮箱："+mail+",问题标题："+title+",问题内容："+content;
            mailMessage.setText(text);
            sender.send(mailMessage);
            Msg msg = new Msg("提示","反馈成功，请等待管理员回复","");
            model.addAttribute("msg",msg);
            return "page/FeedBack";
        }catch (Exception e){
            Msg msg = new Msg("提示","发生错误，请重新尝试，谢谢","");
            model.addAttribute("msg",msg);
            System.out.println(e.getMessage());
            return "page/FeedBack";
        }
    }
    @RequestMapping("/toFeedBack")
    public String toFeedBack(Model model, HttpSession session){
        Msg msg = new Msg("","","");
        model.addAttribute("msg",msg);
        return "page/FeedBack";
    }
    @RequestMapping("/toPublishQuestion")
    public String toPublishQuestion(Model model,HttpSession session){
        Msg msg = new Msg("", "", "");
        model.addAttribute("msg", msg);
        return "page/PublishQuestion";
    }
    @RequestMapping("/publishQuestion")
    public String publishQuestion(Model model, HttpSession session,
                                  @Param("mail")String mail,
                                  @RequestParam(value = "PublisherName", required = false)String publisher,
                                  @RequestParam(value = "QuestionType", required = false)String type, @Param("content")String content, @Param("reward")String reward){
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
    final QuestionRepository questionRepository;
    @RequestMapping("/toBrowserQuestion")
    public String toBrowserQuestion(Model model, HttpSession session){
        List<Question> questions = questionRepository.findAll();
        model.addAttribute("questions", questions);
        return "page/BrowserQuestion";
    }
    @RequestMapping("/queryByQuestionType")
    public String queryByQuestionType(Model model, HttpSession session,
                                      @RequestParam(value = "TypeOfQuestion", required = false)String type){
        List<Question> questions = questionRepository.findAllByType(type);
        model.addAttribute("questions", questions);
        return "page/BrowserQuestion";
    }
    @RequestMapping("/manageUser")
    public String findUserExceptCurUser(Model model,HttpSession session){
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "page/ManageUser";
    }
    @RequestMapping("/findUserByStudentIDLike")
    public String findUserByStudentIDLike(Model model, HttpSession session,
                                           @RequestParam(value = "ClassPrefix", required = false)String classPrefix){
        List<Student> students = studentRepository.query01(classPrefix+'%');
        model.addAttribute("students", students);
        return "page/ManageUser";
    }
    @RequestMapping("/delStuByStuId")
    public String delStuByStuId(Model model,HttpSession session,@RequestParam(value = "studentId", required = false)String stuId){
        studentRepository.delByStudentID(stuId);
        sysUserRepository.delByUsername(stuId);
        List<Student> students = studentRepository.findAll();
        model.addAttribute("students", students);
        return "page/ManageUser";
    }
}
