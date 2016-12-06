package com.crawler;

import com.entity.Request;
import com.entity.Response;
import com.handler.Handler;
import com.http.HttpDownLoader;
import com.queues.RedisQueue;

public class CrawlerProcess implements Runnable{
    private Crawler crawler;
    private Handler handler;
    private RedisQueue queue;
    
    public CrawlerProcess(Handler handler , Crawler crawler){
        this.crawler = crawler;
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
                if(request == null){
                    continue;
                }
                if(request.getCurReqCount() >= request.getMaxReqCount()){
                    continue;
                }
                handler.before(request);
                HttpDownLoader downLoader = new HttpDownLoader();
                Response response = downLoader.precess(request);
                handler.after(response);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }
    }

}
