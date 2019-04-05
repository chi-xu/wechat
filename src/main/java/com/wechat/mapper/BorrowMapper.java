package com.wechat.mapper;

import com.wechat.pojo.Borrow;

import java.util.List;

public interface BorrowMapper {

    public List<Borrow> queryBorrow(String userId);

    public Integer selCount(String userId);
}
