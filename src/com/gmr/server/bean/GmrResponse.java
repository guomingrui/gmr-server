package com.gmr.server.bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;

public class GmrResponse extends GmrResponseImpl {
	// 请求头部信息
	protected Map<String, String> headersMap = new HashMap<String, String>();
	// 输出流
	protected ServletOutputStream outputStream;
	protected int contentLength = -1;
	
	protected String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getContentLength() {
		return this.contentLength;
	}

	public void setHeadersMap(Map<String, String> headersMap) {
		this.headersMap.putAll(headersMap);
	}

	public void setOutputStream(ServletOutputStream outputStream) {
		this.outputStream = outputStream;
	}

}
