package com.wechat.test;

import com.sun.deploy.net.HttpUtils;
import com.wechat.pojo.AccessToken;
import com.wechat.util.WeixinUtil;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class WeixinTest {
    public static void main(String[] args) {
        try {
            AccessToken token = WeixinUtil.getAccessToken();
            System.out.println("票据："+token.getToken());
            System.out.println("有效时间:"+token.getExpiresIn());
            /*String path = "C:/Users/John Dawson/Desktop/voice.amr";
            String mediaId = WeixinUtil.upload(path,token.getToken(),"voice");
            System.out.println(mediaId);*/

            /**
             * 创建菜单
             */
            /*String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
            int result = WeixinUtil.createMenu(token.getToken(),menu);
            if(result == 0){
                System.out.println("创建菜单成功");
            }else{
                System.out.println("错误码："+result);
            }*/

            /**
             * 查询菜单
             */
            /*JSONObject jsonObject = WeixinUtil.queryMenu(token.getToken());
            System.out.println(jsonObject);*/


            /**
             * 删除菜单
             */
            /*int result = WeixinUtil.deleteMenu(token.getToken());
            if(result == 0 ){
                System.out.println("菜单删除成功");
            }else {
                System.out.println(result);
            }*/



           /*String url = "http://pic11.nipic.com/20101111/6153002_002722872554_2.jpg";
           System.out.println("结果："+WeixinUtil.face(url));*/


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
