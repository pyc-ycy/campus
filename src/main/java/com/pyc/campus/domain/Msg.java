//IntelliJ IDEA
//campus
//Msg
//2020/5/20
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

public class Msg {
    private String title;
    private String content;
    private String etraInfo;
    public Msg(String title, String content, String etraInfo){
        super();
        this.title = title;
        this.content = content;
        this.etraInfo = etraInfo;
    }

    public String getContent() {
        return content;
    }

    public String getEtraInfo() {
        return etraInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setEtraInfo(String etraInfo) {
        this.etraInfo = etraInfo;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
