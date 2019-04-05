package com.wechat.service;

import com.wechat.pojo.Borrow;

import java.util.List;

public interface BorrowService {

    public List<Borrow> queryBorrow(String userId);

    public Integer selCount(String userId);
}
