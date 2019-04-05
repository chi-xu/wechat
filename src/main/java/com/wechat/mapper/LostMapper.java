package com.wechat.mapper;

import com.wechat.pojo.Lost;

import java.util.List;

public interface LostMapper {
    public void addLostDesc(Lost lost);

    public List<Lost> queryLost();

    public Integer selCount();

}
