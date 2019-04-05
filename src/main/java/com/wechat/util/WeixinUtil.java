package com.wechat.util;

import com.wechat.joke.JokeResult;
import com.wechat.menu.Button;
import com.wechat.menu.ClickButton;
import com.wechat.menu.Menu;
import com.wechat.menu.ViewButton;
import com.wechat.parcel.ParcelResult;
import com.wechat.parcel.List;
import com.wechat.parcelcompany.CompanyResult;
import com.wechat.pojo.AccessToken;
import com.wechat.weather.*;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信工具类
 */
public class WeixinUtil {
    private static final String APPID = "wx67a4d704da47e223";
    private static final String APPSECRET = "7ae64b574dca683cc5db2b637b3b631c";

    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
    private static final String UPLOAD_URL ="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";

    private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    private static final String QUERY_MENUN_URL = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

    private  static final String DELETE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

    /**
     * get请求
     * @param url
     * @return
     */
    public static JSONObject doGetStr(String url){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        JSONObject jsonObject = null;
        try {
            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if(entity != null){
                String result = EntityUtils.toString(entity);
                jsonObject = JSONObject.fromObject(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    /**
     * post请求
     * @param url
     * @param outStr
     * @return
     */
    public static  JSONObject doPostStr(String url,String outStr){
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httpPost = new HttpPost(url);
        JSONObject jsonObject = null;
        try {
            httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
            HttpResponse response = httpClient.execute(httpPost);
            String result = EntityUtils.toString(response.getEntity(),"UTF-8");
            jsonObject = JSONObject.fromObject(result);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;

    }

    /**
     * 获取access_token
     * @return
     */
    public static AccessToken getAccessToken(){
        AccessToken token = new AccessToken();
            String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET",APPSECRET);
            JSONObject jsonObject = doGetStr(url);
            if(jsonObject != null){
                token.setToken(jsonObject.getString("access_token"));
                token.setExpiresIn(jsonObject.getInt("expires_in"));
        }
        return token;
    }

    /**
     * 文件上传
     * @param filePath
     * @param accessToken
     * @param type
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     * @throws KeyManagementException
     */
    public static String upload(String filePath, String accessToken,String type) throws IOException, NoSuchAlgorithmException, NoSuchProviderException, KeyManagementException {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new IOException("文件不存在");
        }

        String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE",type);

        URL urlObj = new URL(url);
        //连接
        HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();

        con.setRequestMethod("POST");
        con.setDoInput(true);
        con.setDoOutput(true);
        con.setUseCaches(false);

        //设置请求头信息
        con.setRequestProperty("Connection", "Keep-Alive");
        con.setRequestProperty("Charset", "UTF-8");

        //设置边界
        String BOUNDARY = "----------" + System.currentTimeMillis();
        con.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + BOUNDARY);

        StringBuilder sb = new StringBuilder();
        sb.append("--");
        sb.append(BOUNDARY);
        sb.append("\r\n");
        sb.append("Content-Disposition: form-data;name=\"file\";filename=\"" + file.getName() + "\"\r\n");
        sb.append("Content-Type:application/octet-stream\r\n\r\n");

        byte[] head = sb.toString().getBytes("utf-8");

        //获得输出流
        OutputStream out = new DataOutputStream(con.getOutputStream());
        //输出表头
        out.write(head);

        //文件正文部分
        //把文件已流文件的方式 推入到url中
        DataInputStream in = new DataInputStream(new FileInputStream(file));
        int bytes = 0;
        byte[] bufferOut = new byte[1024];
        while ((bytes = in.read(bufferOut)) != -1) {
            out.write(bufferOut, 0, bytes);
        }
        in.close();

        //结尾部分
        byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//定义最后数据分隔线

        out.write(foot);

        out.flush();
        out.close();

        StringBuffer buffer = new StringBuffer();
        BufferedReader reader = null;
        String result = null;
        try {
            //定义BufferedReader输入流来读取URL的响应
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            if (result == null) {
                result = buffer.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        JSONObject jsonObj = JSONObject.fromObject(result);
        System.out.println(jsonObj);
        String typeName = "media_id";
        if(!"image".equals(type)&&!"voice".equals(type)&&!"video".equals(type)){
            typeName = type + "_media_id";
        }
        String mediaId = jsonObj.getString(typeName);
        return mediaId;
    }

    /**
     * 组装菜单
     * @return
     */
    public static Menu initMenu(){
        Menu menu = new Menu();

        //主菜单二的子菜单
        ViewButton button11 = new ViewButton();
        button11.setName("成绩查询");
        button11.setType("view");
        button11.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx67a4d704da47e223&redirect_uri=http://23734896qt.51mypc.cn/wechat/user/getuserinfo&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect");

        ViewButton button12 = new ViewButton();
        button12.setName("课表查询");
        button12.setType("view");
        button12.setUrl("http://23734896qt.51mypc.cn/wechat/test.jsp");

        ClickButton button13 = new ClickButton();
        button13.setName("借书查询");
        button13.setType("click");
        button13.setKey("13");

        ViewButton button14 = new ViewButton();
        button14.setName("四六级查询");
        button14.setType("view");
        button14.setUrl("http://23734896qt.51mypc.cn/wechat/cet.jsp");

        ViewButton button15 = new ViewButton();
        button15.setName("PU口袋校园");
        button15.setType("view");
        button15.setUrl("http://m.pocketuni.net/public/login.html");


        //主菜单一的子菜单
        ViewButton button21 = new ViewButton();
        button21.setName("校园概况");
        button21.setType("view");
        button21.setUrl("http://www.jstu.edu.cn/30/list.htm");

        ViewButton button22 = new ViewButton();
        button22.setName("理工地图");
        button22.setType("view");
        button22.setUrl("https://www.expoon.com/24887/");

        ViewButton button23 = new ViewButton();
        button23.setName("理工影像");
        button23.setType("view");
        button23.setUrl("http://www.jstu.edu.cn/30/list.htm");

        ViewButton button24 = new ViewButton();
        button24.setName("失物招领");
        button24.setType("view");
        button24.setUrl("http://23734896qt.51mypc.cn/wechat/lost/queryLost");


        //主菜单三的子菜单
        ClickButton button31 = new ClickButton();
        button31.setName("每日签到");
        button31.setType("click");
        button31.setKey("31");

        ViewButton button32 = new ViewButton();
        button32.setName("心愿墙");
        button32.setType("view");
        button32.setUrl("http://23734896qt.51mypc.cn/wechat/wish/queryWish");

        ViewButton button33 = new ViewButton();
        button33.setName("微社区");
        button33.setType("view");
        button33.setUrl("https://buluo.qq.com/p/barindex.html?bid=13318");

        ClickButton button34 = new ClickButton();
        button34.setName("机器人聊天");
        button34.setType("click");
        button34.setKey("34");

        ViewButton button35 = new ViewButton();
        button35.setName("留言建议");
        button35.setType("view");
        button35.setUrl("http://23734896qt.51mypc.cn/wechat/liuyan.jsp");

        //一级菜单
        Button button1 = new Button();
        button1.setName("我的大学");
        button1.setSub_button(new Button[]{button21,button22,button23,button24});

        Button button2 = new Button();
        button2.setName("学习助手");
        button2.setSub_button(new Button[]{button11,button12,button13,button14,button15});

        Button button3 = new Button();
        button3.setName("娱乐助手");
        button3.setSub_button(new Button[]{button31,button32,button33,button34,button35});

        menu.setButton(new Button[]{button1,button2,button3});

        return menu;


    }


    public static int createMenu(String token,String menu) throws ParseException, IOException{
        int result = 0;
        String url = CREATE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doPostStr(url,menu);
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }

    public static JSONObject queryMenu(String token){
        String url = QUERY_MENUN_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doGetStr(url);
        return jsonObject;

    }

    public static int deleteMenu(String token){
        String url = DELETE_MENU_URL.replace("ACCESS_TOKEN",token);
        JSONObject jsonObject = doGetStr(url);
        int result = 0;
        if(jsonObject != null){
            result = jsonObject.getInt("errcode");
        }
        return result;
    }


    /**
     * 百度翻译
     * @param source
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String translateFull(String source) throws ParseException, IOException {
        String url = "http://api.fanyi.baidu.com/api/trans/vip/translate";//API接口

        url = url.replace("KEYWORD", URLEncoder.encode(source, "UTF-8"));


        Map<String, String> params = new HashMap<String, String>();
        params.put("q", source);
        params.put("from", "auto");
        params.put("to", "auto");

        params.put("appid", "20190131000260908");
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);
        String src = "20190131000260908" + source + salt + "8Bj_NpoTDFnMUSwHIAyV"; // 加密前的原文
        params.put("sign", MD5.md5(src));

        SSLContext sslcontext = null;
        try {
            sslcontext = SSLContext.getInstance("TLS");
            sslcontext.init(null, new TrustManager[]{myX509TrustManager}, null);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }


        StringBuilder builder = new StringBuilder(url);
        if (url.contains("?")) {
            builder.append("&");
        } else {
            builder.append("?");
        }
        int i = 0;
        for (String key : params.keySet()) {
            String value = params.get(key);
            if (value == null) { // 过滤空的key
                continue;
            }

            if (i != 0) {
                builder.append('&');
            }

            builder.append(key);
            builder.append('=');
            builder.append(encode(value));

            i++;
        }
        String sendUrl =builder.toString();


        URL uri = new URL(sendUrl); // 创建URL对象
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        if (conn instanceof HttpsURLConnection) {
            ((HttpsURLConnection) conn).setSSLSocketFactory(sslcontext.getSocketFactory());
        }

        conn.setConnectTimeout(10000); // 设置相应超时
        conn.setRequestMethod("GET");
        int statusCode = conn.getResponseCode();
        if (statusCode != HttpURLConnection.HTTP_OK) {
            System.out.println("Http错误码：" + statusCode);
        }

        // 读取服务器的数据
        InputStream is = conn.getInputStream();


        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder builder1 = new StringBuilder();
        String line = null;

        System.out.println(br);
        while ((line = br.readLine()) != null) {
            builder1.append(line);
        }
        JSONObject  jsonObject = JSONObject.fromObject( builder1.toString());

        //close(br); // 关闭数据流
        //close(is); // 关闭数据流
        conn.disconnect(); // 断开连接
        StringBuffer dst = new StringBuffer();
        java.util.List<Map> list = (java.util.List) jsonObject.get("trans_result");
        for(Map map : list){
            dst.append(map.get("dst"));
        }
        return dst.toString();
    }

    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };


    /**
     * 天气查询
     * @param source
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String weather(String source) throws ParseException, IOException {
        String url="http://v.juhe.cn/weather/index?format=2&cityname=KEYWORD&key=7930f15d7a56f17c1744356a1bdf02a4";
        url = url.replace("KEYWORD",URLEncoder.encode(source,"UTF-8"));
        JSONObject jsonObject = doGetStr(url);
        String error_code = jsonObject.getString("error_code");
        StringBuffer sb = new StringBuffer();
        if("0".equals(error_code)){
            WeatherResult weatherResult = (WeatherResult) JSONObject.toBean(jsonObject,WeatherResult.class);
            Result result = weatherResult.getResult();
            Sk sk = result.getSk();
            Today today = result.getToday();
            //Future future = result.getFuture()[0];
            //Weather_id weather_id =
            sb.append("今日日期:"+today.getDate_y()+"\n");
            sb.append("当前温度:"+sk.getTemp()+"℃\n");
            sb.append("今日温度:"+today.getTemperature()+"\n");
            sb.append("今日天气:"+today.getWeather()+"\n");
            sb.append("穿衣建议:"+today.getDessing_advice()+"\n");
            sb.append("-----------------------------\n");
            sb.append("**未来几天的天气**\n");

            Future[] future = result.getFuture();
            String tem = null; //温度
            String wea = null; //天气
            String wind = null; //风速
            String week = null; //周几
            String date = null; //日期
            for(int i=1;i<future.length;i++){
                tem = (future[i].getTemperature()!=null&&!"".equals(future[i].getTemperature()))?future[i].getTemperature():"";
                wea = (future[i].getWeather()!=null&&!"".equals(future[i].getWeather()))?future[i].getWeather():"";
                wind = (future[i].getWind()!=null&&!"".equals(future[i].getWind()))?future[i].getWind():"";
                week = (future[i].getWeek()!=null&&!"".equals(future[i].getWeek()))?future[i].getWeek():"";
                date = (future[i].getDate()!=null&&!"".equals(future[i].getDate()))?future[i].getDate():"";

                sb.append("日期:"+date+"\n");
                sb.append("周几："+week+"\n");
                sb.append("温度："+tem+"\n");
                sb.append("天气:"+wea+"\n");
                sb.append("风速:"+wind+"\n");
                sb.append("............................\n");


            }
        }
        return sb.toString();
    }


    /**
     * 快递查询
     * @param com
     * @param no
     * @return
     * @throws ParseException
     * @throws IOException
     */
    public static String parcel(String com,String no) throws ParseException, IOException{
        String url = "http://v.juhe.cn/exp/index?key=51e31c552e1d45191c5868e61cd89959&com=COM&no=NO";
        String url1 = url.replaceAll("COM",URLEncoder.encode(com,"UTF-8"));
        url = url1.replaceAll("NO",URLEncoder.encode(no,"UTF-8"));
        JSONObject jsonObject = doGetStr(url);
        String error_code = jsonObject.getString("error_code");
        StringBuffer sb = new StringBuffer();
        if("0".equals(error_code)){
            ParcelResult parcelResult = (ParcelResult) JSONObject.toBean(jsonObject,ParcelResult.class);
            com.wechat.parcel.Result result = parcelResult.getResult();
            sb.append("快递公司:"+result.getCom()+"\n");
            sb.append("快递单号:"+result.getNo()+"\n");
            sb.append("---------------------------------\n");
            List[] plist = result.getList();
            for(List p : plist){
                sb.append("时间:"+p.getDatetime()+"\n");
                sb.append("地点:"+p.getRemark()+"\n");
                sb.append("..............................\n");
            }
        }
        return sb.toString();

    }


    /**
     * 获取快递公司编号
     * @return
     */
    public static String parcelCompany(){
        String url = "http://v.juhe.cn/exp/com?key=51e31c552e1d45191c5868e61cd89959";
        JSONObject jsonObject = doGetStr(url);
        String error_code = jsonObject.getString("error_code");
        StringBuffer sb = new StringBuffer();
        if("0".equals(error_code)){
            CompanyResult companyResult = (CompanyResult)JSONObject.toBean(jsonObject,CompanyResult.class);
            sb.append("请根据物流编号进行查询！\n");
            sb.append("格式为：编号 快递单号\n");
            sb.append("如：sf 111111111111111\n");
            sb.append("————————————————————\n");
            com.wechat.parcelcompany.Result[] result = companyResult.getResult();
            for(com.wechat.parcelcompany.Result r : result){
                sb.append(r.getCom()+"："+r.getNo()+"\n");
                //sb.append("...........................\n");
            }
        }

        return sb.toString();
    }


    public static String joke(){
        //String url = "http://v.juhe.cn/joke/content/list.php?key=cef1f65377d9c2b351d62f6f1d691a70&page=2&pagesize=2&sort=asc&time=1418745237";
        String url = "http://v.juhe.cn/joke/randJoke.php?key=cef1f65377d9c2b351d62f6f1d691a70";
        JSONObject jsonObject = doGetStr(url);
        int error_code = jsonObject.getInt("error_code");
        StringBuffer sb = new StringBuffer();
        if(0==error_code){
            JokeResult jokeResult = (JokeResult)JSONObject.toBean(jsonObject,JokeResult.class);
            com.wechat.joke.Result[] result = jokeResult.getResult();
            //com.wechat.joke.Data[] data = result.getData();
            //for(com.wechat.joke.Result r : result){
                sb.append(result[0].getContent()+"\n\n");
            //}
        }else {
            sb.append("错误码为："+error_code);
        }
        return sb.toString();
    }



}
