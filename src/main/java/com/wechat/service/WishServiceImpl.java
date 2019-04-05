package com.wechat.service;

import com.github.pagehelper.PageHelper;
import com.wechat.mapper.WishMapper;
import com.wechat.pojo.Wish;
import com.wechat.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WishServiceImpl implements WishService {

    @Autowired
    private WishMapper wishMapper;

    @Override
    public void addWish(Wish wish) {
        this.wishMapper.addWish(wish);
    }

    @Override
    public Page queryWish(Integer currPage) {
        if(currPage == null) currPage = 1;
        Page page = new Page();
        page.setCurrPage(currPage);
        //查总记录数
        page.setTotalCount(this.wishMapper.selCount());
        page.init();
        PageHelper.startPage(currPage,Page.PAGE_SIZE);
        page.setData(this.wishMapper.queryWish());
        return page;
    }


}
