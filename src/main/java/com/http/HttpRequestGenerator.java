package com.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.crawler.Crawler;
import com.entity.Request;

public class HttpRequestGenerator {
	private static Logger logger = LoggerFactory.getLogger(HttpRequestGenerator.class);
    //
    public static RequestBuilder build(Request request) {
        RequestBuilder requestBuilder = null;
        // TODO
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
            requestBuilder = RequestBuilder.post().setUri(request.getUrl());
            if (null != request.getPair() && !request.getPair().isEmpty()) {
                HttpEntity entity;
                try {
                    List<NameValuePair> pair = new ArrayList<NameValuePair>();
                    Map<String, String> map = request.getPair();
                    if (null != map && !map.isEmpty()) {
                        Set<String> set = map.keySet();
                        for (String key : set) {
                            pair.add(new BasicNameValuePair(key, map.get(key)));
                        }
                        entity = new UrlEncodedFormEntity(pair, "UTF-8");
                        requestBuilder.setEntity(entity);
                    }
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    logger.error(e.getMessage());
                }
            }
        } else {
            requestBuilder = RequestBuilder.get().setUri(request.getUrl());
        }
        RequestConfig config = RequestConfig.custom().setCircularRedirectsAllowed(true).build();

        if (request.getParams() != null) {
            for (Map.Entry<String, String> entry : request.getParams().entrySet()) {
                requestBuilder.addParameter(entry.getKey(), entry.getValue());
            }
        }
        requestBuilder.setConfig(config).setHeader("User-Agent", Crawler.getInstance().getUserAgent());
        requestBuilder
                .setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        requestBuilder.setHeader("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        requestBuilder.addHeader("Referer", request.getReferer());
        return requestBuilder;
    }
}
