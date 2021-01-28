//IntelliJ IDEA
//campus
//ChatController
//2020/6/15
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.PublishMessage;
import com.pyc.campus.domain.ResponseMessage;
import com.pyc.campus.domain.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class ChatController {

    final StudentRepository studentRepository;

    public ChatController(StudentRepository studentRepository){
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/publicChatRoom")
    public String publicChatRoom(Model model, HttpSession session){
        SecurityContextImpl securityContext = (SecurityContextImpl)session.getAttribute("SPRING_SECURITY_CONTEXT");
        String currentStudentId = ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername();
        Student s = studentRepository.findNameByStudentID(currentStudentId);
        model.addAttribute("curUse",s);
        return "PublicChatRoom";
    }

    @MessageMapping("/publicChatRoom")
    @SendTo("/publicChat/getResponse")
    public ResponseMessage say(PublishMessage message) throws Exception{
        Thread.sleep(500);
        return new ResponseMessage(message.getName()+":"+message.getContent());
    }
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping(value = "/chat/{name}")
    public void handleChat(Principal principal, String msg,  @DestinationVariable String name){
        messagingTemplate.convertAndSendToUser(name,"/queue/notification",msg);
    }
}
