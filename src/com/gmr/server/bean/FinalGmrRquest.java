package com.gmr.server.bean;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpSession;

import com.gmr.server.enumeration.EmptyEnumeration;
import com.gmr.server.enumeration.GmrEnumeration;

public final class FinalGmrRquest extends GmrRequest {

	private Map<String, Object> requestAttributeMap = new HashMap<String, Object>();
	private Map<String, List<String>> headersArrayMap = new HashMap<String, List<String>>();

	@Override
	public Object getAttribute(String paramString) {
		return requestAttributeMap.get(paramString);
	}

	@Override
	public void removeAttribute(String paramString) {
		requestAttributeMap.remove(paramString);
	}

	@Override
	public void setAttribute(String paramString, Object paramObject) {
		requestAttributeMap.put(paramString, paramObject);
	}

	@Override
	public String getContextPath() {
		return "";
	}

	@Override
	public String getParameter(String paramString) {
		String[] paramters = super.paramtersMap.get(paramString);
		if (paramters != null) {
			return paramters[0];
		}
		return null;
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return super.paramtersMap;
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return new GmrRequestDispatcher(path);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return new GmrEnumeration<String>(super.paramtersMap.keySet());
	}

	@Override
	public String[] getParameterValues(String paramString) {
		return super.paramtersMap.get(paramString);
	}

	@Override
	public String getMethod() {
		return super.method;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		return super.inputStream;
	}

	@Override
	public String getPathInfo() {
		return super.pathInf;
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return new GmrEnumeration<String>(super.headersMap.keySet());
	}

	@Override
	public String getHeader(String paramString) {
		return super.headersMap.get(paramString);
	}

	@Override
	public Enumeration<String> getHeaders(String paramString) {
		List<String> headers = this.headersArrayMap.get(paramString);
		if (headers == null) {
			String header = super.headersMap.get(paramString);
			if (header == null) {
				return new EmptyEnumeration<String>();
			}
			headers = Arrays.asList(header.split("; *"));
			this.headersArrayMap.put(paramString, headers);
		}
		return new GmrEnumeration<String>(headers);
	}

	@Override
	public HttpSession getSession() {
		return super.session;
	}

}
