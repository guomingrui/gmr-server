package com.gmr.server.bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;

public class GmrRequest extends GmrRequestImpl {
	// 请求头部信息
	protected Map<String, String> headersMap = new HashMap<String, String>();
	// 请求参数信息
	protected Map<String, String[]> paramtersMap = new HashMap<String, String[]>();
	protected ServletInputStream inputStream;
	// 请求方法
	protected String method;
	// url路径
	protected String pathInf;
	
	protected HttpSession session;

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setPathInf(String pathInf) {
		this.pathInf = pathInf;
	}

	public void setHeadersMap(Map<String, String> headersMap) {
		this.headersMap.putAll(headersMap);
	}

	public void setParamtersMap(Map<String, String[]> paramtersMap) {
		this.paramtersMap.putAll(paramtersMap);
	}

	public void setInputStream(ServletInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
}
