package com.wechat.controller;

import com.wechat.pojo.Wish;
import com.wechat.service.WishService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/wish")
public class WishController {

    @Resource
    private WishService wishService;

    @RequestMapping(value = "/addWish")
    public String addWish(Wish wish){
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWW");
        System.out.println(wish.toString()+"QQQQQQQQQQQQQQQQQQQQQQQQ");
        this.wishService.addWish(wish);
        return "redirect:/wish/queryWish";
    }

    @RequestMapping(value = "/queryWish")
    public String queryWish(Model model, Integer currPage){
        model.addAttribute("page",this.wishService.queryWish(currPage));
        return "wish";
    }

}
