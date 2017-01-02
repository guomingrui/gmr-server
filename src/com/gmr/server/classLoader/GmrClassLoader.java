package com.gmr.server.classLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

import com.gmr.server.enumeration.GmrSingleEnumeration;

public class GmrClassLoader extends URLClassLoader {
	private String rootPath;

	/**
	 * @param paths
	 *            项目
	 * @param rootPath
	 *            项目相对根目录
	 */
	public GmrClassLoader(String rootPath) {
		super(new URL[] {});
		setRootPath(rootPath);
	}

	public GmrClassLoader(String rootPath, URL[] urls) {
		super(urls);
		setRootPath(rootPath);
	}

	public GmrClassLoader(String rootPath, URL[] urls, ClassLoader parent) {
		super(urls, parent);
		setRootPath(rootPath);
	}

	private void setRootPath(String rootPath) {
		if (!rootPath.endsWith("/")) {
			rootPath = rootPath + "/";
		}
		this.rootPath = rootPath;
	}

	/**
	 * 加载jar
	 * 
	 * @param url
	 */
	public void addJar(URL url) {
		this.addURL(url);
	}

	/**
	 * 加载class下的的class
	 * 
	 * @param name
	 * @return
	 * @throws IOException
	 */
	private byte[] getClassBytes(String name) throws IOException {
		InputStream is = null;
		byte[] bt = null;
		File file = new File(rootPath + name.replace(".", "/") + ".class");
		if (!file.exists()) {
			return bt;
		}
		try {
			is = new FileInputStream(file);
			bt = new byte[is.available()];
			is.read(bt);
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			is.close();
		}
		return bt;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			byte[] bt = getClassBytes(name);
			if (bt == null) {
				return super.loadClass(name);
			}
			return defineClass(name, bt, 0, bt.length);
		} catch (IOException e) {
			throw new ClassNotFoundException("Class " + name + " not found.");
		}
	}

	@Override
	public URL getResource(String path) {
		// 定向到项目的根目录
		File file = new File(rootPath + path);
		if (file.exists()) {
			try {
				return file.toURI().toURL();
			} catch (MalformedURLException e) {
				throw new RuntimeException(e);
			}
		}
		URL url = super.getResource(path);
		if (url != null) {
			return url;
		}
		return null;
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		return new GmrSingleEnumeration<URL>(getResource(name));
	}

}
