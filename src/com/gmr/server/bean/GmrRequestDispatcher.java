package com.gmr.server.bean;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;

public class GmrRequestDispatcher implements RequestDispatcher {
	private String path;

	public GmrRequestDispatcher(String path) {
		this.path = path;
	}

	@Override
	public void forward(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {
		// 先只实现这个
		// 写先死
		// 不跳转到jsp
		if (!path.startsWith("/")) {
			path = "/" + path;
		}
		((FinalGmrRquest) request).setPathInf(path);
		if (path.contains("WEB-INF")) {
			HttpServlet servlet = FinalGmrServletContext
					.getServletByName("default");
			servlet.service(request, response);
		} else {
			HttpServlet servlet = FinalGmrServletContext.getHttpServlet(path);
			if (servlet != null) {
				servlet.service(request, response);
			} else {
				((HttpServletResponse) response).sendError(404);
			}
		}

	}

	@Override
	public void include(ServletRequest request, ServletResponse response)
			throws ServletException, IOException {

	}

}
