package com.chinaykl.java.library.file;

import java.io.File;
import java.util.ArrayList;

public class SimpleDirectory extends BaseFile {
	// 重新创建
	public static final int DIR_MODE_WP = 1;
	// 添加创建
	public static final int DIR_MODE_AP = 2;

	public SimpleDirectory(String path, int mode) {
		super(path);

		File file = new File(getPath());
		switch (mode) {
		case DIR_MODE_WP:
			if (isExist() == true) {
				delete();
			}
			file.mkdir();
			break;
		case DIR_MODE_AP:
			if (isExist() == true) {
				if (isFile() == true) {
					delete();
				}
			}
			file.mkdir();
			break;
		default:
			break;
		}
	}

	public String[] getSelectedFilePaths(IFileFilter filter) {
		File file = new File(getPath());
		File[] subFileList = file.listFiles();
		ArrayList<File> selectedFileList = new ArrayList<File>();

		for (int i = 0; i < subFileList.length; i++) {
			if (filter.isSelected(subFileList[i]) == true) {
				selectedFileList.add(subFileList[i]);
			}
		}

		String[] result = new String[selectedFileList.size()];
		for (int i = 0; i < selectedFileList.size(); i++) {
			result[i]=selectedFileList.get(i).getAbsolutePath();
		}

		return result;
	}
}
