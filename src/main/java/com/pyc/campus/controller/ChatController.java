//IntelliJ IDEA
//campus
//ChatController
//2020/6/15
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.domain.PublishMessage;
import com.pyc.campus.domain.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class ChatController {
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
