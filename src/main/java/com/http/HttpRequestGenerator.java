package com.http;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.RequestBuilder;
import com.crawler.Crawler;
import com.entity.Request;

public class HttpRequestGenerator {

    //
    public static RequestBuilder build(Request request) {
        RequestBuilder requestBuilder = null;
        // TODO
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
            requestBuilder = RequestBuilder.post().setUri(request.getUrl());
            if(null != request.getPair() && !request.getPair().isEmpty()){
                HttpEntity entity;
                try {
                    entity = new UrlEncodedFormEntity(request.getPair(),"UTF-8");
                    requestBuilder.setEntity(entity);
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } else {
            requestBuilder = RequestBuilder.get().setUri(request.getUrl());
        }
        RequestConfig config = RequestConfig.custom()
                .setCircularRedirectsAllowed(true).build();

        if (request.getParams() != null) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                requestBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        requestBuilder.setConfig(config).setHeader("User-Agent",Crawler.getInstance().getUserAgent());
        requestBuilder
                .setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        requestBuilder.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        requestBuilder.addHeader("Referer", request.getReferer());
        return requestBuilder;
    }
}
