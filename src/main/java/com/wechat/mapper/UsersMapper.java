package com.wechat.mapper;

import com.wechat.pojo.Users;

public interface UsersMapper {
    public void add(Users users);

    public Integer queryCount(Users users);

    public String queryId(String openid);

    public Integer chartStatus(String openid);

    public void statusToOne(String openid);

    public void statusToZero(String openid);
}
