package com.gmr.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.gmr.server.classLoader.GmrClassLoader;
import com.gmr.server.util.ConfigUtil;
import com.gmr.server.util.ProjectPathUtil;

public class MainServer {
	public static ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
			ConfigUtil.getInitClientNum(), ConfigUtil.getMaxClientNum(),
			ConfigUtil.getKeepAliveTime(), TimeUnit.MINUTES,
			new LinkedBlockingDeque<Runnable>(),
			new ThreadPoolExecutor.DiscardOldestPolicy());

	public static void main(String[] args) throws Exception {
		ServerSocket server = null;
		try {
			server = new ServerSocket(ConfigUtil.getListenPort());
			Thread.currentThread().setContextClassLoader(
					new GmrClassLoader(ProjectPathUtil.getProjRootPath()
							+ "/classes"));
			ProjectPathUtil.initClass();
			// 开启session检查线程
			new SessionCheckThread().start();
			while (true) {
				Socket socket = server.accept();
				threadPoolExecutor.execute(new TaskThread(socket));
			}
		} catch (Exception e) {
			e.printStackTrace();
//			throw new RuntimeException(e);
		} finally {
			if (server != null) {
				try {
					server.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
