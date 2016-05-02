package com.chinaykl.java.library.file.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqliteSimpliOperations extends DbBase {

	Connection mConnection = null;
	Statement mStatement = null;

	public SqliteSimpliOperations(String path) {
		super(path);
	}

	static final public int SQLITE_DRIVER_ERROR = -1;
	static final public int SQLITE_READ_ERROR = -2;

	public int read() {

		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			System.out.println("Driver load failed");
			return SQLITE_DRIVER_ERROR;
		}

		try {
			mConnection = DriverManager.getConnection("jdbc:sqlite:" + mPath);
			mConnection.setAutoCommit(false);
			mStatement = mConnection.createStatement();
		} catch (SQLException e) {
			System.out.println("File read failed");
			return SQLITE_READ_ERROR;
		}

		return 0;
	}

	static final public int SQLITE_SELECT_ERROR = -3;

	public int getSize(String table) {
		int result = 0;

		try {
			ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
			while (resultSet.next()) {
				result++;
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Sqlite SELECT failed");
		}

		return result;
	}

	public int getInt(String table, int index, String key) {
		int result = 0;

		try {
			ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
			while (resultSet.next()) {
				if (index-- == 0) {
					result = resultSet.getInt(key);
				}
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Sqlite SELECT failed");
		}

		return result;
	}

	public String getString(String table, int index, String key) {
		String result = "";

		try {
			ResultSet resultSet = mStatement.executeQuery("SELECT * FROM " + table + ";");
			while (resultSet.next()) {
				if (index-- == 0) {
					result = resultSet.getString(key);
				}
			}
			resultSet.close();
		} catch (SQLException e) {
			System.out.println("Sqlite SELECT failed");
		}

		return result;
	}

	static final public int SQLITE_CLOSE_ERROR = -4;

	public int close() {
		try {
			mStatement.close();
			mConnection.close();
		} catch (SQLException e) {
			System.out.println("File close failed");
			return SQLITE_CLOSE_ERROR;
		}

		return 0;
	}
}
