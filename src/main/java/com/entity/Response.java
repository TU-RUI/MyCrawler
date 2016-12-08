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
    private String url;
    private Request request;
    private String charset;
    private byte[] data;
    private String content;
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
}
