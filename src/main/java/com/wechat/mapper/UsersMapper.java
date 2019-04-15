package com.wechat.mapper;

import com.wechat.pojo.Users;

public interface UsersMapper {
    public void add(Users users);

    public Integer queryCount(Users users);

    public String queryId(String openid);

    public void remove(String userId);
}
