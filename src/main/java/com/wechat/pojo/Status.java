package com.wechat.pojo;

public class Status {

    private Integer id;
    private String openid;
    private Integer chatStatus;
    private Integer parcelStatus;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public Integer getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(Integer chatStatus) {
        this.chatStatus = chatStatus;
    }

    public Integer getParcelStatus() {
        return parcelStatus;
    }

    public void setParcelStatus(Integer parcelStatus) {
        this.parcelStatus = parcelStatus;
    }
}
