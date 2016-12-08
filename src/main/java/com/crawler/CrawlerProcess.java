package com.crawler;

import com.entity.Request;
import com.entity.Response;
import com.handler.Handler;
import com.http.HttpDownLoader;
import com.queues.RedisQueue;
import com.queues.RequestProducer;

public class CrawlerProcess implements Runnable{
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
            try {
                request = queue.bPop();
                handler.before(request);
                if(request == null){
                    continue;
                }
                if(request.getCurReqCount() >= request.getMaxReqCount()){
                    continue;
                }
                HttpDownLoader downLoader = new HttpDownLoader();
                Response response = downLoader.precess(request);
                if(response == null){
                    if(request.getCurReqCount() < request.getMaxReqCount()){
                        request.setCurReqCount(request.getCurReqCount()+1);
                        queue.push(request);
                    }
                }
                
                
                handler.after(response);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
