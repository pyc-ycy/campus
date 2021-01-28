package com.pyc.campus.controller;

import com.pyc.campus.config.MailConfig;
import com.pyc.campus.domain.Msg;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file FeedbackController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 12:09
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class FeedbackController {

    @RequestMapping("/toHelp")
    public String toHelp(){
        return "page/Help";
    }

    @RequestMapping("/feedback")
    public String feedback(Model model,
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
}
