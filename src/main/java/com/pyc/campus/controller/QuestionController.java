package com.pyc.campus.controller;

import com.pyc.campus.config.MailConfig;
import com.pyc.campus.dao.QuestionRepository;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Question;
import com.pyc.campus.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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
 * @file QuestionController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 12:04
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class QuestionController {

    final QuestionRepository questionRepository;

    final QuestionService questionService;

    public QuestionController(QuestionRepository questionRepository, QuestionService questionService){
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    @RequestMapping("/toBrowserQuestion")
    public String toBrowserQuestion(Model model, HttpSession session,
                                    @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                    @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<Question> questions = questionService.getQuestionList(pageNum,pageSize);
        model.addAttribute("prefix","/toBrowserQuestion");
        model.addAttribute("questions", questions);
        return "page/BrowserQuestion";
    }
    @RequestMapping("/queryByQuestionType")
    public String queryByQuestionType(Model model, HttpSession session,
                                      @RequestParam(value = "TypeOfQuestion", required = false)String type,
                                      @RequestParam(value = "pageNum", defaultValue = "0") int pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") int pageSize){
        Page<Question> questions = questionService.getQuestionListByType(pageNum,pageSize,type);
        model.addAttribute("prefix","/queryByQuestionType");
        model.addAttribute("questions", questions);
        return "page/BrowserQuestion";
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
                             @Param("title")String title, @Param("content")String content,
                             @Param("reward")String reward){
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
    @RequestMapping("/toPublishQuestion")
    public String toPublishQuestion(Model model,HttpSession session){
        Msg msg = new Msg("", "", "");
        model.addAttribute("msg", msg);
        return "page/PublishQuestion";
    }
}
