package com.chinaykl.java.library.file.log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LogFile {
	private FileWriter mLogFileWriter;
	private String mPath;

	public LogFile(String logFilePath) {
		File logFile = new File(logFilePath);
		if (logFile.exists()) {
			logFile.delete();
		}

		try {
			logFile.createNewFile();
		} catch (IOException e) {
			System.err.println("Creat log file failed");
		}

		FileWriter logFileWriter = null;
		try {
			logFileWriter = new FileWriter(logFile);
		} catch (IOException e) {
			System.err.println("Log file open failed");
		}

		mPath = logFilePath;
		mLogFileWriter = logFileWriter;
	}

	public String getPath() {
		return mPath;
	}

	public void writeln(String log) {
		if (mLogFileWriter != null) {
			try {
				mLogFileWriter.write(log + "\n");
				mLogFileWriter.flush();
				System.out.println(log);
			} catch (IOException e) {
				System.err.println("Log file write failed");
			}
		}
	}

	public void write(String log) {
		if (mLogFileWriter != null) {
			try {
				mLogFileWriter.write(log);
				mLogFileWriter.flush();
				System.out.println(log);
			} catch (IOException e) {
				System.err.println("Log file write failed");
			}
		}
	}

	public void close() {
		if (mLogFileWriter != null) {
			try {
				mLogFileWriter.flush();
			} catch (IOException e) {
				System.err.println("Log file write failed");
			}
			try {
				mLogFileWriter.close();
			} catch (IOException e) {
				System.err.println("Log file close failed");
			}
		}
	}
}
