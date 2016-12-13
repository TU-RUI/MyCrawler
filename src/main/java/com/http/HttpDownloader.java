package com.http;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import com.crawler.Crawler;
import com.entity.Request;
import com.entity.Response;

public class HttpDownloader {
    
    private HttpClient httpClient;
    private Request currentRequest;
    private HttpResponse httpResponse;
    private HttpContext httpContext = new BasicHttpContext();
    
    public HttpDownloader(){
       httpClient = HttpClientFactory.getHttpClient(10000,Crawler.getInstance().getCookieStore());
    }
    
    public Response process(Request request) throws Exception{
        currentRequest = request;
        httpResponse = httpClient.execute(HttpRequestGenerator.build(request).build(), httpContext);
        return ResponseGenerator.build(httpResponse,currentRequest);
    }
    
}
