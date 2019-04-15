package com.wechat.mapper;

public interface StatusMapper {

    public void addOpenid(String openid);

    public Integer chartStatus(String openid);

    public void chatStatusToOne(String openid);

    public void chatStatusToZero(String openid);

    public Integer parcelStatus(String openid);

    public void parcelStatusToOne(String openid);

    public void parcelStatusToZero(String openid);

    public void delOpenid(String openid);
}
