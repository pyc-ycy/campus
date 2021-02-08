package com.pyc.campus.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file LoginInterceptor
 * @pack com.pyc.campus.interceptor
 * @date 2021/2/8
 * @time 9:01
 * @E-mail 2923616405@qq.com
 **/

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
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
        Object loginUser = session.getAttribute("loginUser");
        if(loginUser != null)
        {
            return true;
        }else {
            request.setAttribute("warring","请先登陆！");
            request.getRequestDispatcher("/toCheckPassword").forward(request,response);
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
