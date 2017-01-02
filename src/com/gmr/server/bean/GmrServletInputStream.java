package com.gmr.server.bean;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

/**
 * 先不实现
 * @author gmr
 *
 */
public class GmrServletInputStream extends ServletInputStream{
	private InputStream inputStream = null;
	public GmrServletInputStream(InputStream inputStream) {
		super();
		this.inputStream = inputStream;
	}

	@Override
	public boolean isFinished() {
		//未实现
		return false;
	}

	@Override
	public boolean isReady() {
		//未实现
		return false;
	}

	@Override
	public void setReadListener(ReadListener arg0) {
		//未实现
	}

	@Override
	public int read() throws IOException {
		return inputStream.read();
	}
}
