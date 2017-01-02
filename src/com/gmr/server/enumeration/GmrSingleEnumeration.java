package com.gmr.server.enumeration;

import java.util.Enumeration;

public class GmrSingleEnumeration<E> implements Enumeration<E> {

	private boolean hasMore = true;
	private E obj;

	public GmrSingleEnumeration(E obj) {
		if(obj==null){
			hasMore = false;
		}
		this.obj = obj;
	}

	@Override
	public boolean hasMoreElements() {
		return hasMore;
	}

	@Override
	public E nextElement() {
		hasMore = false;
		return obj;
	}

}
