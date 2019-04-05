package com.wechat.service;

import com.wechat.mapper.UsersMapper;
import com.wechat.pojo.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersMapper usersMapper;

    @Override
    public void add(Users users) {

        this.usersMapper.add(users);
    }

    @Override
    public Integer queryCount(Users users) {
        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        return this.usersMapper.queryCount(users);
    }

    @Override
    public String queryId(String userId) {
        return this.usersMapper.queryId(userId);
    }

    @Override
    public Integer chartStatus(String openid) {
        return this.usersMapper.chartStatus(openid);
    }

    @Override
    public void statusToOne(String openid) {
        this.usersMapper.statusToOne(openid);
    }

    @Override
    public void statusToZero(String openid) {
        this.usersMapper.statusToZero(openid);
    }
}
