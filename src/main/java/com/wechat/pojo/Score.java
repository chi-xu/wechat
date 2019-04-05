package com.wechat.pojo;

public class Score {
    private Integer id;
    private String userId;
    private String courseId;
    private Double credit;
    private Double score;
    private String year;
    private Integer term;
    private Users users;
    private Course course;


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

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getCredit() {
        return credit;
    }

    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @Override
    public String toString() {
        return "Score{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", courseId='" + courseId + '\'' +
                ", credit=" + credit +
                ", score=" + score +
                ", year='" + year + '\'' +
                ", term=" + term +
                ", users=" + users +
                ", course=" + course +
                '}';
    }
}
