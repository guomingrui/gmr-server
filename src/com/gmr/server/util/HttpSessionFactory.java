package com.gmr.server.util;

import java.util.Comparator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;

import javax.servlet.http.HttpSession;

import com.gmr.server.bean.GmrSession;

public final class HttpSessionFactory {
	private static final Map<String, GmrSession> SESSION_MAP;
	private static final Map<String, String> SESSION_TIME_MAP;
	private static final char[] RANDOM_CODE;
	private static final Random RANDOM;

	static {
		SESSION_MAP = new ConcurrentSkipListMap<String, GmrSession>();
		SESSION_TIME_MAP = new ConcurrentSkipListMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String sessionId1, String sessionId2) {
						return sessionId1.compareTo(sessionId2);
					}

				});
		RANDOM_CODE = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
				'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
				'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i',
				'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u',
				'v', 'w', 'x', 'y', 'z' };
		RANDOM = new Random();
	}

	private HttpSessionFactory() {
	}

	/**
	 * 获取session
	 * 
	 * @param sessionId
	 * @return
	 */
	public static HttpSession getSession(String sessionId) {
		GmrSession session = null;
		if(sessionId!=null){
			session = SESSION_MAP.get(sessionId);
		}
		
		if (session == null) {
			String newSessionId = generateSessionId();
			session = new GmrSession(newSessionId);
			SESSION_MAP.put(newSessionId, session);
			SESSION_TIME_MAP.put(
					session.getLastAccessedTime()
							+ newSessionId.substring(0, 10), newSessionId);

		} else {
			String tenSessionId = sessionId.substring(0, 10);
			SESSION_TIME_MAP.remove(session.getLastAccessedTime()
					+ tenSessionId);
			session.setLastAccessedTime(System.currentTimeMillis());
			SESSION_TIME_MAP.put(session.getLastAccessedTime() + tenSessionId,
					sessionId);
		}
		return session;
	}

	/**
	 * 生成sessionId
	 * 
	 * @return
	 */
	private static String generateSessionId() {
		char[] sessionId = new char[32];
		for (int i = 0; i < 32; i++) {
			sessionId[i] = RANDOM_CODE[RANDOM.nextInt(62)];
		}
		return String.valueOf(sessionId);
	}

	/**
	 * 检查session,去除过期session
	 */
	public static void check() {
		long currentTime = System.currentTimeMillis();
		// 拷贝Map
		Map<String, String> timeSessionMap = new ConcurrentSkipListMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String sessionId1, String sessionId2) {
						return sessionId1.compareTo(sessionId2);
					}

				});
		timeSessionMap.putAll(SESSION_TIME_MAP);

		for (Entry<String, String> sessionTimeEntry : timeSessionMap.entrySet()) {
			String key = sessionTimeEntry.getKey();
			String value = sessionTimeEntry.getValue();
			HttpSession session = SESSION_MAP.get(value);
			// 超时30分钟
			// 先设定不能设置过期时间
			// 移除session
			if ((currentTime - session.getLastAccessedTime()) > 1800000) {
				SESSION_TIME_MAP.remove(key);
				SESSION_MAP.remove(value);
			}
		}
	}
}
