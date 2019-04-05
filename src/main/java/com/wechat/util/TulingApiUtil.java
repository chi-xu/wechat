package com.wechat.util;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class TulingApiUtil {
    /**
     * 调用图灵机器人api接口，获取智能回复内容，解析获取自己所需结果
     * @param content
     * @return
     */

    private static final String KEY="a484c92ce14a4c09a7180ded657efba6";

    public static String getTulingResult(String content){

        //此处为图灵api接口，参数key需要自己去注册申请
        String apiUrl = "http://www.tuling123.com/openapi/api?key="+KEY+"&info=";
        String param = "";

        try {
            param = apiUrl+ URLEncoder.encode(content,"utf-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        } //将参数转为url编码

        /** 发送httpget请求 */
        HttpGet request = new HttpGet(param);
        String result = "";
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpResponse response = httpClient.execute(request);
            int code =response.getStatusLine().getStatusCode();
            if(code==200){
                result = EntityUtils.toString(response.getEntity());
            }
            else {
                //	System.out.println("code="+code);
            }
        } catch (ClientProtocolException e) {
            System.out.println("ClientProtocolException");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("IOException");
            e.printStackTrace();
        }


        /** 请求失败处理 */
        if(null==result){
            //	System.out.println("null==result");
            return "对不起，你说的话真是太高深了……";
        }

        try {
            StringBuffer bf=new StringBuffer();
            String s="";
            JSONObject json = JSONObject.fromObject(result);
            //以code=100000为例，参考图灵机器人api文档
            /**
             * 	 code  	说明
             100000	文本类
             200000	链接类
             302000	新闻类
             308000	菜谱类
             */
            if(100000==json.getInt("code")){
                s = json.getString("text");
                bf.append(s);
            }
            else if(200000==json.getInt("code")){
                s = json.getString("text");
                bf.append(s);
                bf.append("\n");
                s = json.getString("url");
                bf.append(s);
            }
            else if(302000==json.getInt("code")){
                //s = json.getString("text");
                s="待开发有点麻烦！\n";
                bf.append(s);
            }
            else if(308000==json.getInt("code")){
                //s = json.getString("text");
                s="待开发有点麻烦！\n";
                bf.append(s);
            }
            result=bf.toString();
        } catch (JSONException e) {
            System.out.println("JSONException");
            e.printStackTrace();
        }
        //System.out.println("机器人回复->"+result);
        return result;
    }

}
