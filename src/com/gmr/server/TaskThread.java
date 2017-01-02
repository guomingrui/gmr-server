package com.gmr.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.gmr.server.bean.FinalGmrResopnse;
import com.gmr.server.bean.FinalGmrServletContext;
import com.gmr.server.bean.GmrServletOutputStream;
import com.gmr.server.util.RequestResolver;

public class TaskThread extends Thread {
	private Socket socket;

	public TaskThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			InputStream in = socket.getInputStream();
			OutputStream out = socket.getOutputStream();
			HttpServletRequest request = RequestResolver.resolve(in);
			if (request == null) {
				return;
			}
			FinalGmrResopnse response = new FinalGmrResopnse();
			response.setSessionId(request.getSession().getId());
			response.setOutputStream(new GmrServletOutputStream(out));
			response.setHostHeader(request.getHeader("Host"));
			String pathInfo = request.getPathInfo();
			if (pathInfo.toLowerCase().startsWith("/web-inf")
					|| pathInfo.contains("..")) {
				response.sendError(404);
			} else {
				HttpServlet servlet = FinalGmrServletContext
						.getHttpServlet(request.getPathInfo());
				if (servlet == null) {
					response.sendError(404);
				} else {
					servlet.service(request, response);
				}
			}
			response.getWriter().flush();
			response.setFinised(true);
			response.getWriter().flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (socket != null) {
				try {
					socket.close();
				} catch (Exception e) {
				}
			}
		}
	}

}
