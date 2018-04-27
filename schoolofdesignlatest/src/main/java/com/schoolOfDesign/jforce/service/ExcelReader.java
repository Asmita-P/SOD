package com.schoolOfDesign.jforce.service;

import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.controller.IceController;

@Service
public class ExcelReader {

	public static void main(String args[]) {
		String filePath = "G:\\file\\Student.xlsx";
		System.out.println(readXLSXFile(filePath));
	}

	static Logger log = LoggerFactory.getLogger(ExcelReader.class);

	public static List<Map<String, String>> readXLSXFile(String filePath) {
		List<Map<String, String>> mapList = new ArrayList<Map<String, String>>();
		InputStream excelToRead;
		try {
			XSSFRow row;
			XSSFCell cell;
			excelToRead = new FileInputStream(filePath);
			XSSFWorkbook wb = new XSSFWorkbook(excelToRead);
			XSSFSheet sheet = wb.getSheetAt(0);
			Row firstRow = sheet.getRow(0);
			int length = firstRow.getLastCellNum();

			Cell temp = null;
			List<String> header = new ArrayList<String>();

			for (int i = 0; i < length; i++) {
				temp = firstRow.getCell(i);
				header.add(temp.getStringCellValue());
			}

			Iterator rows = sheet.rowIterator();
			int rownum = 0;
			while (rows.hasNext()) {
				Map<String, String> valueMap = new HashMap();
				row = (XSSFRow) rows.next();
				int count = 0;
				if (rownum != 0) {
					Iterator cells = row.cellIterator();
					while (cells.hasNext()) {

						String cellValue = "";
						cell = (XSSFCell) cells.next();
						switch (cell.getCellType()) {
						case XSSFCell.CELL_TYPE_STRING:
							cellValue = cell.getStringCellValue();
							break;
						case XSSFCell.CELL_TYPE_NUMERIC:

							if (DateUtil.isCellDateFormatted(cell)) {
								SimpleDateFormat dateFormat = new SimpleDateFormat(
										"dd-MM-yyyy");
								cellValue = dateFormat.format(cell
										.getDateCellValue());
							} else
								cellValue = NumberToTextConverter.toText(cell
										.getNumericCellValue());
							break;
						case XSSFCell.CELL_TYPE_BOOLEAN:
							cellValue = cell.getBooleanCellValue() + "";
							break;
						}
						valueMap.put(header.get(count), cellValue);
						count++;
					}
				}
				rownum++;
				if (!valueMap.isEmpty())
					mapList.add(valueMap);

			}
		} catch (Exception e) {
			log.error("Exception", e);
		}
		log.info("Size" + mapList.size());
		return mapList;

	}
}
