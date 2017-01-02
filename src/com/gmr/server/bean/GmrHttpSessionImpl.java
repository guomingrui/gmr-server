package com.gmr.server.bean;

import java.util.HashMap;
import java.util.Map;

public class GmrHttpSessionImpl {
	protected long createTime;
	protected final Map<String, Object> attributeMap = new HashMap<String, Object>();
	// 32位的sessionId
	protected final String sessionId;
	// 应该是最后一次该session获取时间
	protected long lastAccessedTime;
	protected boolean isNew;
	// session存活时间默认分钟
	protected int inactiveInterval;

	public GmrHttpSessionImpl(String sessionId) {
		this.createTime = System.currentTimeMillis();
		this.lastAccessedTime = this.createTime;
		this.isNew = true;
		this.sessionId = sessionId;
	}

	public long getLastAccessedTime() {
		return lastAccessedTime;
	}

	public void setLastAccessedTime(long lastAccessedTime) {
		this.lastAccessedTime = lastAccessedTime;
	}

	public void setNew(boolean isNew) {
		this.isNew = isNew;
	}

}
