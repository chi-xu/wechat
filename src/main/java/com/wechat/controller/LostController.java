package com.wechat.controller;

import com.wechat.pojo.Lost;
import com.wechat.service.LostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.annotation.Resource;

@Controller
@RequestMapping(value = "/lost")
public class LostController {

    @Resource
    private LostService lostService;

    @RequestMapping(value = "/addLostDesc")
    public String addLostDesc(Lost lost){
        System.out.println("WWWWWWWWWWWWWWWWWWWWWWWW");
        System.out.println(lost.toString()+"QQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQQ");
        this.lostService.addLostDesc(lost);
        return "redirect:/lost/queryLost";
    }

    @RequestMapping(value = "/queryLost")
    public String queryLost(Model model,Integer currPage){
        model.addAttribute("page",this.lostService.queryLost(currPage));
        return "lost";
    }
}
