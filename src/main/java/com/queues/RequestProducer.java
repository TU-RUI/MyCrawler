package com.queues;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.crawler.Crawler;
import com.entity.Request;
import com.entity.URLType;
import com.http.HttpMethod;
import com.utils.ConfigUtils;

public class RequestProducer implements Runnable {
    private static final int MAX_LENGTH = 100;
    private static RequestProducer instance = null;
    private static int count = 1;
    private static RedisQueue queue = Crawler.getInstance().getQueue();
    private static final String PIXIV_URL = "http://www.pixiv.net";
    private static final String USER_INFO_URL = "http://www.pixiv.net/member.php";
    private static final String USER_WORKS_URL = "http://www.pixiv.net/member_illust.php";
    

    private RequestProducer() {
        ConfigUtils cu = new ConfigUtils();
        String s = cu.readString("id");
        if (s != null && s.equals("")) {
            count = Integer.valueOf(s);
        }
    };

    public static RequestProducer getInstance() {
        if (instance == null) {
            synchronized (instance) {
                if (instance == null) {
                    instance = new RequestProducer();
                }
            }
        }
        return instance;
    }

    // 自动根据ID生产request
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            if (Crawler.getInstance().getQueue().len() < MAX_LENGTH / 2) {
                for (int i = 0; i < MAX_LENGTH / 2; i++) {
                    queue.push(buildUserInfoRequest(count++));
                }
            } else {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * @Description 根据用户ID生成相应用户信息页
     * @param id
     *            用户ID
     * @return
     */
    public Request buildUserInfoRequest(int id) {
        // 个人主页
        // http://www.pixiv.net/member.php?id=1577302
        Request request = new Request();
        request.setUrl(USER_INFO_URL);
        request.setHttpMethod(HttpMethod.GET);
        request.setMaxReqCount(3);
        request.setUrlType(URLType.AUTHOR_INFO_PAGE);
        request.setReferer(PIXIV_URL);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        request.setParams(map);
        return request;
    }

    /**
     * 
     * @Description 根据用户ID和页码生成用户作品列表页
     * @param id
     *            用户ID
     * @param page
     *            页码
     * @return
     */
    public Request buildUserWorksRequest(int id, int page) {
        Request request = new Request();
        // 作品列表,第一页
        // http://www.pixiv.net/member_illust.php?id=1577302
        // 后几页
        // http://www.pixiv.net/member_illust.php?id=1577302&type=all&p=2
        request.setUrl(USER_WORKS_URL);
        request.setHttpMethod(HttpMethod.GET);
        request.setMaxReqCount(3);
        request.setUrlType(URLType.AUTHOR_INFO_PAGE);
        request.setReferer(USER_INFO_URL+"?id="+id);
        HashMap<String, String> map = new HashMap<String, String>();
        map.put("id", String.valueOf(id));
        if (page >= 2) {
            map.put("type", "all");
            map.put("p", String.valueOf(page));
        }
        request.setParams(map);
        return request;
    }

}
