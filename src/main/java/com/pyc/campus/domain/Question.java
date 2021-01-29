//IntelliJ IDEA
//campus
//Question
//2020/6/14
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Question implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = true,unique = true)
    private String publisher;   // 申请人
    @Column(nullable = true,unique = true)
    private String mail;    // 邮箱
    @Column(nullable = true,unique = true)
    private String type; // 问题类型
    @Column(nullable = true,unique = true)
    private String content; // 问题内容
    @Column(nullable = true,unique = true)
    private String reward; // 悬赏
    public Question(){
        super();
    }
    public Question(long id, String publisher,
                    String mail, String type, String content, String reward){
        super();
        this.id=id;
        this.publisher=publisher;
        this.mail=mail;
        this.type=type;
        this.content=content;
        this.reward=reward;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public void setReward(String reward) {
        this.reward = reward;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMail() {
        return mail;
    }

    public String getContent() {
        return content;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getReward() {
        return reward;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", publisher='" + publisher + '\'' +
                ", mail='" + mail + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", reward='" + reward + '\'' +
                '}';
    }
}
