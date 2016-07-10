package com.chinaykl.java.library.file.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.chinaykl.java.library.file.SimpleFile;

public class SqliteFile extends SimpleFile {

	public static final int SQLITE_DRIVER_ERROR = SIMPLEFILE_ERROR_CODE - 1;
	public static final int SQLITE_OPEN_ERROR = SQLITE_DRIVER_ERROR - 1;
	public static final int SQLITE_SELECT_ERROR = SQLITE_OPEN_ERROR - 1;
	public static final int SQLITE_CLOSE_ERROR = SQLITE_SELECT_ERROR - 1;

	private Connection mConnection = null;
	private Statement mStatement = null;

	public SqliteFile(String path) {
		super(path, FILE_MODE_R);
	}

	public int open() {
		int fileState = super.open();
		if (fileState != 0) {
			return fileState;
		}

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			return SQLITE_DRIVER_ERROR;
		}

		try {
			mConnection = DriverManager.getConnection("jdbc:sqlite:" + getPath());
			mConnection.setAutoCommit(false);
			mStatement = mConnection.createStatement();
		} catch (SQLException e) {
			return SQLITE_OPEN_ERROR;
		}
		return 0;
	}

	public int getTableSize(String table) throws SQLException {
		int result = 0;

		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
		while (resultSet.next()) {
			result++;
		}
		resultSet.close();

		return result;
	}

	public int getInt(String table, int index, String key) throws SQLException {
		int result = 0;

		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
		while (resultSet.next()) {
			if (index-- == 0) {
				result = resultSet.getInt(key);
			}
		}
		resultSet.close();

		return result;
	}

	public String getString(String table, int index, String key) throws SQLException {
		String result = "";

		ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
		while (resultSet.next()) {
			if (index-- == 0) {
				result = resultSet.getString(key);
			}
		}
		resultSet.close();

		return result;
	}

	public int close() {
		try {
			mStatement.close();
			mConnection.close();
		} catch (SQLException e) {
			return SQLITE_CLOSE_ERROR;
		}

		int fileState = super.close();
		return fileState;
	}
}
