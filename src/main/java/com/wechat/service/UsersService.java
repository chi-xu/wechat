package com.wechat.service;

import com.wechat.pojo.Users;

public interface UsersService {

    public void add(Users users);

    public Integer queryCount(Users users);

    public String queryId(String userId);

    public Integer chartStatus(String openid);

    public void statusToOne(String openid);

    public void statusToZero(String openid);
}
