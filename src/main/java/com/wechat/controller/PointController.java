package com.wechat.controller;

import com.wechat.pojo.Users;
import com.wechat.pojo.WeiXinUser;
import com.wechat.service.PointService;
import com.wechat.service.SelectScoreService;
import com.wechat.service.UsersService;
import com.wechat.service.WeiXinUserInfoService;
import com.wechat.util.WeixinUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/point")
public class PointController {

    WechatController wechatController = new WechatController();

    @Resource
    private PointService pointService;
    @Resource
    private UsersService usersService;
    @Resource
    private WeiXinUserInfoService weiXinUserInfoService;

    @RequestMapping(value = "/getUserInfo")
    public void check(HttpServletRequest request , HttpSession session, Map<String, Object> map) {
        //首先判断一下session中，是否有保存着的当前用户的信息，有的话，就不需要进行重复请求信息
        WeiXinUser weiXinUser = null ;
            /*if(session.getAttribute("currentUser") != null){
                weiXinUser = (WeiXinUser) session.getAttribute("currentUser");
            }else {
*/
        //进行获取openId，必须的一个参数，这个是当进行了授权页面的时候，再重定向了我们自己的一个页面的时候，
        //会在request页面中，新增这个字段信息，要结合这个ProjectConst.Get_WEIXINPAGE_Code这个常量思考
        String code = request.getParameter("code");
        try {
            //得到当前用户的信息(具体信息就看weixinUser这个javabean)
            weiXinUser = getTheCode(session, code);
            //将获取到的用户信息，放入到session中
            //session.setAttribute("currentUser", weiXinUser);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //}
        //map.put("weiXinUser", weiXinUser);
        String result = null;
            Users users = new Users();
            users.setOpenid(weiXinUser.getOpenId());
            result = this.usersService.queryId(users.getOpenid());//根据此openid查询该用户是否绑定
            System.out.println(result);
            if(result == null){
                System.out.println("失败！！！！！！！！！！！！！！！！！！");

            }else{
                System.out.println("成功！！！！！！！！！！！！！！");
                if(this.pointService.checkSign(result)==null){ //如果积分为0，说明是首次签到
                    this.pointService.firstSign(result); //插入积分值
                }else {
                    this.pointService.sign(result);//更新积分
                }

        }

    }

    /**
     * 获取用户的openId
     * @param session
     * @param code
     * @return 返回封装的微信用户的对象
     */
    private WeiXinUser getTheCode(HttpSession session, String code) {
        Map<String , String> authInfo = new HashMap<>();
        String openId = "";
        if (code != null)
        {
            //调用根据用户的code得到需要的授权信息
            authInfo = weiXinUserInfoService.getAuthInfo(code);
            //获取到openId
            openId = authInfo.get("Openid");
        }
        // 获取基础刷新的接口访问凭证（目前还没明白为什么用authInfo.get("AccessToken");这里面的access_token就不行）
        String accessToken = WeixinUtil.getAccessToken().getToken();
        //获取到微信用户的信息
        WeiXinUser userinfo = weiXinUserInfoService.getUserInfo(accessToken ,openId);

        return userinfo;
    }





}
