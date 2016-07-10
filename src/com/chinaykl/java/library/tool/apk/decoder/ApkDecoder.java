package com.chinaykl.java.library.tool.apk.decoder;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ApkDecoder {

	private String MAINCLASSNAME = "brut.apktool.Main";
	private String MAINMETHODNAME = "main";

	private Class<?> mApkToolClass = null;

	public ApkDecoder(String apktoolPath) {
		URL apktoolUrl = null;
		try {
			apktoolUrl = new URL("file:" + apktoolPath);
		} catch (MalformedURLException e) {
			System.out.println("Unknown protocol is found");
		}
		URLClassLoader apktoolClassLoader = new URLClassLoader(new URL[] { apktoolUrl });
		Thread.currentThread().setContextClassLoader(apktoolClassLoader);
		try {
			mApkToolClass = apktoolClassLoader.loadClass(MAINCLASSNAME);
		} catch (ClassNotFoundException e) {
			System.out.println("Class not found");
		}
	}

	public boolean decode(String apkPath, String outputPath) {
		Method apktoolMainMethod = null;
		try {
			apktoolMainMethod = mApkToolClass.getMethod(MAINMETHODNAME, String[].class);
		} catch (NoSuchMethodException e) {
			System.out.println("No such method");
		} catch (SecurityException e) {
			System.out.println("No permission");
		}

		String apkName = apkPath.substring(apkPath.lastIndexOf(File.separator) + 1, apkPath.lastIndexOf('.'));
		String apkDcPath = outputPath + File.separator + apkName;
		boolean exception = false;
		try {
			apktoolMainMethod.invoke(mApkToolClass.newInstance(),
					(Object) new String[] { "d", "-s", "-f", "-o", apkDcPath, apkPath });
		} catch (IllegalAccessException e) {
			System.out.println("Apktool access");
			exception = true;
		} catch (IllegalArgumentException e) {
			System.out.println("Apktool argument");
			exception = true;
		} catch (InvocationTargetException e) {
			System.out.println("Apktool throws an exception");
			exception = true;
		} catch (InstantiationException e) {
			System.out.println("Apktool code change");
			exception = true;
		}
		return !exception;
	}
}
