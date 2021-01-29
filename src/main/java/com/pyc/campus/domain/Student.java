//IntelliJ IDEA
//campus
//Student
//2020/5/4
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = true,unique = true)
    private String name;
    @Column(nullable = true,unique = true)
    private String studentID;
    @Column(nullable = true,unique = true)
    private String password;
    @Column(nullable = true,unique = true)
    private String weChat;
    @Column(nullable = true,unique = true)
    private String QQ;
    @Column(nullable = true,unique = true)
    private int admin;
    @Column(nullable = true,unique = true)
    private boolean onlineStatus;
    @Column(nullable = true,unique = true)
    private boolean frozen;

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
        this.onlineStatus=false;
        this.frozen=false;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setAdmin(int admin) {
        this.admin = admin;
    }

    public boolean isOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(boolean onlineStatus) {
        this.onlineStatus = onlineStatus;
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

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", studentID='" + studentID + '\'' +
                ", password='" + password + '\'' +
                ", weChat='" + weChat + '\'' +
                ", QQ='" + QQ + '\'' +
                ", admin=" + admin +
                ", onlineStatus=" + onlineStatus +
                ", frozen=" + frozen +
                '}';
    }
}
