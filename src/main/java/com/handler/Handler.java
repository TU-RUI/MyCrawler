package com.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.entity.Request;
import com.entity.Response;

public class Handler {
    private static Logger LOGGER = LoggerFactory.getLogger(Handler.class);
    //请求预处理
	//判断去重
    public void before(Request request){
        
    }
    
    //响应预处理,判断响应码等等
    public void after(Response response){
        
    }
}
