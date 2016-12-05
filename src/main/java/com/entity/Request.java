package com.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;

import cn.wanghaomiao.seimi.http.HttpMethod;

public class Request implements Serializable{;
    
	private static final long serialVersionUID = 875298819695944492L;
	//请求的url
    private String url;
    //所用的方法
    private HttpMethod httpMethod;
    //请求需要的参数
    private Map<String,String> params;
    //请求提交的表单
    private List<NameValuePair> pair;
    //该请求最大重试次数
    private int maxReqCount;
    //该请求当前已重试次数
    private int curReqCount;
    
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
