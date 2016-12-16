package com.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.http.NameValuePair;

import com.http.HttpMethod;

public class Request implements Serializable {
	;

	private static final long serialVersionUID = 875298819695944492L;
	// 请求的url
	private String url;
	// referer
	private String referer;
	// 所用的方法
	private HttpMethod httpMethod;
	// 请求需要的参数
	private Map<String, String> params;
	// 请求提交的表单
	private Map<String, String> pair;
	// 该请求最大重试次数
	private int maxReqCount = 3;
	// 该请求当前已重试次数
	private int curReqCount = 0;
	// URL类型
	private URLType urlType;
	// 真实url.包含queryString
	private String realUrl;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public HttpMethod getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(HttpMethod httpMethod) {
		this.httpMethod = httpMethod;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, String> getPair() {
		return pair;
	}

	public void setPair(Map<String, String> pair) {
		this.pair = pair;
	}

	public int getMaxReqCount() {
		return maxReqCount;
	}

	public void setMaxReqCount(int maxReqCount) {
		this.maxReqCount = maxReqCount;
	}

	public int getCurReqCount() {
		return curReqCount;
	}

	public void setCurReqCount(int curReqCount) {
		this.curReqCount = curReqCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public URLType getUrlType() {
		return urlType;
	}

	public void setUrlType(URLType urlType) {
		this.urlType = urlType;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public String getRealUrl() {
		if (null == url || url.equals("")) {
			return "";
		}
		StringBuilder sb = new StringBuilder(url);
		if (url.contains("?")) {
			sb.append("&");
		} else {
			sb.append("?");
		}
		if (null != params && !params.isEmpty()) {
			Map<String, String> map = params;
			Set<String> set = map.keySet();
			for (String s : set) {
				sb.append(s + "=" + map.get(s));
				sb.append("&");
			}
		}
		realUrl = sb.substring(0, sb.length()-1).toString();
		return realUrl;
	}

	public void setRealUrl(String realUrl) {
		this.realUrl = realUrl;
	}
}
