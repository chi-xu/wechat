package com.wechat.service;

import com.wechat.pojo.Wish;
import com.wechat.util.Page;

import java.util.List;

public interface WishService {

    public void addWish(Wish wish);

    public Page queryWish(Integer currPage);

}
