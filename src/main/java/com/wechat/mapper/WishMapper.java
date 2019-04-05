package com.wechat.mapper;

import com.wechat.pojo.Wish;

import java.util.List;

public interface WishMapper {
    public void addWish(Wish wish);

    public List<Wish> queryWish();

    public Integer selCount();
}
