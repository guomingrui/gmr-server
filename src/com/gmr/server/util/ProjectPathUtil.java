package com.gmr.server.util;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServlet;

import com.gmr.server.bean.FinalGmrServletContext;
import com.gmr.server.bean.GmrServletConfig;
import com.gmr.server.servlet.DefaultServlet;
import com.gmr.server.servlet.JspServlet;

public class ProjectPathUtil {
	public static String getProjRootPath() {
		String projRootPath = ClassLoader.getSystemClassLoader().getResource("")
				.getPath();
		if (projRootPath.startsWith("/") && projRootPath.contains(":")) {
			projRootPath = projRootPath.substring(1);
		}
		if (projRootPath.endsWith("/")) {
			projRootPath = projRootPath.substring(0,
					projRootPath.lastIndexOf("/"));
		}
		int index = projRootPath.lastIndexOf("/");
		projRootPath = projRootPath.substring(0, index+1)
				.concat("webapps/");
		File file = new File(projRootPath);
		File[] projects = file.listFiles();
		if (projects != null) {
			for (File project : projects) {
				if (project.isDirectory()) {
					return projRootPath.concat(project.getName()).concat(
							"/WEB-INF/");
				}
			}
		}
		return "";
	}

	private static URL getFileURL(String path) {
		try {
			return new File(path).toURI().toURL();
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public static void initClass() {
		String projRootPath = getProjRootPath();
		if (projRootPath == null) {
			return;
		}
		ClassUtil.addJars(projRootPath + "lib/");
		ClassUtil.initClass(getFileURL(projRootPath + "classes"));
		// 框架servlet,写死在这里先,先不写成xml的形式
		try {
			ServletContext servletContext = new FinalGmrServletContext();
			GmrServletConfig gmrServletConfig = new GmrServletConfig(
					servletContext, "dispatcherServlet");
			Class<?> servletClass = Class.forName(
					"gmr.all.framework.DispatcherServlet", true, Thread
							.currentThread().getContextClassLoader());
			// 初始化默认的servlet
			JspServlet jspServlet = new JspServlet();
			DefaultServlet defaultServlet = new DefaultServlet();
			servletContext.addServlet("jsp", jspServlet);
			jspServlet.init(gmrServletConfig);
			servletContext.addServlet("default", defaultServlet);
			defaultServlet.init(gmrServletConfig);

			HttpServlet servlet;
			servlet = (HttpServlet) servletClass.newInstance();
			servletContext.addServlet("dispatchServlet", servlet);
			servlet.init(gmrServletConfig);
			servletContext.getServletRegistration("dispatchServlet")
					.addMapping("/*");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
