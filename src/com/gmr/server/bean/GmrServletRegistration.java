package com.gmr.server.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;

/**
 * @author gmr
 *
 */
public class GmrServletRegistration implements ServletRegistration {
	private final Map<String, String> InitParameterMap = new HashMap<String, String>();
	private final Set<String> mapping = new HashSet<String>();
	private HttpServlet servlet;
	private String servletName;

	public GmrServletRegistration(HttpServlet servlet, String servletName) {
		this.servlet = servlet;
		this.servletName = servletName;
	}

	public GmrServletRegistration(HttpServlet servlet) {
		this.servlet = servlet;
		this.servletName = servlet.getClass().getSimpleName();
	}

	public HttpServlet getServlet() {
		return servlet;
	}

	@Override
	public String getClassName() {
		return this.servlet.getClass().getName();
	}

	@Override
	public String getInitParameter(String arg0) {
		return null;
	}

	@Override
	public Map<String, String> getInitParameters() {
		return this.InitParameterMap;
	}

	@Override
	public String getName() {
		return this.servletName;
	}

	@Override
	public boolean setInitParameter(String key, String value) {
		return false;
	}

	@Override
	public Set<String> setInitParameters(Map<String, String> map) {
		this.InitParameterMap.putAll(map);
		return this.InitParameterMap.keySet();
	}

	@Override
	public Set<String> addMapping(String... mappingPaths) {
		for (String mappingPath : mappingPaths) {
			this.mapping.add(mappingPath);
			FinalGmrServletContext.addMapping(mappingPath, this.servlet);
		}
		
		return this.mapping;
	}

	@Override
	public Collection<String> getMappings() {
		return this.mapping;
	}

	@Override
	public String getRunAsRole() {
		// TODO Auto-generated method stub
		return null;
	}

}
