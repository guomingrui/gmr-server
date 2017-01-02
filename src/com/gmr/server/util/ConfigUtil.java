package com.gmr.server.util;

import com.gmr.server.bean.ServerConfigParam;

public class ConfigUtil {

	public static String getHiddenRoot() {
		return ServerConfigParam.HIDDEN_ROOT;
	}

	public static String getDocumentRoot() {
		return ServerConfigParam.DOCUMENT_ROOT;
	}

	public static String getDefaultFile() {
		return ServerConfigParam.DEFAULT_FILE;
	}

	public static int getListenPort() {
		return ServerConfigParam.LISTEN_PORT;
	}

	public static int getInitClientNum() {
		return ServerConfigParam.INIT_CLIENT_NUM;
	}

	public static int getMaxClientNum() {
		return ServerConfigParam.MAX_CLIENT_NUM;
	}

	public static int getKeepAliveTime() {
		return ServerConfigParam.KEEP_ALIVE_TIME;
	}
}
