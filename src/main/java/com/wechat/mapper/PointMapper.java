package com.wechat.mapper;

import com.wechat.pojo.Point;

import java.util.Date;

public interface PointMapper {
    public void firstSign(String userId);

    public void sign(String userId);

    public Point checkSign(String userId);

    public Date queryTime(String userId);

    public Integer queryNum(Date signTime);
}
