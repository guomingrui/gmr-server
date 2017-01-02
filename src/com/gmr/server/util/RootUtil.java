package com.gmr.server.util;

import java.net.URL;

public class RootUtil {
	private static String staticRoot = null;
	private static String staticClassRoot = null;
	private static String staticLibRoot = null;
	private static boolean isWindows;

	static {
		if (System.getProperties().getProperty("os.name").toLowerCase()
				.contains("window")) {
			isWindows = true;
		} else {
			isWindows = false;
		}
	}

	private static URL getRootUrl() {
		return Thread.currentThread().getContextClassLoader().getResource("");
	}

	public static String getClassRootPath() {
		if (staticClassRoot == null) {
			String rootPath = getRootPath();
			if (rootPath == null) {
				return null;
			}
			staticClassRoot = rootPath + "/WEB-INF/classes";
		}
		return staticClassRoot;
	}

	public static String getLibRootPath() {
		if (staticLibRoot == null) {
			String rootPath = getRootPath();
			if (rootPath == null) {
				return null;
			}
			staticClassRoot = rootPath + "/WEB-INF/lib";
		}
		return staticLibRoot;
	}

	public static String getRootPath() {
		if (staticRoot == null) {
			URL url = getRootUrl();
			if (url == null) {
				return null;
			} else {
				String rootPath = url.getPath();
				rootPath = url.getPath();
				if (isWindows && rootPath.startsWith("/")) {
					rootPath = rootPath.substring(1);
				}
				if (rootPath.endsWith("/")) {
					rootPath = rootPath.substring(0, rootPath.length() - 1);
				}
				rootPath = rootPath.substring(0,
						rootPath.lastIndexOf("/WEB-INF/classes"));
				staticRoot = rootPath;
			}
		}
		return staticRoot;
	}
}
