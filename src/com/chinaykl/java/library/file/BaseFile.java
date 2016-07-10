package com.chinaykl.java.library.file;

import java.io.File;
import java.io.IOException;

class BaseFile {

	public static final int FILE_OPERATE_FAIL = -1;
	public static final int FILE_NAME_UNAVAILABLE = FILE_OPERATE_FAIL - 1;
	public static final int FILE_ALREADY_EXIST = FILE_NAME_UNAVAILABLE - 1;

	private File mFile = null;

	public BaseFile(String path) {
		mFile = new File(path);
	}

	public boolean isExist() {
		return mFile.exists();
	}

	public boolean isFile() {
		return mFile.isFile();
	}

	public boolean isDirectory() {
		return mFile.isDirectory();
	}

	protected void init() {
		mFile = null;
	}

	protected boolean isAvailable() {
		if (mFile == null) {
			return false;
		}
		return true;
	}

	public String getPath() {
		if (isAvailable() == false) {
			return "";
		}
		try {
			return mFile.getCanonicalPath();
		} catch (IOException e) {
			return "";
		}
	}

	public String getName() {
		if (isAvailable() == false) {
			return "";
		}
		return mFile.getName();
	}

	public int rename(String name) {
		if (isAvailable() == false) {
			return FILE_OPERATE_FAIL;
		}

		if (name.contains(File.separator) == true) {
			return FILE_NAME_UNAVAILABLE;
		}

		String newPath = getPath().substring(0, getPath().lastIndexOf(File.separatorChar)) + File.separator + name;
		File newFile = new File(newPath);
		if (newFile.exists() == true) {
			return FILE_ALREADY_EXIST;
		}
		mFile.renameTo(newFile);
		return 0;

	}

	public boolean delete() {
		if (isAvailable() == false) {
			return false;
		}
		return delete(mFile);
	}

	private boolean delete(File file) {
		File[] subFiles = file.listFiles();
		if (subFiles != null) {
			for (int i = 0; i < subFiles.length; i++) {
				File subFile = subFiles[i];
				if (subFile.isFile() == true) {
					subFile.delete();
				} else if (subFile.isDirectory() == true) {
					delete(subFile);
				}
			}
		}
		return file.delete();
	}
}
