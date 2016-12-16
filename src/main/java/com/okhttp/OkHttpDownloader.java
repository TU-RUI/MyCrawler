package com.okhttp;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import cn.wanghaomiao.seimi.utils.StrFormatUtil;
import cn.wanghaomiao.xpath.exception.XpathSyntaxErrorException;
import cn.wanghaomiao.xpath.model.JXDocument;

import com.entity.BodyType;
import com.entity.Request;
import com.entity.Response;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;

public class OkHttpDownloader {
    
    private Request request;
    private Request currentRequest;
    private okhttp3.Request.Builder currentRequestBuilder;
    private OkHttpClient okHttpClient;
    private okhttp3.Response lastResponse;
    
    public Response process(Request request) throws Exception {
        if(request == null){
            return null;
        }
        currentRequest = request;
        OkHttpClient.Builder clientbuilder = OkHttpClientManager.getBuilder();
        clientbuilder.cookieJar(CookieManager.getInstance());
        clientbuilder.connectTimeout(10, TimeUnit.SECONDS);
        okHttpClient = clientbuilder.build();
        okhttp3.Request.Builder requestBuilder = OkHttpRequestGenerator.build(request);
        okhttp3.Response response = okHttpClient.newCall(requestBuilder.build()).execute();
        if(!response.isSuccessful()){
            return null;
        }
        if(response.isRedirect()){
             return process(buildRedirectRequest(response,request));
        }
        return responseBuild(response,currentRequest);
    }
    
    /**
     * 
     * @Description 根据okhttp响应生成自定义响应
     * @param response okhttp响应
     * @param request 该响应对应的请求
     * @return 自定义响应
     */
    public Response responseBuild(okhttp3.Response resp,Request request){
        Response response = new Response();
        response.setRequest(request);
        response.setUrl(request.getUrl());
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
        response.setStatusCode(resp.code());
        
        ResponseBody okResponseBody = resp.body();
        if (okResponseBody!=null){
            String type = okResponseBody.contentType().type().toLowerCase();
            String subtype = okResponseBody.contentType().subtype().toLowerCase();
            if (type.contains("text")||type.contains("json")||type.contains("ajax")||subtype.contains("json")
                    ||subtype.contains("ajax")){
                response.setBodyType(BodyType.TEXT);
                try {
                    byte[] data = okResponseBody.bytes();
                    String utfContent = new String(data,"UTF-8");
                    String charsetFinal = renderRealCharset(utfContent);
                    if (charsetFinal.equals("UTF-8")){
                        response.setContent(utfContent);
                    }else {
                        response.setContent(new String(data,charsetFinal));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else {
                response.setBodyType(BodyType.BINARY);
                try {
                    response.setData(okResponseBody.bytes());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return response;
    }
    
    /**
     * 
     * @Description 302跳转
     * @param response
     * @param Request
     * @return
     */
    private Request buildRedirectRequest(okhttp3.Response response,Request Request){
        String url = response.header("Location");
        if(!StringUtils.isBlank(url)){
            request.setUrl(url);
            return request;
        }
        return null; 
    }
    
    
    
    /**
     * 
     * @Description 检测字符编码
     * @param content
     * @return
     * @throws XpathSyntaxErrorException
     */
    private String renderRealCharset(String content) throws XpathSyntaxErrorException {
        String charset;
        JXDocument doc = new JXDocument(content);
        charset = StrFormatUtil.getFirstEmStr(doc.sel("//meta[@charset]/@charset"),"").trim();
        if (StringUtils.isBlank(charset)){
            charset = StrFormatUtil.getFirstEmStr(doc.sel("//meta[@http-equiv='charset']/@content"),"").trim();
        }
        if (StringUtils.isBlank(charset)){
            String ct = StringUtils.join(doc.sel("//meta[@http-equiv='Content-Type']/@content|//meta[@http-equiv='content-type']/@content"),";").trim();
            charset = StrFormatUtil.parseCharset(ct.toLowerCase());
        }
        return StringUtils.isNotBlank(charset)?charset:"UTF-8";
    }
    
    
}
