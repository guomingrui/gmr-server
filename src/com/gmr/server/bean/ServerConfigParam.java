package com.gmr.server.bean;

/**
 * 配置文件，先写死
 * 
 * @author gmr
 *
 */
public class ServerConfigParam {
	// 隐藏目录名
	//暂时没用上，先写死
	public static final String HIDDEN_ROOT = "/WEB-INF/";
	// 文档根目录
	//暂时没用上，先写死
	public static final String DOCUMENT_ROOT = "/webapp";
	// 默认文件
	//暂时没用上，先写死
	public static final String DEFAULT_FILE = "index.html";
	// 监听端口
	public static final int LISTEN_PORT = 80;
	// 超时时间
	public static final int KEEP_ALIVE_TIME = 80;
	// 初始化客户端数量
	public static final int INIT_CLIENT_NUM = 120;
	// 最大客户端数量
	public static final int MAX_CLIENT_NUM  = 250;

}
