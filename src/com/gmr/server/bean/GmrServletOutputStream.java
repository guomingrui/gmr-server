package com.gmr.server.bean;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;

public class GmrServletOutputStream extends ServletOutputStream {
	private OutputStream outputStream;

	public GmrServletOutputStream(OutputStream outputStream) {
		super();
		this.outputStream = outputStream;
	}

	@Override
	public boolean isReady() {
		// 未实现
		return false;
	}

	@Override
	public void setWriteListener(WriteListener paramWriteListener) {
		// 未实现
	}

	@Override
	public void write(int b) throws IOException {
		this.outputStream.write(b);
	}

	@Override
	public void write(byte[] arg0) throws IOException {
		this.outputStream.write(arg0);
	}

	@Override
	public void write(byte[] arg0, int arg1, int arg2) throws IOException {
		this.outputStream.write(arg0, arg1, arg2);
	}

}
