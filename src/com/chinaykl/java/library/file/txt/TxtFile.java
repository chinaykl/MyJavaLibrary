package com.chinaykl.java.library.file.txt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.chinaykl.java.library.file.SimpleFile;

public class TxtFile extends SimpleFile {

	public static final int TXT_OPEN_ERROR = SIMPLEFILE_ERROR_CODE - 1;
	public static final int TXT_CLOSE_ERROR = TXT_OPEN_ERROR - 1;

	private FileReader mFileReader = null;
	private BufferedReader mBufferedReader = null;

	public TxtFile(String path) {
		super(path, FILE_MODE_R);
	}

	public int open() {
		
		int fileState = super.open();
		if (fileState != 0) {
			return fileState;
		}

		File file = null;
		file = new File(getPath());

		try {
			mFileReader = new FileReader(file);
		} catch (FileNotFoundException e) {
			return TXT_OPEN_ERROR;
		}

		mBufferedReader = new BufferedReader(mFileReader);

		return 0;
	}

	public String[] readAllLine() {
		ArrayList<String> configList = new ArrayList<String>();

		String line = null;
		do {
			try {
				line = mBufferedReader.readLine();
			} catch (IOException e) {
				return new String[0];
			}
			if (line != null) {
				line = line.trim();
				if (line.isEmpty() == false) {
					configList.add(line);
				}
			}
		} while (line != null);

		return (String[]) configList.toArray(new String[0]);
	}

	public int close() {
		int result = 0;

		try {
			mBufferedReader.close();
		} catch (IOException e) {
			result = TXT_CLOSE_ERROR;
		}

		try {
			mFileReader.close();
		} catch (IOException e) {
			result = TXT_CLOSE_ERROR;
		}

		return result;
	}
}
