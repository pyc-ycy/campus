package com.pyc.campus.interceptor;

import com.pyc.campus.dao.StudentRepository;
import com.pyc.campus.domain.Msg;
import com.pyc.campus.domain.Student;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file AdminInterceptor
 * @pack com.pyc.campus.interceptor
 * @date 2021/2/8
 * @time 13:23
 * @E-mail 2923616405@qq.com
 **/

@Slf4j
public class AdminInterceptor implements HandlerInterceptor {
    /**
     * 目标方法执行前
     * @author 彭友聪
     * @param  request javax.servlet.http.HttpServletRequest
     * @param response javax.servlet.http.HttpServletResponse
     * @param handler Object
     * @return boolean
     * @throws Exception Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 登陆检查逻辑
        log.info("preHandle拦截的请求路径是{}",request.getRequestURI());
        HttpSession session = request.getSession();
        Object admin = session.getAttribute("admin");
        if(admin != null)
        {
            return true;
        }else {
            Msg msg = new Msg("系统警告","你不是管理员，不允许使用管理功能！","");
            request.setAttribute("msg",msg);
            request.getRequestDispatcher("/home").forward(request,response);
            return false;
        }
    }

    /**
     * 目标方法执行后
     * @author 彭友聪
     * @param request javax.servlet.http.HttpServletRequest
     * @param response javax.servlet.http.HttpServletResponse
     * @param handler Object
     * @param modelAndView org.springframework.web.servlet.ModelAndView
     * @throws Exception Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("postHandle拦截的请求路径：{},{}",request.getRequestURI(),modelAndView);
    }


    /**
     * 页面渲染后
     * @author 彭友聪
     * @param request javax.servlet.http.HttpServletRequest
     * @param response javax.servlet.http.HttpServletResponse
     * @param handler Object
     * @param ex Exception
     * @throws Exception Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info("afterCompletion 拦截的请求路径：{},异常：{}", request.getRequestURI(),ex);
    }
}
