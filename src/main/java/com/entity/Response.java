package com.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.lang3.builder.ToStringBuilder;


public class Response implements Serializable {

    private static final long serialVersionUID = 2792228141564580864L;
    //响应类型
    private BodyType bodyType;
    //原始地址
    private String url;
    
    private int StatusCode;
    //原始请求
    private Request request;
    //编码类型
    private String charset;
    //字节流数据
    private byte[] data;
    //文本数据
    private String content;
    //真实请求地址(包括Query String)
    private String realUrl;

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void saveTo(File targetFile) throws Exception {

        try (FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                FileChannel fo = fileOutputStream.getChannel();) {
            File pf = targetFile.getParentFile();
            if (!pf.exists()) {
                pf.mkdirs();
            }
            if (BodyType.TEXT.equals(bodyType)) {
                fo.write(ByteBuffer.wrap(getContent().getBytes()));
            } else {
                fo.write(ByteBuffer.wrap(getData()));
            }
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }
}
