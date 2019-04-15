package com.wechat.controller;

import com.wechat.menu.ClickButton;
import com.wechat.pojo.Borrow;
import com.wechat.pojo.Point;
import com.wechat.pojo.Users;
import com.wechat.service.BorrowService;
import com.wechat.service.PointService;
import com.wechat.service.StatusService;
import com.wechat.service.UsersService;
import com.wechat.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.logging.SimpleFormatter;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/weixin")
public class WechatController {


    @Resource
    private UsersService usersService;
    @Resource
    private PointService pointService;
    @Resource
    private BorrowService borrowService;
    @Resource
    private StatusService statusService;

    @RequestMapping(value = "check", method = RequestMethod.GET)
    public void weChatVerification( String signature,  String timestamp,  String nonce,  String echostr,
                                   HttpServletRequest req, HttpServletResponse resp) {


        try {
            PrintWriter out = resp.getWriter();
            if (CheckUtil.checkSignature(signature,timestamp,nonce)) {
                System.out.println("success");
                out.print(echostr);
            } else {
                System.out.println("fail");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 用户与微信服务器的所有交互从此接口进入
     * @param req
     * @param resp
     * @param session
     * @throws IOException
     */
    @RequestMapping(value = "check", method = RequestMethod.POST)
    public void receiveTextMsg(HttpServletRequest req, HttpServletResponse resp, HttpSession session) throws IOException{

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Map<String,String> map = MessageUtil.xmlToMap(req);
        String fromUserName = map.get("FromUserName");
        String toUserName = map.get("ToUserName");
        String msgType = map.get("MsgType");
        String content = map.get("Content");

        String  message = null;
        /**
         * 接收文本消息
         */
        if(MessageUtil.MESSAGE_TEXT.equals(msgType)){
            if(this.statusService.chartStatus(fromUserName)==1){ //如果聊天状态为1，则调用机器人
                if("取消".equals(content)){ //如果输入取消，则退出智能聊天模式
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.quitRobot());
                    this.statusService.chatStatusToZero(fromUserName); //退出后将聊天状态值置为0，回到正常模式
                }else {
                    message = MessageUtil.initText(toUserName, fromUserName, TulingApiUtil.getTulingResult(content));
                }

            }else if(this.statusService.parcelStatus(fromUserName)==1){ //如果处于快递查询状态
                String com = content.substring(0,content.indexOf(" ")); //快递公司编号
                String no = content.substring(content.indexOf(" ")+1,content.length()); //快递单号
                Pattern pattern = Pattern.compile("[a-z]*");
                if(!pattern.matcher(com).matches()){ //如果输入的编号不全是小写字母，则判定有误
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.parcel());
                }else{
                    message = MessageUtil.initText(toUserName,fromUserName,WeixinUtil.parcel(com,no));
                    this.statusService.parcelStatusToZero(fromUserName); //快递查询后将快递状态值置为0，回到正常模式
                }


            } else if (content.startsWith("翻译")){
                String word = content.replaceAll("^翻译", "").trim();
                if ("".equals(word)) {
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.translate());
                } else {
                    message = MessageUtil.initText(toUserName, fromUserName, WeixinUtil.translateFull(word));
                }
            }else if(content.startsWith("绑定")){  // 绑定学号
                Pattern pattern = Pattern.compile("^\\绑定\\+\\d{10}\\+\\d{6}");
                if(!pattern.matcher(content).matches()){ //格式不匹配
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.binding());
                }else {
                    message = binding(fromUserName,toUserName,content);
                }

            }else if(content.endsWith("天气")){ //天气查询
                 String word = content.replaceAll("天气","").trim();
                 if("".equals(word)){
                     message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.weather());
                 }else{
                     message = MessageUtil.initText(toUserName,fromUserName,WeixinUtil.weather(word));
                 }
            }else if("快递查询".equals(content)){
                 message = MessageUtil.initText(toUserName,fromUserName,WeixinUtil.parcelCompany());
                 this.statusService.parcelStatusToOne(fromUserName); //将状态值置为1,处于快递查询状态

            }else if("笑话".equals(content)){
                 message = MessageUtil.initText(toUserName,fromUserName,WeixinUtil.joke());
            }else if("帮助".equals(content)){
                message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
            }else if(content.startsWith("解绑")){ //解绑
                Pattern pattern = Pattern.compile("^\\解绑\\+\\d{10}\\+\\d{6}");
                if(!pattern.matcher(content).matches()){ //格式不匹配
                    message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.remove());
                }else {
                    message = remove(fromUserName,toUserName,content);
                }
            }


        } /**接收图片消息*/
         else if(MessageUtil.MESSAGE_IMAGE.equals(msgType)){
            String url = map.get("PicUrl");
            message = MessageUtil.initText(toUserName,fromUserName,WeixinUtil.face(url));

        }/**接收事件推送*/
         else if(MessageUtil.MESSAGE_EVENT.equals(msgType)){
            String eventType = map.get("Event");
            if(MessageUtil.MESSAGE_SUBSCRIBE.equals(eventType)){  //用户关注时弹出提示菜单
                this.statusService.addOpenid(fromUserName);
                message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.menuText());
            }else if(MessageUtil.MESSAGE_UNSUBSCRIBE.equals((eventType))){ //取消关注
                String userId = this.usersService.queryId(fromUserName);
                if(userId != null){
                    this.usersService.remove(userId);
                }
                this.statusService.delOpenid(fromUserName);

            }else if(MessageUtil.MESSAGE_CLICK.equals(eventType)){  //点击菜单拉取消息时的事件推送
                String eventKey = map.get("EventKey");
                if(eventKey.equals("34")) { //智能聊天
                    message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.robot());//提醒用户进入智能聊天模式
                    this.statusService.chatStatusToOne(fromUserName); //将状态值置为1,处于智能聊天状态
                }else if (eventKey.equals("31")){ //点击签到
                    message = sign(fromUserName,toUserName);
                }else if(eventKey.equals("13")){ //借书查询
                    message = MessageUtil.initText(toUserName,fromUserName,borrowBook(fromUserName));
                }
            }else if(MessageUtil.MESSAGE_VIEW.equals(eventType)){  //点击菜单跳转链接时的事件推送
                String url = map.get("EventKey");
                message = MessageUtil.initText(toUserName,fromUserName,url);
            }
        }

        System.out.println(message);
        out.print(message);

    }

    /**
     * 用户绑定学号
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */

    public String binding(String fromUserName,String toUserName,String content){
        String message = null;
        Users users = new Users();
        users.setOpenid(fromUserName);   //fromUserName即为用户的openid
        users.setUserId(content.substring(3,13)); //取用户学号
        users.setPassword(content.substring(14)); //取用户密码
        if(this.usersService.queryCount(users)>0){ //如果有此用户，则插入该用户openid
            this.usersService.add(users);
            message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.register());
        }else {
            message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.registerFail());
        }
        return  message;

    }

    /**
     * 用户解绑学号
     * @param fromUserName
     * @param toUserName
     * @param content
     * @return
     */

    public String remove(String fromUserName,String toUserName,String content){
        String message = null;
        Users users = new Users();
        users.setOpenid(fromUserName);   //fromUserName即为用户的openid
        users.setUserId(content.substring(3,13)); //取用户学号
        users.setPassword(content.substring(14)); //取用户密码
        if(this.usersService.queryCount(users)>0){ //如果有此用户，则删除该用户openid
            this.usersService.remove(users.getUserId());
            message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.removeSuccess());
        }else {
            message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.removeFail());
        }
        return message;
    }

    /**
     * 提示用户第几个签到
     * @return
     */

    public  String signSuccess(){
        StringBuffer sb = new StringBuffer();
        CheckSignUtil checkSignUtil = new CheckSignUtil();
        sb.append("您今日第"+this.pointService.queryNum(checkSignUtil.todayTime())+"个签到,积分+3");
        return sb.toString();
    }


    /**
     * 借书查询
     * @param fromUserName
     * @return
     */
    public String borrowBook(String fromUserName){
        StringBuffer sb = new StringBuffer();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String result = this.usersService.queryId(fromUserName); //根据Openid查出已绑定用户的学号
        if(result != null){  //如果此用户已绑定
            List<Borrow> list = this.borrowService.queryBorrow(result);
            if(list.size()>0){
                sb.append("您在借图书共"+this.borrowService.selCount(result)+"本\n");
                sb.append("----------------------------\n");
                for(int i=0;i<list.size();i++){ //遍历返回的list集合
                    Borrow borrow = list.get(i); //获取每一个Borrow对象
                    sb.append("编号:"+borrow.getBookId()+"\n");
                    sb.append("书名:"+borrow.getBook().getBookName()+"\n");
                    sb.append("借书日期:"+sdf.format(borrow.getStartDate())+"\n");
                    sb.append("还书日期:"+sdf.format(borrow.getEndDate())+"\n\n");
                    sb.append("......................\n");
                }
            }else {
                sb.append("少年,你一本书也没借,趁年轻多读读书吧！");
            }


        }else {
            sb.append("请先绑定学号！");
        }
        return sb.toString();
    }


    /**
     * 每日签到
     * @param fromUserName
     * @param toUserName
     * @return
     */
    public String sign(String fromUserName,String toUserName){
        String message = null;
        String result = null;
        CheckSignUtil checkSignUtil = new CheckSignUtil();
        Users users = new Users();
        users.setOpenid(fromUserName);
        result = this.usersService.queryId(users.getOpenid());//根据此openid查询该用户是否绑定
        System.out.println(result);
        if(result == null){
            message = MessageUtil.initText(toUserName,fromUserName,MessageUtil.registerAgain());
        }else{
            if(this.pointService.checkSign(result)==null){ //如果积分为0，说明是首次签到
                this.pointService.firstSign(result); //插入积分值
                message = MessageUtil.initText(toUserName,fromUserName,signSuccess());
            }else {
                try {
                    //如果当天已经签到过，则不更新
                    if (checkSignUtil.checkAllotSigin(this.pointService.queryTime(result)) == 1) {
                        message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.signAgain());
                    } else { //否则，更新签到表
                        this.pointService.sign(result);//更新积分
                        message = MessageUtil.initText(toUserName, fromUserName, signSuccess());
                    }
                }catch (Exception e) {
                    e.printStackTrace();
                }

            }

        }
        return message;
    }


}



