package com.gmr.server.bean;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import com.gmr.server.enumeration.GmrEnumeration;

/**
 * Servlet配置类
 * 
 * @author gmr
 *
 */
public class GmrServletConfig implements ServletConfig {
	private Map<String, String> initParamterMap = new HashMap<String, String>();
	private ServletContext servletContext;
	private String servletName;

	public GmrServletConfig(ServletContext servletContext, String serveletName) {
		this.servletContext = servletContext;
		this.servletName = serveletName;
	}

	public void addInitParamter(String paramKey, String paramValue) {
		this.initParamterMap.put(paramKey, paramValue);
	}

	@Override
	public String getInitParameter(String paramName) {
		return initParamterMap.get(paramName);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return new GmrEnumeration<String>(this.initParamterMap.keySet());
	}

	@Override
	public ServletContext getServletContext() {
		return this.servletContext;
	}

	@Override
	public String getServletName() {
		return this.servletName;
	}

}
