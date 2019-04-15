package com.wechat.service;

import com.wechat.mapper.StatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusMapper statusMapper;

    @Override
    public void addOpenid(String openid) {
        this.statusMapper.addOpenid(openid);
    }

    @Override
    public Integer chartStatus(String openid) {
       return this.statusMapper.chartStatus(openid);
    }

    @Override
    public void chatStatusToOne(String openid) {
        this.statusMapper.chatStatusToOne(openid);
    }

    @Override
    public void chatStatusToZero(String openid) {
        this.statusMapper.chatStatusToZero(openid);
    }

    @Override
    public Integer parcelStatus(String openid) {
        return this.statusMapper.parcelStatus(openid);
    }

    @Override
    public void parcelStatusToOne(String openid) {
        this.statusMapper.parcelStatusToOne(openid);
    }

    @Override
    public void parcelStatusToZero(String openid) {
        this.statusMapper.parcelStatusToZero(openid);
    }

    @Override
    public void delOpenid(String openid) {
        this.statusMapper.delOpenid(openid);
    }
}
