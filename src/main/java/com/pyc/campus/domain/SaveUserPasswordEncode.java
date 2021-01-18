package com.pyc.campus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author 御承扬
 * @product IntelliJ IDEA
 * @project campus
 * @file SaveUserPasswordEncode
 * @pack com.pyc.campus.domain
 * @date 2021/1/18
 * @time 10:57
 * @E-mail 2923616405@qq.com
 **/

@Entity
public class SaveUserPasswordEncode {
    @Id
    @GeneratedValue
    private long id;
    private String stuID;
    private String encodePassword;
    public SaveUserPasswordEncode(){
        super();
    }
    public SaveUserPasswordEncode(String stuID,String encodePassword){
        super();
        this.stuID=stuID;
        this.encodePassword=encodePassword;
    }

    public void setStuID(String stuID) {
        this.stuID = stuID;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setEncodePassword(String encodePassword) {
        this.encodePassword = encodePassword;
    }

    public long getId() {
        return id;
    }

    public String getStuID() {
        return stuID;
    }

    public String getEncodePassword() {
        return encodePassword;
    }
}
