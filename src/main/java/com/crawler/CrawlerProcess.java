package com.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Request;
import com.entity.Response;
import com.handler.Handler;
import com.okhttp.OkHttpDownloader;
import com.queues.RedisQueue;

public class CrawlerProcess implements Runnable{
	private static Logger LOGGER = LoggerFactory.getLogger(CrawlerProcess.class);
    private Handler handler;
    private RedisQueue queue;
    
    public CrawlerProcess(Handler handler , Crawler crawler){
        this.handler = handler;
        queue = crawler.getQueue();
    }
    
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while(true){
            Request request = null;
                request = queue.bPop();
                if(request == null){
                    continue;
                }
                
                handler.before(request);
                LOGGER.info("消费url:{}",request.getRealUrl());
                if(request.getCurReqCount() >= request.getMaxReqCount()){
                    continue;
                }
                OkHttpDownloader OkdownLoader = new OkHttpDownloader();
                Response response = null;
                try {
                    response = OkdownLoader.process(request);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                if(response == null){
                    if(request.getCurReqCount() < request.getMaxReqCount()){
                        request.setCurReqCount(request.getCurReqCount()+1);
                        queue.push(request);
                    }
                    LOGGER.info("响应为空,重新加入队列:{}",request.getRealUrl());
                    continue;
                }
                int StatusCode = response.getStatusCode();
                //正常状态码
                if(StatusCode >=200 && StatusCode < 300){
                    handler.after(response);                    
                }else if(StatusCode == 401){
                    LoginProcess.login();
                }else{
                    if(request.getCurReqCount() < request.getMaxReqCount()){
                        request.setCurReqCount(request.getCurReqCount()+1);
                        queue.push(request);
                    }
                    LOGGER.info("响应为空,重新加入队列:{}",request.getRealUrl());
                    continue;
                }
                
                
        }
    }

}
