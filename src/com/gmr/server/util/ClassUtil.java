package com.gmr.server.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.gmr.server.classLoader.GmrClassLoader;

/**
 * 类相关操作工具
 * 
 * @author gmr
 *
 */
public class ClassUtil {

	public static ClassLoader getClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	/**
	 * 加载类
	 * 
	 * @param className
	 * @param isInited
	 * @return
	 */
	private static Class<?> loadClass(String className, boolean isInited) {
		try {
			return Class.forName(className, isInited, getClassLoader());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	private static void addJar(URL url) {
		((GmrClassLoader) getClassLoader()).addJar(url);
	}

	/**
	 * 初始话class文件
	 * 
	 * @param url
	 */
	public static void initClass(URL url) {
		initClass(url, false);
	}

	/**
	 * 获取包下的所有class集合
	 * 
	 * @param packageName
	 * @return
	 */
	public static void initClass(URL url, boolean isJarPath) {
		if (url != null) {
			/*
			 * 协议处理
			 */
			if (url.getProtocol().equals("file") && !isJarPath) {
				// 替换url的空格的unicode编码成空格
				// url--->Path
				// 增加该路径下的类
				addClass(url.getPath().replace("%20", " "), "");
			} else {
			}
		} else {
		}

	}

	/**
	 * @param packagePath
	 *            包路径
	 * @param packageName
	 *            包名
	 */
	private static void addClass(String packagePath, String packageName) {
		// 链接包路径
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			public boolean accept(File file) {
				return ((file.getName().endsWith(".class") || file.getName()
						.endsWith(".jar")) && file.isFile())
						|| file.isDirectory();
			}
		});
		// 对包路径下遍历
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (file.isFile()) {
				if (file.getName().endsWith(".class")) {
					String className = file.getName().substring(0,
							file.getName().lastIndexOf("."));
					if (packageName != null && packageName.trim().length() > 0) {
						className = packageName + "." + className;
					}
					loadClass(className, false);
				} else {
					JarFile jarFile = null;
					try {
						jarFile = new JarFile(file);
					} catch (IOException e) {
						throw new RuntimeException(e);
					}
					try {
						if (jarFile != null) {
							/* 遍历jar里的文件 */
							Enumeration<JarEntry> jarEnums = jarFile.entries();
							while (jarEnums.hasMoreElements()) {
								JarEntry jarEntry = jarEnums.nextElement();
								String jarEntryName = jarEntry.getName();
								if (jarEntryName.endsWith(".class")) {
									String className = jarEntryName.substring(
											0, jarEntryName.lastIndexOf("."))
											.replace("/", ".");
									loadClass(className, false);

								}
							}
						}
					} finally {
						if (jarFile != null) {
							try {
								jarFile.close();
							} catch (IOException e) {
							}
						}
					}
				}
			} else {
				String nextPackagePath = file.getName();
				String nextPackageName = nextPackagePath;

				if (packagePath != null && packagePath.trim().length() > 0) {
					nextPackagePath = packagePath + "/" + file.getName();
				}
				if (packageName != null && packageName.trim().length() > 0) {
					nextPackageName = packageName + "." + file.getName();
				}
				addClass(nextPackagePath, nextPackageName);
			}
		}
	}

	/**
	 * 链接jar包
	 * 
	 * @param path
	 */
	public static void addJars(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isDirectory()) {
				for (String childPath : file.list()) {
					addJars(path + childPath + "/");
				}
			} else if (file.getName().endsWith(".jar")) {
				try {
					addJar(file.toURI().toURL());
				} catch (MalformedURLException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
}
