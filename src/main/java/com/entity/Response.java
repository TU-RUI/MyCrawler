package com.entity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

import cn.wanghaomiao.seimi.struct.BodyType;

public class Response implements Serializable {

	
	private static final long serialVersionUID = 2792228141564580864L;
	private BodyType bodyType;
    private Request request;
    private String charset;
    private String referer;
    private byte[] data;
    private String content;
    /**
     * 这个主要用于存储上游传递的一些自定义数据
     */
    private Map<String, String> meta;
    private String url;
    private Map<String, String> params;
    /**
     * 网页内容真实源地址
     */
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

    public Map<String, String> getMeta() {
        return meta;
    }

    public void setMeta(Map<String, String> meta) {
        this.meta = meta;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public void setRealUrl(String realUrl) {
        this.realUrl = realUrl;
    }

    public void saveTo(File targetFile) throws Exception {

        try (
                FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
                FileChannel fo = fileOutputStream.getChannel();
        ) {
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
}
