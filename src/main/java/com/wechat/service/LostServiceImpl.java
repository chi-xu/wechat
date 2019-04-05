package com.wechat.service;

import com.github.pagehelper.PageHelper;
import com.wechat.mapper.LostMapper;
import com.wechat.pojo.Lost;
import com.wechat.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LostServiceImpl implements LostService {

    @Autowired
    private LostMapper lostMapper;

    @Override
    public void addLostDesc(Lost lost) {
        this.lostMapper.addLostDesc(lost);
    }

    @Override
    public Page queryLost(Integer currPage) {
        if(currPage == null) currPage = 1;
        Page page = new Page();
        page.setCurrPage(currPage);
        //查总记录数
        page.setTotalCount(this.lostMapper.selCount());
        page.init();
        PageHelper.startPage(currPage,Page.PAGE_SIZE);
        page.setData(this.lostMapper.queryLost());
        return page;
    }
}
