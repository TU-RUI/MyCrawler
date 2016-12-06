package com.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.crawler.Crawler;
import com.entity.Request;
import com.entity.Response;

public class HttpDownLoader {
    
    private HttpClient httpClient;
    private Request currentRequest;
    private HttpResponse httpResponse;
    private HttpContext httpContext = new BasicHttpContext();
    
    public HttpDownLoader(){
       httpClient = HttpClientFactory.getHttpClient(10000,Crawler.getInstance().getCookieStore());
    }
    
    public Response precess(Request request) throws Exception{
        currentRequest = request;
        httpResponse = httpClient.execute(RequestGenerator.build(request).build(), httpContext);
        return ResponseGenerator.build(httpResponse,currentRequest);
    }
    
}
