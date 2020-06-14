//IntelliJ IDEA
//campus
//MailService
//2020/6/14
// Author:御承扬
//E-mail:2923616405@qq.com


package com.pyc.campus.service;

public interface MailService {
    /**
     * 发送文本邮件
     * @param from 发件人
     * @param to 收件人
     * @param subject 主题
     * @param content 内容
     */
    void sendSimpleMail(String from,String to, String subject, String content);
}
