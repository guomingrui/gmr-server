package com.gmr.server.bean;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class GmrResponseImpl implements HttpServletResponse{

	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBufferSize(int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setCharacterEncoding(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentLength(int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentLengthLong(long paramLong) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setContentType(String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setLocale(Locale paramLocale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCookie(Cookie paramCookie) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean containsHeader(String paramString) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String encodeURL(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectURL(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeUrl(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String encodeRedirectUrl(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendError(int paramInt, String paramString) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendError(int paramInt) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendRedirect(String paramString) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setDateHeader(String paramString, long paramLong) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addDateHeader(String paramString, long paramLong) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHeader(String paramString1, String paramString2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addHeader(String paramString1, String paramString2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIntHeader(String paramString, int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addIntHeader(String paramString, int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int paramInt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatus(int paramInt, String paramString) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getStatus() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getHeader(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaders(String paramString) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<String> getHeaderNames() {
		// TODO Auto-generated method stub
		return null;
	}

}
