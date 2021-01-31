package com.pyc.campus.controller;

import com.pyc.campus.dao.FriendListRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.FriendList;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Student;
import lombok.extern.slf4j.Slf4j;
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
 * @file FrindListController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 11:44
 * @E-mail 2923616405@qq.com
 **/
@Slf4j
@Controller
public class FriendListController {

    final FriendListRepository friendListRepository;

    final StudentRepository studentRepository;

    public FriendListController(FriendListRepository friendListRepository,
                                StudentRepository studentRepository){
        this.friendListRepository = friendListRepository;
        this.studentRepository = studentRepository;
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

    @RequestMapping("/toAddFriend")
    public String toAddFriend(Model model, HttpSession session)
    {
        Msg msg = new Msg("注意","添加好友时请输入对方的学号，而不是姓名！","");
        model.addAttribute("msg",msg);
        return "page/AddFriend";
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
        log.info(currentStudentId + "发起与" + toName + "的私聊....");
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
}
