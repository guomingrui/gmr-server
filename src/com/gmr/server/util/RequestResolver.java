package com.gmr.server.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import com.gmr.server.bean.FinalGmrRquest;
import com.gmr.server.bean.GmrServletInputStream;

public class RequestResolver {
	private static final String GET = "GET";
	private static final String POST = "POST";

	/**
	 * 解析获取url上的参数
	 * 
	 * @param paramString
	 * @return
	 */
	private static Map<String, String[]> resolveUrlParam(String paramString) {

		try {
			// 对url进行解码
			paramString = URLDecoder.decode(paramString, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return null;
		}
		String[] params = paramString.split("&");
		Map<String, List<String>> paramsListMap = new HashMap<String, List<String>>();
		Map<String, String[]> paramsMap = new HashMap<String, String[]>();
		for (String param : params) {
			int spilIndex = param.indexOf("=");
			if (spilIndex == -1) {
				continue;

			}
			String paramName = param.substring(0, spilIndex);
			String paramValue = param.substring(spilIndex + 1);
			if (!paramsListMap.containsKey(paramName)) {
				paramsListMap.put(paramName, new ArrayList<String>());
			}
			paramsListMap.get(paramName).add(paramValue);
		}
		for (Entry<String, List<String>> entry : paramsListMap.entrySet()) {
			List<String> list = entry.getValue();
			paramsMap
					.put(entry.getKey(), list.toArray(new String[list.size()]));
		}
		return paramsMap;
	}

	/**
	 * 这个应该放在线程内部解析 解析请求体
	 * 
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public static HttpServletRequest resolve(InputStream in) throws IOException {
		FinalGmrRquest request = new FinalGmrRquest();
		Map<String, String[]> paramsMap = null;
		BufferedReader bufferedReader = new BufferedReader(
				new InputStreamReader(in));
		String data = bufferedReader.readLine();
		// 解析方法类型，路径url以及支持的协议号
		if (data == null) {
			return null;
		}
		String[] baseUrlStrs = data.split(" ");
		String requestMethod = baseUrlStrs[0].trim();
		String urlWithParam = baseUrlStrs[1].trim();
		String httpVersion = baseUrlStrs[2].trim();

		// 检查版本
		if (!httpVersion.equals("HTTP/1.1")) {
			// 不支持HTTP版本
			return null;
		}

		// 检查并设置方法
		if (!requestMethod.equals(GET) && !requestMethod.equals(POST)) {
			// 不支持方法
			return null;
		}
		request.setMethod(requestMethod);

		int spilIndex = urlWithParam.indexOf("?");
		String url = urlWithParam;
		// 获取并设置urlInfo
		if (spilIndex != -1) {
			url = urlWithParam.substring(0, spilIndex);
		}

		if (url.equals("/") || url.length() == 0) {
			url="/index";
		} 
		
		if (!url.endsWith("/")) {
			url = url.concat("/");
		}
		request.setPathInf(url);
		// 检查是否为get方法，为get方法则获取参数，忽略掉请求体的东西
		if (requestMethod.equals("GET")) {
			// get请求解析url里的参数
			paramsMap = resolveUrlParam(urlWithParam.substring(spilIndex + 1));
			request.setParamtersMap(paramsMap);
		}

		// 获取设置请求头
		Map<String, String> headerMap = new HashMap<String, String>();
		while ((data = bufferedReader.readLine()) != null && data.length() > 1) {
			resolveHeaders(data, headerMap);
		}
		request.setHeadersMap(headerMap);
		// 解析post请求的请求体
		if (requestMethod.equals(POST)) {
			// post请求
			String contentLength = headerMap.get("Content-Length");
			// 判断有无内容长度请求头
			// 没有内容长度请求头的不接收
			if (contentLength == null) {
				// 没有长度，坏请求
				return null;
			}
			int length = -1;
			try {
				length = Integer.parseInt(headerMap.get("Content-Length"));
			} catch (Exception e) {
				return null;
			}
			if (length < 0) {
				// 坏请求
				// 长度<0，坏请求
				return null;
			}
			if (length > 0) {
				// 有内容体
				// 先不判断是不是文件请求
				char[] content = null;

				if (length > 8192) {
					// 非文件请求，最多8k数据
					// 坏请求
					return null;
				}

				content = new char[length];

				// 读取请求体，并解析
				int len = bufferedReader.read(content);
				if (len != -1) {
					paramsMap = resolveUrlParam(String.valueOf(content, 0, len));
					request.setParamtersMap(paramsMap);
				}
			}
		}
		request.setInputStream(new GmrServletInputStream(in));
		// 设置Session
		setSession(request);

		return request;
	}

	/**
	 * 获取session并设置到HttpRequest当中
	 * 
	 * @param request
	 */
	private static void setSession(FinalGmrRquest request) {
		request.setSession(HttpSessionFactory.getSession(getSessionId(request
				.getHeader("Cookie"))));
	}

	/**
	 * 解析请求头
	 * 
	 * @param headersData
	 * @param headerMap
	 */
	private static void resolveHeaders(String headersData,
			Map<String, String> headerMap) {
		int spiliteIndex = headersData.indexOf(":");
		String headerName = headersData.substring(0, spiliteIndex);
		String headerBody = headersData.substring(spiliteIndex + 1).trim();
		headerMap.put(headerName, headerBody);
	}

	/**
	 * 获取请求头里的sessionId
	 * 
	 * @param cookie
	 * @return
	 */
	private static String getSessionId(String cookie) {
		if (cookie == null
				|| (!cookie.contains("JSESSIONID=") && !cookie
						.contains("JSESSIONID ="))) {
			return null;
		}
		cookie = cookie.replace(" ", "");
		cookie = cookie.substring(cookie.indexOf("JSESSIONID=")
				+ "JSESSIONID=".length());
		int i = -1;
		i = cookie.indexOf(";");
		if (i == -1) {
			cookie = cookie.substring(0);
		} else {
			cookie = cookie.substring(0, i);
		}
		return cookie;
	}
}
