package com.chinaykl.java.library.file;

import java.io.File;
import java.io.IOException;

public class SimpleFile extends BaseFile {

	// r 打开一个已有的文本文件，允许读取文件。
	public static final int FILE_MODE_R = 1;
	// w 打开一个文本文件，允许写入文件。如果文件不存在，则会创建一个新文件。在这里，您的程序会从文件的开头写入内容。
	public static final int FILE_MODE_W = 2;
	// a 打开一个文本文件，以追加模式写入文件。如果文件不存在，则会创建一个新文件。在这里，您的程序会在已有的文件内容中追加内容。
	public static final int FILE_MODE_A = 3;
	// r+ 打开一个文本文件，允许读写文件。
	public static final int FILE_MODE_RP = 4;
	// w+ 打开一个文本文件，允许读写文件。如果文件已存在，则文件会被截断为零长度，如果文件不存在，则会创建一个新文件。
	public static final int FILE_MODE_WP = 5;
	// a+ 打开一个文本文件，允许读写文件。如果文件不存在，则会创建一个新文件。读取会从文件的开头开始，写入则只能是追加模式。
	public static final int FILE_MODE_AP = 6;

	protected static final int SIMPLEFILE_ERROR_CODE = FILE_ALREADY_EXIST;

	public SimpleFile(String path, int mode) {
		super(path);
		switch (mode) {
		case FILE_MODE_R:
			if ((isExist() == false) || (isFile() == false)) {
				init();
			}
			break;
		case FILE_MODE_W:

			break;
		case FILE_MODE_A:

			break;
		case FILE_MODE_RP:

			break;
		case FILE_MODE_WP:
			if (isExist() == true) {
				if (delete() == false) {
					init();
					break;
				}
			}
			if (isExist() == false) {
				File file = new File(getPath());
				try {
					file.createNewFile();
				} catch (IOException e) {
					init();
				}
			} else {
				init();
			}
			break;
		case FILE_MODE_AP:
			if (isExist() == false) {
				File file = new File(getPath());
				try {
					file.createNewFile();
				} catch (IOException e) {
					init();
				}
			}
			break;
		default:
			init();
			break;
		}
	}

	public int open() {
		return isAvailable() == true ? 0 : FILE_OPERATE_FAIL;
	}

	public int close() {
		return isAvailable() == true ? 0 : FILE_OPERATE_FAIL;
	}
}
