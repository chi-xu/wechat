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
    public void remove(String userId) {
        this.usersMapper.remove(userId);
    }
}
