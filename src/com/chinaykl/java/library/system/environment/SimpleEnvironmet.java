package com.chinaykl.java.library.system.environment;

import java.io.File;

public class SimpleEnvironmet {
	private static String JARPATH = "java.class.path";

	public static String getExecutePath() {
		return System.getProperty(JARPATH).substring(0, System.getProperty(JARPATH).lastIndexOf(File.separator));
	}
}
