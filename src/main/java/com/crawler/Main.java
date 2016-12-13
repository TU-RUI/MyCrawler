package com.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.handler.Handler;
import com.queues.RedisQueue;

public class Main {
    private static Logger LOGGER = LoggerFactory.getLogger(Main.class);
    public static void main1(String[] args) {
        // TODO Auto-generated method stub
        RedisQueue.connect();
        Crawler crawler = Crawler.getInstance();
        
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        for(int i = 0;i<4;i++){
            threadPool.execute(new CrawlerProcess(new Handler(), crawler));
        }
    }

}
