package com.chinaykl.java.library.file;

import java.io.File;
import java.io.IOException;

public class SimpleFile extends BaseFile {

	// r ��һ�����е��ı��ļ��������ȡ�ļ���
	public static final int FILE_MODE_R = 1;
	// w ��һ���ı��ļ�������д���ļ�������ļ������ڣ���ᴴ��һ�����ļ�����������ĳ������ļ��Ŀ�ͷд�����ݡ�
	public static final int FILE_MODE_W = 2;
	// a ��һ���ı��ļ�����׷��ģʽд���ļ�������ļ������ڣ���ᴴ��һ�����ļ�����������ĳ���������е��ļ�������׷�����ݡ�
	public static final int FILE_MODE_A = 3;
	// r+ ��һ���ı��ļ��������д�ļ���
	public static final int FILE_MODE_RP = 4;
	// w+ ��һ���ı��ļ��������д�ļ�������ļ��Ѵ��ڣ����ļ��ᱻ�ض�Ϊ�㳤�ȣ�����ļ������ڣ���ᴴ��һ�����ļ���
	public static final int FILE_MODE_WP = 5;
	// a+ ��һ���ı��ļ��������д�ļ�������ļ������ڣ���ᴴ��һ�����ļ�����ȡ����ļ��Ŀ�ͷ��ʼ��д����ֻ����׷��ģʽ��
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
