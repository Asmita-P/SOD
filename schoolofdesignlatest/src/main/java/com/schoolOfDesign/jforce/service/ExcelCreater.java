package com.schoolOfDesign.jforce.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelCreater {

	public static void CreateExcelFile(List<Map<String, Object>> lstExcelData,
			List<String> header, String fileName) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("ICA Sheet");

		// skip first row as it contains headers so start enter value from
		// second row onward
		int rowNum = 1;

		Row headerRow = sheet.createRow(0);
		for (int colNum = 0; colNum < header.size(); colNum++) {
			Cell cell = headerRow.createCell(colNum);
			cell.setCellValue(header.get(colNum));
		}

		for (Map<String, Object> map : lstExcelData) {
			Row row = sheet.createRow(rowNum++);

			for (int colNum = 0; colNum < header.size(); colNum++) {
				Cell cell = row.createCell(colNum);
				cell.setCellValue((String) map.get(header.get(colNum)));
			}
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("Done");
	}

	public static void CreateExcelFileCustom(Map<String, List<Map>> mapList,
			List<String> header, String fileName) {
		XSSFWorkbook workbook = new XSSFWorkbook();
		int rollNo = 1;

		for (Map.Entry<String, List<Map>> m : mapList.entrySet()) {

			XSSFSheet sheet = workbook.createSheet("A00" + rollNo++);

			// skip first row as it contains headers so start enter value from
			// second row onward
			int rowNum = 1;

			Row headerRow = sheet.createRow(0);
			for (int colNum = 0; colNum < header.size(); colNum++) {
				Cell cell = headerRow.createCell(colNum);
				cell.setCellValue(header.get(colNum));
			}

			List<Map> values = m.getValue();
			for (Map<String, Object> map : values) {
				Row row = sheet.createRow(rowNum++);

				for (int colNum = 0; colNum < header.size(); colNum++) {
					Cell cell = row.createCell(colNum);
					cell.setCellValue((String) map.get(header.get(colNum)));
				}
			}

			System.out.println("Done");
		}

		try {
			FileOutputStream outputStream = new FileOutputStream(fileName);
			workbook.write(outputStream);
			workbook.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}