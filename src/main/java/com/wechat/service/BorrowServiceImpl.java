package com.wechat.service;

import com.wechat.mapper.BorrowMapper;
import com.wechat.pojo.Borrow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BorrowServiceImpl implements BorrowService {

    @Autowired
    private BorrowMapper borrowMapper;

    @Override
    public List<Borrow> queryBorrow(String userId) {
        return this.borrowMapper.queryBorrow(userId);
    }

    @Override
    public Integer selCount(String userId) {
        return this.borrowMapper.selCount(userId);
    }
}
