package com.gmr.server.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

import com.gmr.server.util.RootUtil;

public class DefaultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String rootPath = RootUtil.getRootPath();
		if (rootPath == null) {
			response.sendError(404);
			return;
		}
		File file = new File(rootPath + request.getPathInfo());
		if (!file.exists() || file.isDirectory()) {
			// 抛出404
			response.sendError(404);
			return;
		}
		String fileNameSuffix = file.getName().toLowerCase();
		fileNameSuffix = fileNameSuffix.substring(fileNameSuffix
				.lastIndexOf("."));
		if (fileNameSuffix.equals(".js")) {
			response.setContentType("application/x-javascript");
		} else if (fileNameSuffix.equals(".gif")
				|| fileNameSuffix.equals(".png")
				|| fileNameSuffix.equals(".jpeg")
				|| fileNameSuffix.equals(".jpg")
				|| fileNameSuffix.equals(".BMP")) {
			response.setContentType("image/" + fileNameSuffix.substring(1));
		} else if (fileNameSuffix.equals(".css")) {
			response.setContentType("text/css");
		} else if (fileNameSuffix.equals(".html")
				|| fileNameSuffix.equals(".htm")) {
			response.setContentType("text/html");
		} else if (fileNameSuffix.equals(".jsp")
				|| fileNameSuffix.equals(".txt")) {
			response.setContentType("text/json");
		} else {
			response.setContentType("text/plain");
		}
		response.setContentLength((int) file.length());
		FileInputStream in = new FileInputStream(file);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
		try {
			response.getWriter().flush();
			IOUtils.copy(bufferedInputStream , response.getOutputStream());
		} catch (Exception e) {
			// 抛出404
			e.printStackTrace();
			response.sendError(404);
			return;
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
				}
			}
		}

	}
}
