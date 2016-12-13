package com.crawler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Request;
import com.entity.Response;
import com.http.HttpDownloader;
import com.http.HttpMethod;
import com.okhttp.OkHttpDownloader;

public class LoginProcess {
//    private static final String username = "991642404@qq.com";
//    private static final String password = "123";
    private static Logger LOGGER = LoggerFactory.getLogger(LoginProcess.class);
    
    public static boolean login(){
        Request request = new Request();
        request.setHttpMethod(HttpMethod.POST);
        request.setMaxReqCount(3);
        request.setUrl("https://www.secure.pixiv.net/login.php");
        Map<String,String> map = new HashMap<String,String>();
        map.put("mode", "login");
        map.put("return_to", "/");
        map.put("pixiv_id", "991642404");
        map.put("pass", "turui123");
        map.put("skip", "1");
        OkHttpDownloader downloader = new OkHttpDownloader();
        try {
            Response response = downloader.process(request);
            LOGGER.info("content:{}\n",response.getContent());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    
    public static boolean login1(){
        Request request = new Request();
        request.setHttpMethod(HttpMethod.POST);
        request.setMaxReqCount(3);
        request.setUrl("https://accounts.pixiv.net/login");
        request.setReferer("https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index");
        Map<String,String> map = new HashMap<String,String>();
        map.put("post_key", "3611e4f265a9b7b631e817e0a71ddfcc");
        map.put("return_to", "http://www.pixiv.net/");
        map.put("lang", "zh");
        map.put("source", "pc");
        map.put("pixiv_id", "991642404");
        map.put("pass", "turui123");
        HttpDownloader downloader = new HttpDownloader();
        try {
            Response response = downloader.process(request);
            LOGGER.info("content:{}\n",response.getContent());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {
        login1();
    }
}
