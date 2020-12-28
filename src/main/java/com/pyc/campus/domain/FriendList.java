//IntelliJ IDEA
//campus
//FriendList
//2020/12/28
// Author:御承扬
//E-mail:2923616405@qq.com
// 私聊功能，好友列表
package com.pyc.campus.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class FriendList {
    @Id
    @GeneratedValue
    long id;
    private String fromName;    //主动添加方
    private String toName;
    private Boolean status; // 标识双方是否通过好友申请

    public FriendList() {
        super();
    }
    public FriendList(String fromName, String toName){
        this.fromName=fromName;
        this.toName=toName;
        this.status=false;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public long getId() {
        return id;
    }

    public String getFromName() {
        return fromName;
    }

    public String getToName() {
        return toName;
    }

    public Boolean getStatus() {
        return status;
    }
}
