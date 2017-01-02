package com.gmr.server.bean;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.ServletRegistration;
import javax.servlet.ServletRegistration.Dynamic;
import javax.servlet.http.HttpServlet;

public class FinalGmrServletContext extends GmrServletContext {
	private static final Map<String, ServletRegistration> servleRegiMap = new HashMap<String, ServletRegistration>();
	private static final Map<String, HttpServlet> CONTAIN_MAPPING_MAP = new HashMap<String, HttpServlet>();
	private static final Map<String, HttpServlet> WILDCARD_MAPPING_MAP = new HashMap<String, HttpServlet>();

	public static HttpServlet getServletByName(String name) {
		ServletRegistration registration = servleRegiMap.get(name);
		if (registration != null) {
			return ((GmrServletRegistration) registration).getServlet();
		}
		return null;

	}

	public static HttpServlet getHttpServlet(String pathInfo) {
		if (CONTAIN_MAPPING_MAP.containsKey(pathInfo)) {
			return CONTAIN_MAPPING_MAP.get(pathInfo);
		}
		if(pathInfo.startsWith("/static/")){
			return WILDCARD_MAPPING_MAP.get("/static/.*");
		}
		if(pathInfo.startsWith("/WEB-INF/")){
			return WILDCARD_MAPPING_MAP.get("/WEB-INF/view/.*");
		}
		for (String key : CONTAIN_MAPPING_MAP.keySet()) {
			if (key.startsWith(pathInfo)) {
				return CONTAIN_MAPPING_MAP.get(key);
			}
		}
		for (String key : WILDCARD_MAPPING_MAP.keySet()) {
			if (pathInfo.matches(key)) {
				return WILDCARD_MAPPING_MAP.get(key);
			}
		}
		return null;
	}

	public FinalGmrServletContext() {
	}

	public static void addMapping(String path, HttpServlet servlet) {
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}
		if (path.contains("*")) {
			path = path.replace("*", ".*");
			WILDCARD_MAPPING_MAP.put(path, servlet);
		} else {
			CONTAIN_MAPPING_MAP.put(path, servlet);
		}
	}

	@Override
	public Dynamic addServlet(String name, Servlet servlet) {
		GmrServletRegistration servletRegistration = new GmrServletRegistration(
				(HttpServlet) servlet, name);
		servleRegiMap.put(name, (ServletRegistration) servletRegistration);
		return null;
	}

	@Override
	public ServletRegistration getServletRegistration(String servletName) {
		return servleRegiMap.get(servletName);
	}

	@Override
	public Map<String, ? extends ServletRegistration> getServletRegistrations() {
		return servleRegiMap;
	}

}
