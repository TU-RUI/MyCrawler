package com.okhttp;

import java.util.Map;
import java.util.Set;

import okhttp3.FormBody;

import com.crawler.Crawler;
import com.entity.Request;
import com.http.HttpMethod;

public class OkHttpRequestGenerator {
    public static okhttp3.Request.Builder build(Request request) {
        okhttp3.Request.Builder requestBuilder = new okhttp3.Request.Builder();
        requestBuilder.url(request.getUrl());
        requestBuilder.header("User-Agent", Crawler.getInstance().getUserAgent())
                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
                .header("Referer",request.getReferer())
                .header("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        if (HttpMethod.POST.equals(request.getHttpMethod())) {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            if (request.getPair() != null) {
                for (Map.Entry<String, String> entry : request.getPair().entrySet()) {
                    formBodyBuilder.add(entry.getKey(), entry.getValue());
                }
            }
            requestBuilder.post(formBodyBuilder.build());
        } else {

            StringBuilder realUrl = new StringBuilder(request.getUrl());
            if (request.getUrl().contains("?")) {
                realUrl.append("&");
            } else {
                realUrl.append("?");
            }
            if (null != request.getParams() && !request.getParams().isEmpty()) {
                Map<String, String> map = request.getParams();
                Set<String> set = map.keySet();
                for (String s : set) {
                    realUrl.append(s + "=" + map.get(s));
                    realUrl.append("&");
                }
            }
            requestBuilder.url(realUrl.substring(0, realUrl.length() - 1));
            requestBuilder.get();
        }
        return requestBuilder;
    }
}
