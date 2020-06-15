//IntelliJ IDEA
//campus
//Student
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Student {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String studentID;
    private String password;
    private String weChat;
    private String QQ;
    private int admin;

    public Student() {
        super();
    }

    public Student(String name, String studentID, String password, String weChat, String QQ, int admin) {
        super();
        this.name = name;
        this.studentID = studentID;
        this.password = password;
        this.weChat = weChat;
        this.QQ = QQ;
        this.admin = admin;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public int getAdmin() {
        return admin;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public void setQQ(String QQ) {
        this.QQ = QQ;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getQQ() {
        return QQ;
    }

    public String getStudentID() {
        return studentID;
    }

    public String getWeChat() {
        return weChat;
    }
}
