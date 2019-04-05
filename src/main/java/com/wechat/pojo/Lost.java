package com.wechat.pojo;

import java.util.Date;

public class Lost {

    private Integer id;
    private String username;
    private String phone;
    private String address;
    private Date losetime;
    private String description;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getLosetime() {
        return losetime;
    }

    public void setLosetime(Date losetime) {
        this.losetime = losetime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Lost{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", losetime=" + losetime +
                ", description='" + description + '\'' +
                '}';
    }
}
