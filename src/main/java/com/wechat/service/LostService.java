package com.wechat.service;

import com.wechat.pojo.Lost;
import com.wechat.util.Page;

public interface LostService {
    public void addLostDesc(Lost lost);

    public Page queryLost(Integer currPage);


}
