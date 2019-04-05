package com.wechat.pojo;

import java.util.Date;

public class Wish {

    private Integer id;
    private String username;
    private Date publishtime;
    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getPublishtime() {
        return publishtime;
    }

    public void setPublishtime(Date publishtime) {
        this.publishtime = publishtime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Wish{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", publishtime=" + publishtime +
                ", content='" + content + '\'' +
                '}';
    }
}
