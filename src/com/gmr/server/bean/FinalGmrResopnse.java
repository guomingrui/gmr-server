package com.gmr.server.bean;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;

import com.gmr.server.util.ConfigUtil;
import com.gmr.server.util.ResponseGenerator;

public class FinalGmrResopnse extends GmrResponse {
	private Map<String, List<String>> headersArrayMap = new HashMap<String, List<String>>();
	private int status = 200;
	private String characterEncoding = "";
	private String contentType = null;
	private PrintWriter printWriter = null;
	private boolean isFinised = false;
	private String hostHeader = null;

	@Override
	public void sendRedirect(String path) throws IOException {
		// 302
		// 重定向
		setStatus(302);
		setContentLength(0);
		if (path.length() > 6 && path.substring(0, 7).contains(":")) {
			if(path.startsWith("/")){
				path = path.substring(1);
			}
			setHeader("Location", path);
		} else {
			if (path.trim().startsWith("//")) {
				setHeader("Location", "http:" + path);
			} else {
				if (!path.startsWith("/")) {
					path = "/" + path;
				}
				if (hostHeader == null) {
					setHeader("Location",
							"http://localhost:" + ConfigUtil.getListenPort() + path);
				} else {
					setHeader("Location", "http://"+this.hostHeader + path);
				}
			}
		}
		getWriter().flush();
	}

	public void setHostHeader(String hostHeader) {
		this.hostHeader = hostHeader;
	}

	public FinalGmrResopnse() {
	}

	public Map<String, String> getAllHeaders() {
		return super.headersMap;
	}

	@Override
	public String getCharacterEncoding() {
		return this.characterEncoding;
	}

	@Override
	public String getContentType() {
		return this.contentType;

	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		return super.outputStream;
	}

	public void setFinised(boolean isFinised) {
		this.isFinised = isFinised;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		if (this.printWriter == null) {
			if (this.characterEncoding.length() <= 0) {
				this.printWriter = new GmrPrintWriter(this.outputStream);
			} else {
				// 编码，编码
				this.printWriter = new GmrPrintWriter(this.outputStream);
			}
		}
		return this.printWriter;
	}

	@Override
	public void setCharacterEncoding(String encoding) {
		if (encoding == null) {
			encoding = "";
		}
		if (!this.characterEncoding.equals(encoding.toLowerCase())) {
			this.printWriter = null;
		}
		this.characterEncoding = encoding.toLowerCase();
	}

	@Override
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}

	@Override
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public boolean containsHeader(String header) {
		return super.headersMap.containsKey(header);
	}

	@Override
	public void sendError(int paramInt) throws IOException {
		sendError(paramInt, "");

	}

	@Override
	public void sendError(int errorCode, String errordeMessage)
			throws IOException {
		String pageError = ErrorPage.getErrorPage(errorCode, errordeMessage);
		this.contentType = "text/html";
		this.status = errorCode;
		getWriter().write(pageError);
	}

	@Override
	public void setHeader(String header, String content) {
		super.headersMap.put(header, content);
		this.headersArrayMap.remove(header);
	}

	@Override
	public void addHeader(String key, String value) {
		if (key != null) {
			getHeaders(key).add(value);
			setHeader(key, getHeader(key).concat(value));
		}
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public int getStatus() {
		return this.status;
	}

	@Override
	public String getHeader(String paramString) {
		return super.headersMap.get(paramString);
	}

	@Override
	public Collection<String> getHeaders(String paramString) {
		List<String> headers = this.headersArrayMap.get(paramString);
		if (headers == null) {
			String header = super.headersMap.get(paramString);
			if (header == null) {
				headers = new ArrayList<String>();
			} else {
				headers = Arrays.asList(header.split("; *"));
			}
			this.headersArrayMap.put(paramString, headers);
		}
		return headers;
	}

	@Override
	public Collection<String> getHeaderNames() {
		return super.headersMap.keySet();
	}

	class GmrPrintWriter extends PrintWriter {
		private CharBuffer charBuffer;
		private boolean isInit = true;
		private PrintWriter gmrPrintWriter;

		public GmrPrintWriter(OutputStream outputStream) {
			super(outputStream);
			try {
				gmrPrintWriter = new PrintWriter(new OutputStreamWriter(
						outputStream, "gbk"));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			this.charBuffer = CharBuffer.allocate(2000);
		}

		public GmrPrintWriter(OutputStream outputStream, boolean isFlushed) {
			super(outputStream, isFlushed);
			try {
				gmrPrintWriter = new PrintWriter(new OutputStreamWriter(
						outputStream, "gbk"), isFlushed);
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(e);
			}
			this.charBuffer = CharBuffer.allocate(2000);
		}

		@Override
		public void write(int intParam) {
			write(String.valueOf(intParam));

		}

		@Override
		public void write(char[] param, int start, int len) {
			if (param == null || param.length < 1) {
				return;
			}
			write(String.valueOf(param), start, len);
		}

		@Override
		public void write(char[] param) {
			if (param == null || param.length < 1) {
				return;
			}
			write(String.valueOf(param));
		}

		@Override
		public void write(String param, int start, int len) {
			if (param.length() < len + start) {
				if (param.length() <= start) {
					return;
				}
				write(param.substring(start));
			} else {
				write(param.substring(start, start + len));
			}
		}

		@Override
		public void write(String param) {
			if (param.length() >= charBuffer.length()) {
				int length = charBuffer.length();
				charBuffer.put(param, 0, length);
				flush();
				write(param.substring(param.length()));
			} else {
				charBuffer.put(param);
			}
		}

		@Override
		public void flush() {
			if (isInit) {
				gmrPrintWriter.write(ResponseGenerator
						.generateResponseHeader(FinalGmrResopnse.this));
				gmrPrintWriter.flush();
				isInit = false;
			}
			if (isFinised && contentLength < 0) {
				gmrPrintWriter.write("0\r\n\r\n");
				gmrPrintWriter.flush();
			}

			if (charBuffer.capacity() == charBuffer.length()) {
				return;
			}
			charBuffer.flip();
			String content = charBuffer.toString();
			if (contentLength < 0) {
				try {
					gmrPrintWriter.write(Integer.toHexString(content
							.getBytes("GBK").length) + "\r\n");
				} catch (UnsupportedEncodingException e) {
					throw new RuntimeException(e);
				}
				gmrPrintWriter.flush();
			}
			gmrPrintWriter.write(content);
			charBuffer.clear();
			if (contentLength < 0) {
				gmrPrintWriter.write("\r\n");
			}
			gmrPrintWriter.flush();
		}

	}
}
