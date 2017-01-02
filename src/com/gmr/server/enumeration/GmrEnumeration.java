package com.gmr.server.enumeration;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;

public class GmrEnumeration<E> implements Enumeration<E> {
	private Iterator<E> iterator = null;

	public GmrEnumeration(Collection<E> collection) {
		this.iterator = collection.iterator();
	}

	@Override
	public boolean hasMoreElements() {
		return iterator.hasNext();
	}

	@Override
	public E nextElement() {
		return iterator.next();
	}

}
