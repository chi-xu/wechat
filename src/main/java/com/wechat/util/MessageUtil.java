package com.wechat.util;

import com.thoughtworks.xstream.XStream;
import com.wechat.pojo.*;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class MessageUtil {
    public static final String MESSAGE_TEXT = "text";
    public static final String MESSAGE_NEWS = "news";
    public static final String MESSAGE_IMAGE = "image";
    public static final String MESSAGE_VOICE = "voice";
    public static final String MESSAGE_VIDEO = "video";
    public static final String MESSAGE_MUSIC = "music";
    public static final String MESSAGE_LINK = "link";
    public static final String MESSAGE_LOCATION = "location";
    public static final String MESSAGE_EVENT = "event";
    public static final String MESSAGE_SUBSCRIBE = "subscribe";
    public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
    public static final String MESSAGE_CLICK = "CLICK";
    public static final String MESSAGE_VIEW = "VIEW";
    public static final String MESSAGE_SCANCODE= "scancode_push";




    /**
     * xml转为 map集合
     * @param request
     * @return
     */
    public static Map<String,String> xmlToMap(HttpServletRequest request) throws IOException {
        Map<String,String> map = new HashMap<String, String>();
        SAXReader reader = new SAXReader();

        InputStream ins = request.getInputStream();
        Document doc = null;
        try {
            doc = reader.read(ins);
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        Element root = doc.getRootElement();
        List<Element> list = root.elements();

        for(Element e:list){
            map.put(e.getName(),e.getText());
        }
        ins.close();
        return map;
    }

    /**
     * 文本消息转为 Xml
     * @param textMessage
     * @return
     */

    public static String textMessageToXml(TextMessage textMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",textMessage.getClass());
        return xstream.toXML(textMessage);

    }

    public static String initText(String toUserName,String fromUserName,String content){
        TextMessage text = new TextMessage();
        text.setFromUserName(toUserName);
        text.setToUserName(fromUserName);
        text.setMsgType(MessageUtil.MESSAGE_TEXT);
        text.setCreateTime(new Date().getTime());
        text.setContent(content);
        return textMessageToXml(text);
    }

    /**
     * 主菜单
     * @return
     */
    public static String menuText(){
        StringBuffer sb = new StringBuffer();
        sb.append("欢迎您的关注,您可以通过使用以下命令来完成不同的操作：\n\n");
        sb.append("1、绑定学号 格式：绑定+学号+身份证后6位（加号必须要有，下面的命令一样），用于微信号与学号的绑定，是后面操作的前提\n");
        sb.append("2、解绑 格式：绑定+学号+身份证后6位\n");
        sb.append("3、查询天气 格式：地名+天气，如：常州天气\n");
        sb.append("4、翻译 格式：翻译+翻译内容，如：翻译我爱你\n");
        sb.append("5、笑话 格式：笑话\n");
        sb.append("6、快递查询 格式：先发送‘快递查询’，再根据提示发送公司编号和运单号\n");
        sb.append("7、人脸识别 格式：直接发送人脸图片\n");
        sb.append("-----------------------------\n");
        sb.append("回复【帮助】调出此菜单。");
        return sb.toString();
    }

    public static String firstMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("本套课程介绍微信公众号开发");
        return sb.toString();
    }

    public static String SecondMenu(){
        StringBuffer sb = new StringBuffer();
        sb.append("慕课网是垂直的互联网IT技能免费学习网站。以独家视频教程、在线编程工具、学习计划、问答社区为核心特色。在这里，你可以找到最好的互联网技术牛人，也可以通过免费的在线公开视频课程学习国内领先的互联网IT技术。");
        return sb.toString();
    }

    public static String binding(){
        StringBuffer sb = new StringBuffer();
        sb.append("绑定格式错误！\n");
        sb.append("格式为：绑定+学号+身份证后六位");
        return sb.toString();
    }

    public static String remove(){
        StringBuffer sb = new StringBuffer();
        sb.append("解绑格式错误！\n");
        sb.append("格式为：解绑+学号+身份证后六位");
        return sb.toString();
    }

    public static String translate(){
        StringBuffer sb=new StringBuffer();
        sb.append("词组翻译使用指南\n\n");
        sb.append("使用示例：\n");
        sb.append("翻译足球\n");
        sb.append("翻译中国足球\n");
        sb.append("翻译football\n\n");
        sb.append("回复【帮助】显示主菜单。");
        return sb.toString();
    }

    public static String weather(){
        StringBuffer sb=new StringBuffer();
        sb.append("天气查询格式：地名天气\n");
        sb.append("如：上海天气");
        return sb.toString();
    }


    public static String robot(){
        StringBuffer sb=new StringBuffer();
        sb.append("您已进入智能聊天模式,如需退出,请输入'取消'!");
        return sb.toString();
    }

    public static String quitRobot(){
        StringBuffer sb=new StringBuffer();
        sb.append("您已退出智能聊天模式！");
        return sb.toString();
    }

    public static String parcel(){
        StringBuffer sb=new StringBuffer();
        sb.append("您输入的快递公司编号似乎有问题,请检查！");
        return sb.toString();
    }

    public static String register(){
        StringBuffer sb = new StringBuffer();
        sb.append("绑定成功！");
        return sb.toString();
    }

    public static String registerFail(){
        StringBuffer sb = new StringBuffer();
        sb.append("绑定失败！\n");
        sb.append("请检查学号和密码是否错误！");
        return sb.toString();
    }

    public static String removeSuccess(){
        StringBuffer sb = new StringBuffer();
        sb.append("解绑成功!");

        return sb.toString();
    }

    public static String removeFail(){
        StringBuffer sb = new StringBuffer();
        sb.append("解绑失败！\n");
        sb.append("请检查学号和密码是否错误！");
        return sb.toString();
    }

    public static String registerAgain(){
        StringBuffer sb = new StringBuffer();
        sb.append("请根据提示绑定学号！");
        return sb.toString();
    }

    public static String signSuccess(){
        StringBuffer sb = new StringBuffer();
        sb.append("您已成功签到,积分+3！");
        return sb.toString();
    }

    public static String signAgain(){
        StringBuffer sb = new StringBuffer();
        sb.append("您今日已经签到过了，请明天再来吧！");
        return sb.toString();
    }

    /**
     * 图文消息转为xml
     * @param newsMessage
     * @return
     */
    public static String newsMessageToXml(NewsMessage newsMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",newsMessage.getClass());
        xstream.alias("item",new News().getClass());
        return xstream.toXML(newsMessage);

    }

    /**
     * 图片消息转为xml
     * @param imageMessage
     * @return
     */
    public static String imageMessageToXml(ImageMessage imageMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",imageMessage.getClass());
        return xstream.toXML(imageMessage);

    }

    /**
     * 语音消息转为xml
     * @param voiceMessage
     * @return
     */
    public static String VoiceMessageToXml(VoiceMessage voiceMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",voiceMessage.getClass());
        return xstream.toXML(voiceMessage);

    }

    /**
     * 音乐消息转为xml
     * @param musicMessage
     * @return
     */
    public static String musicMessageToXml(MusicMessage musicMessage){
        XStream xstream = new XStream();
        xstream.alias("xml",musicMessage.getClass());
        return xstream.toXML(musicMessage);

    }

    /**
     * 图文消息的组装
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initNewsMessage(String toUserName,String fromUserName){
        String message =null;
        //创建集合接收消息体
        List<News> newsList = new ArrayList<News>();
        NewsMessage newsMessage = new NewsMessage();

        News news = new News();
        news.setTitle("慕课网介绍");
        news.setDescription("慕课网是垂直的互联网IT技能免费学习网站");
        news.setPicUrl("http://23734896qt.51mypc.cn/wechat/image/1.jpg");
        news.setUrl("www.imooc.com");

        newsList.add(news);

        newsMessage.setToUserName(fromUserName);
        newsMessage.setFromUserName(toUserName);
        newsMessage.setCreateTime(new Date().getTime());
        newsMessage.setMsgType(MESSAGE_NEWS);
        newsMessage.setArticles(newsList);
        newsMessage.setArticleCount(newsList.size());

        message = newsMessageToXml(newsMessage);
        return message;

    }

    /**
     * 组装图片消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initImageMessage(String toUserName,String fromUserName){
        String message = null;
        Image image = new Image();
        image.setMediaId("tXbDJzIzikkH6oHvvmjSPY246s0FOz-tlqg2sYoZO6a6FifbE53trdpZMsByzxeP");
        ImageMessage imageMessage = new ImageMessage();
        imageMessage.setFromUserName(toUserName);
        imageMessage.setToUserName(fromUserName);
        imageMessage.setMsgType(MESSAGE_IMAGE);
        imageMessage.setCreateTime(new Date().getTime());
        imageMessage.setImage(image);
        message = imageMessageToXml(imageMessage);
        return message;

    }

    /**
     * 组装语音消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initVoiceMessage(String toUserName,String fromUserName){
        String message = null;
        Voice voice = new Voice();
        voice.setMediaId("tXbDJzIzikkH6oHvvmjSPY246s0FOz-tlqg2sYoZO6a6FifbE53trdpZMsByzxeP");
        VoiceMessage voiceMessage = new VoiceMessage();
        voiceMessage.setFromUserName(toUserName);
        voiceMessage.setToUserName(fromUserName);
        voiceMessage.setMsgType(MESSAGE_VOICE);
        voiceMessage.setCreateTime(new Date().getTime());
        voiceMessage.setVoice(voice);
        message = VoiceMessageToXml(voiceMessage);
        return message;

    }

    /**
     * 组装音乐消息
     * @param toUserName
     * @param fromUserName
     * @return
     */
    public static String initMusicMessage(String toUserName,String fromUserName){
        String message = null;
        Music music = new Music();
        music.setThumbMediaId("cWsofoAh_P2W0lkU8ui48u6Sy3CQwuYfyq8vLMsytVeOXA19gEd1XdAZo8TF_dub");
        music.setTitle("see you again");
        music.setDescription("速7主题曲");
        music.setMusicUrl("http://23734896qt.51mypc.cn/wechat/music/See You Again.mp3");
        music.setHQMusicUrl("http://23734896qt.51mypc.cn/wechat/music/See You Again.mp3");

        MusicMessage musicMessage = new MusicMessage();
        musicMessage.setFromUserName(toUserName);
        musicMessage.setToUserName(fromUserName);
        musicMessage.setMsgType(MESSAGE_MUSIC);
        musicMessage.setCreateTime(new Date().getTime());
        musicMessage.setMusic(music);
        message = musicMessageToXml(musicMessage);
        return message;

    }
}



