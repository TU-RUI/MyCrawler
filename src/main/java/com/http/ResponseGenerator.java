package com.http;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.util.EntityUtils;



import com.entity.BodyType;
import com.entity.Request;
import com.entity.Response;

public class ResponseGenerator {

    public static Response build(HttpResponse httpResponse, Request request) {
        Response response = new Response();
        response.setRequest(request);
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
        response.setUrl(realUrl.substring(0, realUrl.length() - 1));
        // TODO
        if (httpResponse.getFirstHeader("Content-Type").getValue().contains("image")) {
            response.setBodyType(BodyType.BINARY);
            try {
                byte[] data = EntityUtils.toByteArray(httpResponse.getEntity());
                response.setData(data);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        } else {
            response.setBodyType(BodyType.TEXT);
            response.setCharset("UTF-8");
            try {
                String body = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
                response.setContent(body);
            } catch (ParseException | IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return null;
            }
        }

        return response;
    }
}
