package com.gmr.server.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TimeZone;

import javax.servlet.http.HttpServletResponse;

import com.gmr.server.bean.FinalGmrResopnse;

public class ResponseGenerator {
	private static SimpleDateFormat sdf = null;
	static {
		sdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss 'GMT'", Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
	}

	/**
	 * 生成响应头
	 * 
	 * @param response
	 * @return
	 */
	public static String generateResponseHeader(HttpServletResponse response) {
		Map<String, String> responseHeaderMap = new HashMap<String, String>();
		FinalGmrResopnse gmrResopnse = (FinalGmrResopnse) response;
		for (Entry<String, String> entry : gmrResopnse.getAllHeaders()
				.entrySet()) {
			// 覆盖策略
			if ("content-type".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Content-Type", entry.getValue());

			} else if ("content-length".equals(entry.getKey().trim()
					.toLowerCase())) {
				responseHeaderMap.put("Content-Length", entry.getValue());

			} else if ("date".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Date", entry.getValue());

			} else if ("server".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Server", entry.getValue());

			} else if ("cache-control".equals(entry.getKey().trim()
					.toLowerCase())) {
				responseHeaderMap.put("Cache-Control", entry.getValue());

			} else if ("connection".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Connection", entry.getValue());

			} else if ("content-encoding".equals(entry.getKey().trim()
					.toLowerCase())) {
				responseHeaderMap.put("Content-Encoding", entry.getValue());

			} else if ("etag".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("ETag", entry.getValue());

			} else if ("keep-alive".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Keep-Alive", entry.getValue());

			} else if ("set-cookie".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Set-Cookie", entry.getValue());

			} else if ("transfer-encoding".equals(entry.getKey().trim()
					.toLowerCase())) {
				responseHeaderMap.put("Transfer-Encoding", entry.getValue());

			} else if ("bary".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Vary", entry.getValue());

			} else if ("x-powered-by".equals(entry.getKey().trim()
					.toLowerCase())) {
				responseHeaderMap.put("X-Powered-By", entry.getValue());
			} else if ("location".equals(entry.getKey().trim().toLowerCase())) {
				responseHeaderMap.put("Location", entry.getValue());
			} else {
				responseHeaderMap.put(entry.getKey(), entry.getValue());
			}
		}

		// 添加默认响应头
		if (!responseHeaderMap.containsKey("Server")) {
			responseHeaderMap.put("Server", "gmr/1.0");
		}

		if (!responseHeaderMap.containsKey("Date")) {
			responseHeaderMap.put("Date", getDateHeaderContent());
		}

		String encoding = gmrResopnse.getCharacterEncoding().trim();
		if (encoding.length() > 0) {
			putHeader("Content-Type", "charset=" + encoding, responseHeaderMap);
		}

		if (gmrResopnse.getContentLength() >= 0) {
			putHeader("Content-Length",
					String.valueOf(gmrResopnse.getContentLength()),
					responseHeaderMap);
		} else {
			putHeader("Transfer-Encoding", "chunked", responseHeaderMap);

		}
		if (response.getContentType() != null) {
			putHeader("Content-Type", response.getContentType(),
					responseHeaderMap);
		}

		if (gmrResopnse.getSessionId() != null) {
			putHeader("Set-Cookie", "JSESSIONID=" + gmrResopnse.getSessionId()
					+ "; HttpOnly", responseHeaderMap);
		}

		return generateResponseString(responseHeaderMap,
				gmrResopnse.getStatus());
	}

	private static String generateResponseString(
			Map<String, String> headersMap, int status) {
		// HTTP/1.1 200 OK
		StringBuilder sb = new StringBuilder();
		sb.append("HTTP/1.1 ").append(status);
		if(status/100==3){
			sb.append(" Found\r\n");
		}else{
			sb.append(" OK\r\n");
		}
		for (Entry<String, String> headerEntry : headersMap.entrySet()) {
			sb.append(headerEntry.getKey()).append(":")
					.append(headerEntry.getValue()).append("\r\n");
		}
		sb.append("\r\n");
		return sb.toString();
	}

	/**
	 * 放头到map里
	 * 
	 * @param headerName
	 * @param content
	 */
	private static void putHeader(String headerName, String content,
			Map<String, String> responseHeaderMap) {
		String oriContent = responseHeaderMap.get(headerName);
		content = content.trim();
		if (content != null && content.length() > 0) {
			if (oriContent != null) {
				responseHeaderMap.put(headerName, oriContent + ";" + content);
			} else {
				responseHeaderMap.put(headerName, content);
			}
		}
	}

	/**
	 * 获取GMT时间头内容
	 * 
	 * @return
	 */
	private static String getDateHeaderContent() {
		return sdf.format(Calendar.getInstance().getTime());
	}
}
