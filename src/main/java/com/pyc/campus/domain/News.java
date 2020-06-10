//IntelliJ IDEA
//campus
//News
//2020/6/10
// Author:御承扬
//E-mail:2923616405@qq.com

package com.pyc.campus.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class News {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    public News()
    {
        super();
    }
    public News(Long id,String title, String content){
        super();
        this.id=id;
        this.title=title;
        this.content=content;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
