package com.crawler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.handler.Handler;

public class Main {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Crawler crawler = Crawler.getInstance();
        ExecutorService threadPool = Executors.newFixedThreadPool(4);
        for(int i = 0;i<4;i++){
            threadPool.execute(new CrawlerProcess(new Handler(), crawler));
        }
    }

}
