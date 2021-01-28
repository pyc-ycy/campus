package com.pyc.campus.controller;

import com.pyc.campus.dao.NewsRepository;
import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.News;
import com.pyc.campus.domain.Student;
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
 * @file NewsController
 * @pack com.pyc.campus.controller
 * @date 2021/1/27
 * @time 12:11
 * @E-mail 2923616405@qq.com
 **/

@Controller
public class NewsController {

    final NewsRepository newsRepository;

    final StudentRepository studentRepository;

    public NewsController(NewsRepository newsRepository,StudentRepository studentRepository){
        this.newsRepository = newsRepository;
        this.studentRepository = studentRepository;
    }

    @RequestMapping("/news")
    public String news(Model model, HttpSession session)
    {
        List<News> news = newsRepository.findAll();
        model.addAttribute("news",news);
        return "page/BrowseNews";
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
}
