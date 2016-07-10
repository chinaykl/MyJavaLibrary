package com.chinaykl.java.library.file.excel;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.common.usermodel.Hyperlink;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.chinaykl.java.library.file.SimpleFile;

public class ExcelFile extends SimpleFile {

	public static final int EXCEL_CREATE_FAIL = SIMPLEFILE_ERROR_CODE - 1;
	public static final int EXCEL_OPEN_FAIL = EXCEL_CREATE_FAIL - 1;
	public static final int EXCEL_CLOSE_FAIL = EXCEL_OPEN_FAIL - 1;
	public static final int EXCEL_SHEET_ALREADY_EXIST = EXCEL_CLOSE_FAIL - 1;
	public static final int EXCEL_SHEET_NOT_EXIST = EXCEL_SHEET_ALREADY_EXIST - 1;
	public static final int EXCEL_SHEET_NOT_EMPTY = EXCEL_SHEET_NOT_EXIST - 1;
	public static final int EXCEL_SHEET_CREATE_FAIL = EXCEL_SHEET_NOT_EMPTY - 1;

	private static final int EXCEL_SHEET_NAME_MAX = 31;

	private XSSFWorkbook mWorkbook = null;

	public ExcelFile(String path, boolean append) {
		super(path, append == true ? FILE_MODE_AP : FILE_MODE_WP);
	}

	public int open() {
		int fileState = super.open();
		if (fileState != 0) {
			return fileState;
		}

		// try {
		// File file = new File(getPath());
		// mWorkbook = new XSSFWorkbook(file);
		// } catch (InvalidFormatException | IOException e) {
		// return EXCEL_OPEN_FAIL;
		// }
		mWorkbook = new XSSFWorkbook();
		return 0;
	}

	public int format() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(getPath());
		} catch (FileNotFoundException e) {
			return EXCEL_CREATE_FAIL;
		}
		XSSFWorkbook workbook = null;
		try {
			workbook = new XSSFWorkbook();
			workbook.createSheet();
			workbook.write(fos);
			workbook.close();
		} catch (IOException e) {
			return EXCEL_CREATE_FAIL;
		}

		try {
			fos.flush();
			fos.close();
		} catch (IOException e) {
			return EXCEL_CREATE_FAIL;
		}

		return 0;
	}

	public int getSheetNum() {
		return mWorkbook.getNumberOfSheets();
	}

	public int getRowNum(String sheetName) {
		XSSFSheet sheet = mWorkbook.getSheet(sheetName);
		if (sheet == null) {
			return 0;
		}
		int lastNum = sheet.getLastRowNum();
		return lastNum;
	}

	public boolean isSheetExist(String sheetName) {
		XSSFSheet sheet = mWorkbook.getSheet(sheetName);
		if (sheet == null) {
			return false;
		}
		return true;
	}

	public String getAvaliableSheetName(String sheetName) {
		String name = sheetName;
		if (name.length() > EXCEL_SHEET_NAME_MAX) {
			name = name.substring(0, EXCEL_SHEET_NAME_MAX - 1);
		}
		return name;
	}

	public boolean isRowExist(String sheetName, int rowNum) {
		if (isSheetExist(sheetName) == true) {
			XSSFRow row = mWorkbook.getSheet(sheetName).getRow(rowNum);
			if (row != null) {
				return true;
			}
		}
		return false;
	}

	public boolean isCellExist(String sheetName, int rowNum, int cellNum) {
		if (isRowExist(sheetName, rowNum) == true) {
			XSSFCell cell = mWorkbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
			if (cell != null) {
				return true;
			}
		}
		return false;
	}

	public int addSheet(String sheetName) {
		if (isSheetExist(sheetName) == true) {
			return EXCEL_SHEET_ALREADY_EXIST;
		}

		mWorkbook.createSheet(sheetName);

		if (isSheetExist(sheetName) == false) {
			return EXCEL_SHEET_CREATE_FAIL;
		}
		return 0;
	}

	public int addRow(String sheetName, String[] rowValue) {
		if (isSheetExist(sheetName) == false) {
			return EXCEL_SHEET_NOT_EXIST;
		}

		int rowNum = getRowNum(sheetName);
		XSSFRow row = mWorkbook.getSheet(sheetName).createRow(rowNum + 1);
		for (int i = 0; i < rowValue.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(rowValue[i]);
		}
		return 0;
	}

	public int addTitle(String sheetName, String[] title) {
		if (isSheetExist(sheetName) == false) {
			return EXCEL_SHEET_NOT_EXIST;
		}
		if (getRowNum(sheetName) != 0) {
			return EXCEL_SHEET_NOT_EMPTY;
		}

		XSSFRow row = mWorkbook.getSheet(sheetName).createRow(0);
		for (int i = 0; i < title.length; i++) {
			XSSFCell cell = row.createCell(i);
			cell.setCellValue(title[i]);
		}
		return 0;
	}

	public int addTable(String sheetName, String[][] table) {
		if (isSheetExist(sheetName) == false) {
			return EXCEL_SHEET_NOT_EXIST;
		}

		int startRow = getRowNum(sheetName);
		for (int i = 0; i < table.length; i++) {
			XSSFRow row = mWorkbook.getSheet(sheetName).createRow(startRow + i);
			for (int j = 0; j < table[i].length; j++) {
				XSSFCell cell = row.createCell(j);
				cell.setCellValue(table[i][j]);
			}
		}
		return 0;
	}

	public String getCellValue(String sheetName, int rowNum, int cellNum) {
		if (isCellExist(sheetName, rowNum, cellNum) == false) {
			return null;
		}
		return mWorkbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).getStringCellValue();
	}

	public int setCellValue(String sheetName, int rowNum, int cellNum, String value) {
		if (isCellExist(sheetName, rowNum, cellNum) == false) {
			return 0;
		}
		mWorkbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum).setCellValue(value);
		return 0;

	}

	public int setCellLink(String sheetName, int rowNum, int cellNum, String linkName, String URIValue) {
		if (isSheetExist(sheetName) == false) {
			return EXCEL_SHEET_NOT_EXIST;
		}

		XSSFCellStyle hlinkstyle = mWorkbook.createCellStyle();
		XSSFHyperlink link = mWorkbook.getCreationHelper().createHyperlink(Hyperlink.LINK_FILE);
		link.setAddress(URIValue);
		XSSFCell cell = mWorkbook.getSheet(sheetName).getRow(rowNum).getCell(cellNum);
		cell.setHyperlink(link);
		cell.setCellStyle(hlinkstyle);
		return 0;
	}

	public int close() {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(getPath());
		} catch (FileNotFoundException e) {
			return EXCEL_CREATE_FAIL;
		}

		try {
			mWorkbook.write(fos);
			mWorkbook.close();
		} catch (IOException e) {
			return EXCEL_CLOSE_FAIL;
		}

		try {
			fos.flush();
			fos.close();
		} catch (IOException e) {
			return EXCEL_CREATE_FAIL;
		}

		int fileState = super.close();
		return fileState;
	}
}
