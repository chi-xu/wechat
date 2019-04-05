package com.wechat.service;

import com.wechat.mapper.PointMapper;
import com.wechat.pojo.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointMapper pointMapper;


    @Override
    public void firstSign(String userId) {
        this.pointMapper.firstSign(userId);
    }

    @Override
    public void sign(String userId) {
        this.pointMapper.sign(userId);
    }

    @Override
    public Point checkSign(String userId) {
        return this.pointMapper.checkSign(userId);
    }

    @Override
    public Date queryTime(String userId) {
        return this.pointMapper.queryTime(userId);
    }

    @Override
    public Integer queryNum(Date signTime) {
        return this.pointMapper.queryNum(signTime);
    }
}
