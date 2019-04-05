package com.wechat.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Borrow {

    private Integer id;
    private String userId;
    private String bookId;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    //@DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Integer status;
    private Users users;
    private Book book;

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

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "Borrow{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                ", users=" + users +
                ", book=" + book +
                '}';
    }
}
