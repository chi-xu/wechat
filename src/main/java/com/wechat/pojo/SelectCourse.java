package com.wechat.pojo;

public class SelectCourse {

    private Integer id;
    private String userId;
    private String courseId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    @Override
    public String toString() {
        return "SelectCourse{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", courseId='" + courseId + '\'' +
                '}';
    }
}
