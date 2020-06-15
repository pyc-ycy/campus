//IntelliJ IDEA
//campus
//ChatController
//2020/6/15
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.controller;

import com.pyc.campus.domain.PublishMessage;
import com.pyc.campus.domain.ResponseMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
    @MessageMapping("/publicChatRoom")
    @SendTo("/publicChat/getResponse")
    public ResponseMessage say(PublishMessage message) throws Exception{
        Thread.sleep(500);
        return new ResponseMessage(message.getName()+":"+message.getContent());
    }
}
