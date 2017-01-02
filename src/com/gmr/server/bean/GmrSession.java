package com.gmr.server.bean;

import java.util.Enumeration;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import com.gmr.server.enumeration.GmrEnumeration;

@SuppressWarnings("deprecation")
public class GmrSession extends GmrHttpSessionImpl implements HttpSession{

	public GmrSession(String sessionId) {
		super(sessionId);
	}

	@Override
	public long getCreationTime() {
		return super.createTime;
	}

	@Override
	public String getId() {
		return super.sessionId;
	}

	@Override
	public long getLastAccessedTime() {
		return super.lastAccessedTime;
	}

	@Override
	public ServletContext getServletContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setMaxInactiveInterval(int paramInt) {
		super.inactiveInterval = paramInt;
		
	}

	@Override
	public int getMaxInactiveInterval() {
		return super.inactiveInterval;
	}

	@Override
	public HttpSessionContext getSessionContext() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getAttribute(String paramString) {
		return super.attributeMap.get(paramString);
	}

	@Override
	public Object getValue(String paramString) {
		//暂时同上
		return super.attributeMap.get(paramString);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		return new GmrEnumeration<String>(super.attributeMap.keySet());
	}

	@Override
	public String[] getValueNames() {
		Set<String> valueNamesSet = super.attributeMap.keySet();
		return valueNamesSet.toArray(new String[valueNamesSet.size()]);
	}

	@Override
	public void setAttribute(String paramString, Object paramObject) {
		super.attributeMap.put(paramString, paramObject);
		
	}

	@Override
	public void putValue(String paramString, Object paramObject) {
		//暂时视同为attribute
		super.attributeMap.put(paramString, paramObject);
	}

	@Override
	public void removeAttribute(String paramString) {
		super.attributeMap.remove(paramString);
	}

	@Override
	public void removeValue(String paramString) {
		super.attributeMap.remove(paramString);
		
	}

	@Override
	public void invalidate() {
		
	}

	@Override
	public boolean isNew() {
		return super.isNew;
	}
	
}
