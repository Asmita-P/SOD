package com.schoolOfDesign.jforce.service;

import java.awt.BasicStroke;
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.ICETEEConsolidated;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.extensions.XSSFCellBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.SubCategoryAxis;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.MinMaxCategoryRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.zeroturnaround.zip.ZipUtil;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.daos.reportdao.ReportSheetDao;
import com.schoolOfDesign.jforce.utils.Utils;

@Service
public class MarkSheetExcelService {

	@Autowired
	ReportSheetDao markSheetDao;

	@Autowired
	MarksService marksService;

	@Autowired
	private StudentCourseFacultyService studentCourseFacultyService;

	@Value("${tempDir}")
	private String tempDir;

	@Value("${icaFileTemplate1}")
	private String icaFileTemplate1;

	@Value("${icaFileTemplate2}")
	private String icaFileTemplate2;

	@Value("${teeFileTemplate1}")
	private String teeTemplate1;

	@Value("${teeFileTemplate2}")
	private String teeTemplate2;

	@Value("${teeMarkSheet1}")
	private String teeMarkSheet1;

	@Value("${teeMarkSheet2}")
	private String teeMarkSheet2;

	@Value("${newteeMarkSheet1}")
	private String newteeMarkSheet1;

	@Value("${newteeMarkSheet2}")
	private String newteeMarkSheet2;

	@Value("${classSheet}")
	private String classSheet;

	@Value("${fontPath}")
	private String fontPath;

	@Value("${teeIntExt}")
	private String teeIntExt;

	@Autowired
	private TEEService teeService;

	@Autowired
	private StudentService studentService;

	Logger log = LoggerFactory.getLogger(MarkSheetExcelService.class);

	public String getIcaReports() {
		String filePath = tempDir + File.separator + "IcaReports"
				+ Instant.now().toEpochMilli() + ".xlsx";
		List<Map> result = markSheetDao.getQueryResults("", "");
		List<Map<String, Object>> resultMap = new ArrayList();
		List<String> headers = new ArrayList();
		String h[] = { "SAPId", "First Name", "Last Name", "Course Name",
				"Assignment Id", "Assignment Name", "Criteria",
				"Criteria_weightage", "Criteria_wise_marks" };
		headers = Arrays.asList(h);
		int count = 0;
		for (Map<String, Object> m : result) {

			String sapId = m.get("sapId").toString();
			String icId = m.get("id").toString();
			Map<String, Object> rsMap = new LinkedHashMap<>();
			rsMap.put("SAPId", sapId);
			rsMap.put("First Name", m.get("fname"));
			rsMap.put("Last Name", m.get("lname"));
			rsMap.put("Course Name", m.get("courseName"));
			rsMap.put("Assignment Id", icId);
			rsMap.put("Assignment Name", m.get("assignmentName"));
			rsMap.put("Criteria", m.get("criteria"));
			rsMap.put("Criteria_weightage", m.get("criteria_weightage"));
			if (count == 0) {

				rsMap.put("Criteria_wise_marks", m.get("w1"));
				Map<String, Object> rs = new HashMap();
				rs.putAll(rsMap);
				resultMap.add(rs);

			} else if (count == 1) {
				rsMap.put("Criteria_wise_marks", m.get("w2"));
				Map<String, Object> rs = new HashMap();
				rs.putAll(rsMap);
				resultMap.add(rs);

			} else if (count == 2) {
				rsMap.put("Criteria_wise_marks", m.get("w3"));
				Map<String, Object> rs = new HashMap();
				rs.putAll(rsMap);
				resultMap.add(rs);

			} else if (count == 3) {
				rsMap.put("Criteria_wise_marks", m.get("w4"));
				Map<String, Object> rs = new HashMap();
				rs.putAll(rsMap);
				resultMap.add(rs);
				Map<String, Object> totalMap = new LinkedHashMap<>();
				totalMap.put("SAPId", "");
				totalMap.put("First Name", "");
				totalMap.put("Last Name", "");
				totalMap.put("Course Name", "");
				totalMap.put("Assignment Id", "");
				totalMap.put("Assignment Name", "");
				totalMap.put("Criteria", "");
				totalMap.put("Criteria_weightage", "Total");
				totalMap.put("Criteria_wise_marks", m.get("total"));
				resultMap.add(totalMap);
				count = -1;
			}
			count++;

		}
		ExcelCreater.CreateExcelFile(resultMap, headers, filePath);

		return filePath;

	}

	public String getIcaReportBulks(String currentAcadSession) {
		String filePath = tempDir + File.separator + "ICAReportBulks"
				+ Instant.now().toEpochMilli() + ".xlsx";

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}
		log.info("sapIdToRoll---------------------- " + sapIdToRoll);

		List<String> headers = new ArrayList();
		String h[] = { "Assigment", "Module Name", "ICA Name", "Criteria",
				"Criteria Weightage", "Criteria Marks", "Marks",
				"Module wise Total Marks", "Module wise %" };
		headers = Arrays.asList(h);
		int count = 0;
		String oldSapId = "";
		String oldCourseName = "";
		String oldIceName = "";
		String oldName = "";

		XSSFWorkbook workbook = new XSSFWorkbook();

		XSSFCellStyle totalStyle = workbook.createCellStyle();

		XSSFCellStyle totalRedStyle = workbook.createCellStyle();

		XSSFFont defaultFont = workbook.createFont();
		defaultFont.setFontHeightInPoints((short) 10);
		defaultFont.setFontName("Arial");
		defaultFont.setColor(IndexedColors.BLACK.getIndex());
		defaultFont.setBold(true);
		defaultFont.setItalic(false);
		totalStyle.setFont(defaultFont);
		totalRedStyle.setFont(defaultFont);

		XSSFCellStyle style = workbook.createCellStyle();

		style.setBorderBottom(CellStyle.BORDER_THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(CellStyle.BORDER_THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderRight(CellStyle.BORDER_THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		// style.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());

		XSSFCellStyle headerStyle = workbook.createCellStyle();

		headerStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
		headerStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		headerStyle.setFont(defaultFont);

		XSSFColor grayColor = new XSSFColor(new Color(211, 211, 211));
		totalStyle.setFillBackgroundColor(grayColor);

		XSSFColor redColor = new XSSFColor(new Color(255, 0, 0));
		totalRedStyle.setFillBackgroundColor(redColor);

		XSSFCellStyle borderStyle = workbook.createCellStyle();
		XSSFColor color = new XSSFColor(new java.awt.Color(0, 0, 0));
		borderStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
		borderStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
		borderStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
		borderStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);

		totalStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
		totalStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
		totalStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
		totalStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
		totalStyle.setFillPattern(FillPatternType.LESS_DOTS);

		totalRedStyle.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
		totalRedStyle.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
		totalRedStyle.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
		totalRedStyle.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);
		totalRedStyle.setFillPattern(FillPatternType.LESS_DOTS);

		style.setBorderColor(XSSFCellBorder.BorderSide.BOTTOM, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.TOP, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.LEFT, color);
		style.setBorderColor(XSSFCellBorder.BorderSide.RIGHT, color);

		CellStyle centerStyle = workbook.createCellStyle();
		centerStyle.setAlignment(CellStyle.ALIGN_CENTER);
		// style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		// int rollNo = 1;

		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();

			int rowNum = 0;
			double totalCourseWise = 0;
			double totalWise = 0;
			int assigmentNo = 0;
			double overAllScore = 0;
			double overAllTotal = 0;
			log.info("SAPID - " + sapId);
			log.info("sapIdToRoll----------"+sapIdToRoll);

			XSSFSheet sheet = workbook.getSheet(sapIdToRoll.get(sapId));

			if (sheet == null) {
				overAllScore = 0;
				overAllTotal = 0;
				assigmentNo = 0;
				sheet = workbook.createSheet(sapIdToRoll.get(sapId));

				sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

				sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 8));

				sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 8));

				Row collegeNameRow = sheet.createRow(rowNum++);
				Row programRow = sheet.createRow(rowNum++);
				Row reviewRow = sheet.createRow(rowNum++);

				Cell collegeCell = collegeNameRow.createCell(0);
				collegeCell.setCellValue("SVKM's NMIMS SCHOOL OF DESIGN	");

				Cell programCell = programRow.createCell(0);
				programCell
						.setCellValue("Bachelor of Design (Humanising Technology) Year I Semester I  2017-21");

				Cell reviewCell = reviewRow.createCell(0);
				LocalDateTime now = LocalDateTime.now();

				DateTimeFormatter formatter = DateTimeFormatter
						.ofPattern("yyyy-MM-dd HH:mm:ss");

				String formatDateTime = now.format(formatter);

				reviewCell.setCellValue("Review	report on  " + formatDateTime);

				collegeCell.setCellStyle(centerStyle);
				reviewCell.setCellStyle(centerStyle);
				programCell.setCellStyle(centerStyle);

				Row rollNoRow = sheet.createRow(rowNum++);

				rollNoRow.createCell(0).setCellValue("Roll No");
				rollNoRow.createCell(1).setCellValue(sapIdToRoll.get(sapId));

				Row sapIdRow = sheet.createRow(rowNum++);

				sapIdRow.createCell(0).setCellValue("SAP Id");
				sapIdRow.createCell(1).setCellValue(sapId);

				String nameOnSheet = Utils.getBlankIfNull(stud.get("fname"))
						+ Utils.getBlankIfNull(stud.get("lname"));
				Row nameRow = sheet.createRow(rowNum++);
				nameRow.createCell(0).setCellValue("Name");
				nameRow.createCell(1).setCellValue(nameOnSheet);

				Row headerRow = sheet.createRow(rowNum++);

				Row row = sheet.createRow(rowNum++);
				row.setRowStyle(borderStyle);
				for (int colNum = 0; colNum < headers.size(); colNum++) {

					Cell cell = headerRow.createCell(colNum);
					cell.setCellValue(headers.get(colNum));
					cell.setCellStyle(headerStyle);
				}
			}

			List<Map> results = markSheetDao
					.getQueryResults(Utils.getBlankIfNull(stud.get("sapId")),
							currentAcadSession);
			log.info("results sapId" + results);

			for (Map<String, Object> m : results) {

				String courseName = Utils.getBlankIfNull(m.get("courseName"));
				String iceName = Utils.getBlankIfNull(m.get("assignmentName"));

				if (!oldCourseName.equals(courseName)
						&& !oldCourseName.isEmpty()) {
					Row row = sheet.createRow(++rowNum);
					int colNum = 0;

					for (int i = 0; i < 6; i++) {
						Cell c = row.createCell(colNum++);
						c.setCellValue("");
						c.setCellStyle(borderStyle);
					}

					if (totalWise != 0) {
						Cell score = row.createCell(colNum++);
						score.setCellValue(doublePrecisionFormatter
								.format(totalCourseWise) + "");
						score.setCellStyle(totalStyle);
						Cell total = row.createCell(colNum++);
						total.setCellStyle(totalStyle);
						total.setCellValue(doublePrecisionFormatter
								.format(totalWise) + "");
						Cell over = row.createCell(colNum++);

						double percent = ((totalCourseWise / totalWise) * 100);
						over.setCellValue(doublePrecisionFormatter
								.format(percent) + "");
						if (percent < 50) {
							over.setCellStyle(totalRedStyle);
						} else
							over.setCellStyle(totalStyle);
						totalCourseWise = 0;
						totalWise = 0;
					}

				}

				Row row = sheet.createRow(++rowNum);

				int colNum = 0;

				if (oldIceName.isEmpty() || !oldIceName.equals(iceName))
					row.createCell(colNum++).setCellValue(
							"Assigment " + (++assigmentNo));
				else
					row.createCell(colNum++).setCellValue("");

				String name = Utils.getBlankIfNull(m.get("fname"))
						+ Utils.getBlankIfNull(m.get("lname"));
				if (oldName.isEmpty() || !name.equals(oldName)) {

					// row.createCell(colNum++).setCellValue(
					// Utils.getBlankIfNull(m.get("fname")));
					// row.createCell(colNum++).setCellValue(
					// Utils.getBlankIfNull(m.get("lname")));
				} else {
					// row.createCell(colNum++).setCellValue("");
					// row.createCell(colNum++).setCellValue("");
				}
				if (oldCourseName.isEmpty()
						|| !oldCourseName.equals(courseName)) {
					row.createCell(colNum++).setCellValue(courseName);

				} else
					row.createCell(colNum++).setCellValue("");
				if (oldIceName.isEmpty() || !oldIceName.equals(iceName)) {
					row.createCell(colNum++).setCellValue(iceName);
				} else {
					row.createCell(colNum++).setCellValue("");
				}

				row.createCell(colNum++).setCellValue(
						Utils.getBlankIfNull(m.get("criteria")));
				row.createCell(colNum++).setCellValue(
						Utils.getBlankIfNull(m.get("criteria_weightage")));

				totalWise = totalWise
						+ Double.valueOf(Utils.getBlankIfNull(m
								.get("criteria_weightage")));

				if (count == 0) {
					String w1 = Utils.getBlankIfNull(m.get("w1"));
					if (w1.isEmpty())
						row.createCell(colNum++).setCellValue(w1);
					else {
						row.createCell(colNum++).setCellValue(
								doublePrecisionFormatter.format(Double
										.valueOf(w1)));
						totalCourseWise = totalCourseWise + Double.valueOf(w1);
					}

				} else if (count == 1) {
					String w2 = Utils.getBlankIfNull(m.get("w2"));
					if (w2.isEmpty())
						row.createCell(colNum++).setCellValue(w2);
					else {
						row.createCell(colNum++).setCellValue(
								doublePrecisionFormatter.format(Double
										.valueOf(w2)));
						totalCourseWise = totalCourseWise + Double.valueOf(w2);
					}

				} else if (count == 2) {
					String w3 = Utils.getBlankIfNull(m.get("w3"));
					if (w3.isEmpty())
						row.createCell(colNum++).setCellValue(w3);
					else {
						row.createCell(colNum++).setCellValue(
								doublePrecisionFormatter.format(Double
										.valueOf(w3)));
						totalCourseWise = totalCourseWise + Double.valueOf(w3);
					}

				} else if (count == 3) {
					String w4 = Utils.getBlankIfNull(m.get("w4"));
					if (w4.isEmpty())
						row.createCell(colNum++).setCellValue(w4);
					else {
						row.createCell(colNum++).setCellValue(
								doublePrecisionFormatter.format(Double
										.valueOf(w4)));
						totalCourseWise = totalCourseWise + Double.valueOf(w4);
					}

					Row ttrow = sheet.createRow(++rowNum);
					colNum = 0;
					ttrow.createCell(colNum++).setCellValue("");
					ttrow.createCell(colNum++).setCellValue("");
					ttrow.createCell(colNum++).setCellValue("");
					ttrow.createCell(colNum++).setCellValue("");

					// row.createCell(colNum++).setCellValue("");
					// row.createCell(colNum++).setCellValue("");
					Cell c = ttrow.createCell(colNum++);
					c.setCellValue("Total");
					c.setCellStyle(totalStyle);

					Cell t1 = ttrow.createCell(colNum++);
					String tt = Utils.getBlankIfNull(m.get("total"));
					if (tt.isEmpty())
						t1.setCellValue("");
					else
						t1.setCellValue(doublePrecisionFormatter.format(Double
								.valueOf(tt)));

					overAllScore = overAllScore
							+ Double.valueOf(Utils.getBlankIfNull(m
									.get("total")));
					overAllTotal = overAllTotal + 100;
					t1.setCellStyle(totalStyle);

					count = -1;
				}
				count++;
				oldSapId = sapId;
				oldCourseName = courseName;
				oldIceName = iceName;
				oldName = name;
			}

			Row row = sheet.createRow(++rowNum);
			int colNum = 0;

			for (int i = 0; i < 6; i++) {
				Cell c = row.createCell(colNum++);
				c.setCellValue("");
				c.setCellStyle(borderStyle);
			}
			/*
			 * row.createCell(colNum++).setCellValue("");
			 * row.createCell(colNum++).setCellValue("");
			 * row.createCell(colNum++).setCellValue("");
			 * 
			 * row.createCell(colNum++).setCellValue("");
			 * row.createCell(colNum++).setCellValue("");
			 * row.createCell(colNum++).setCellValue("");
			 * row.createCell(colNum++).setCellValue("");
			 */
			Cell score = row.createCell(colNum++);
			score.setCellValue(doublePrecisionFormatter.format(totalCourseWise)
					+ "");
			score.setCellStyle(totalStyle);
			Cell total = row.createCell(colNum++);
			total.setCellStyle(totalStyle);
			total.setCellValue(doublePrecisionFormatter.format(totalWise) + "");
			Cell over = row.createCell(colNum++);
			over.setCellValue((doublePrecisionFormatter
					.format((totalCourseWise / totalWise) * 100)) + "");
			over.setCellStyle(totalStyle);
			totalCourseWise = 0;
			totalWise = 0;
			colNum = 0;
			Row gr = sheet.createRow(++rowNum);

			for (int i = 0; i < 4; i++) {
				Cell c = gr.createCell(colNum++);
				c.setCellValue("");
				c.setCellStyle(borderStyle);
			}
			Cell overCell = gr.createCell(colNum++);
			overCell.setCellValue("Overall");
			overCell.setCellStyle(totalStyle);
			gr.createCell(colNum++).setCellValue("");
			Cell os = gr.createCell(colNum++);
			os.setCellValue(doublePrecisionFormatter.format(overAllScore) + "");
			os.setCellStyle(totalStyle);
			Cell ot = gr.createCell(colNum++);
			ot.setCellValue(doublePrecisionFormatter.format(overAllTotal) + "");
			ot.setCellStyle(totalStyle);
			Cell per = gr.createCell(colNum++);

			double percent = (overAllScore / overAllTotal) * 100;
			per.setCellValue(doublePrecisionFormatter.format(percent) + "");
			if (percent < 50) {
				per.setCellStyle(totalRedStyle);
			} else
				per.setCellStyle(totalStyle);
			per.setCellValue(doublePrecisionFormatter.format(percent) + "");

			// rollNo++;

		}

		try {
			FileOutputStream outputStream = new FileOutputStream(filePath);
			workbook.write(outputStream);
			workbook.close();
		} catch (Exception e) {
			log.error("Exception ", e);
		}

		return filePath;

	}

	static DecimalFormat doublePrecisionFormatter = new DecimalFormat("###.##");

	public String getAttributeMap(String currentAcadSession) {

		File dir = new File(tempDir + File.separator + "AttributeMapForICA"
				+ Instant.now().toEpochMilli());

		String zippedPath = dir.getAbsolutePath() + ".zip";
		dir.mkdir();

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		log.info("students---------------------------------" + students);

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}

		// int rollNo = 1;
		if (students.size() != 0 || !students.isEmpty()) {
			for (Map<String, Object> stud : students) {
				String sapId = stud.get("sapId").toString();
				String name = Utils.getBlankIfNull(stud.get("fname")) + " "
						+ Utils.getBlankIfNull(stud.get("lname"));
				int count = 0;

				List<Map> results = markSheetDao.getQueryResults(
						Utils.getBlankIfNull(stud.get("sapId")),
						currentAcadSession);
				log.info("results sapId" + results);
				Map<String, Map<String, Double>> studMapping = new HashMap<String, Map<String, Double>>();

				for (Map<String, Object> m : results) {

					String mapping = Utils
							.getBlankIfNull(m.get("mapping_desc"));
					String weightage = Utils.getBlankIfNull(m
							.get("criteria_weightage"));

					Map<String, Double> valueMap = null;
					if (studMapping.containsKey(mapping)) {

						Map<String, Double> mapper = studMapping.get(mapping);

						if (mapper == null) {
							mapper = new HashMap<String, Double>();
							mapper.put("total", Double.valueOf(weightage));
						} else if (mapper.containsKey("total"))
							mapper.put(
									"total",
									mapper.get("total")
											+ Double.valueOf(weightage));
						else
							mapper.put("total", Double.valueOf(weightage));
						studMapping.put(mapping, mapper);
					} else {
						Map<String, Double> mapper = new HashMap<String, Double>();
						mapper.put("total", Double.valueOf(weightage));
						studMapping.put(mapping, mapper);
					}
					double scored = 0;
					if (count == 0) {
						scored = Double.valueOf(Utils.getBlankIfNull(m
								.get("w1")));

					} else if (count == 1) {
						scored = Double.valueOf(Utils.getBlankIfNull(m
								.get("w2")));

					} else if (count == 2) {
						scored = Double.valueOf(Utils.getBlankIfNull(m
								.get("w3")));

					} else if (count == 3) {

						scored = Double.valueOf(Utils.getBlankIfNull(m
								.get("w4")));
						count = -1;
					}

					if (studMapping.containsKey(mapping)) {

						Map<String, Double> mapper = studMapping.get(mapping);
						if (mapper == null) {
							mapper = new HashMap<String, Double>();
							mapper.put("scored", Double.valueOf(scored));
						} else if (mapper.containsKey("scored"))
							mapper.put(
									"scored",
									mapper.get("scored")
											+ Double.valueOf(scored));
						else
							mapper.put("scored", Double.valueOf(scored));

						studMapping.put(mapping, mapper);
					} else {
						Map<String, Double> mapper = new HashMap<String, Double>();
						mapper.put("scored", Double.valueOf(scored));
						studMapping.put(mapping, mapper);
					}
					count++;

				}

				String rNo = sapIdToRoll.get(sapId);// "A00" + rollNo++;
				log.info("Value" + studMapping);
				String toCopy = getIcaMappingPdf(rNo, sapId, name, studMapping);
				File rNoFile = new File(dir.getAbsolutePath() + File.separator
						+ rNo + ".pdf");
				try {
					FileUtils.copyFile(new File(toCopy), rNoFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					log.error("Exception", e);
				}

			}

			ZipUtil.pack(dir, new File(zippedPath));
			return zippedPath;
		} else {

		}
		return zippedPath;

	}

	public String getAttributeMapForTee(String currentAcadSession) {

		File dir = new File(tempDir + File.separator + "AttributeMapForTee"
				+ Instant.now().toEpochMilli());

		String zippedPath = dir.getAbsolutePath() + ".zip";
		dir.mkdir();

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}

		// int rollNo = 1;
		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();
			String name = Utils.getBlankIfNull(stud.get("fname")) + " "
					+ Utils.getBlankIfNull(stud.get("lname"));
			int count = 0;

			List<Map> results = markSheetDao
					.getQueryResultsForTee(
							Utils.getBlankIfNull(stud.get("sapId")),
							currentAcadSession);
			log.info("results sapId" + results);
			Map<String, Map<String, Double>> studMapping = new HashMap<String, Map<String, Double>>();

			for (Map<String, Object> m : results) {

				String mapping = Utils.getBlankIfNull(m.get("mapping_desc"));
				String weightage = Utils.getBlankIfNull(m
						.get("criteria_weightage"));

				Map<String, Double> valueMap = null;
				if (studMapping.containsKey(mapping)) {

					Map<String, Double> mapper = studMapping.get(mapping);

					if (mapper == null) {
						mapper = new HashMap<String, Double>();
						mapper.put("total", Double.valueOf(weightage));
					} else if (mapper.containsKey("total"))
						mapper.put("total",
								mapper.get("total") + Double.valueOf(weightage));
					else
						mapper.put("total", Double.valueOf(weightage));
					studMapping.put(mapping, mapper);
				} else {
					Map<String, Double> mapper = new HashMap<String, Double>();
					mapper.put("total", Double.valueOf(weightage));
					studMapping.put(mapping, mapper);
				}
				double scored = 0;
				if (count == 0) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w1")));

				} else if (count == 1) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w2")));

				} else if (count == 2) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w3")));

				} else if (count == 3) {

					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w4")));
					count = -1;
				}

				if (studMapping.containsKey(mapping)) {

					Map<String, Double> mapper = studMapping.get(mapping);
					if (mapper == null) {
						mapper = new HashMap<String, Double>();
						mapper.put("scored", Double.valueOf(scored));
					} else if (mapper.containsKey("scored"))
						mapper.put("scored",
								mapper.get("scored") + Double.valueOf(scored));
					else
						mapper.put("scored", Double.valueOf(scored));

					studMapping.put(mapping, mapper);
				} else {
					Map<String, Double> mapper = new HashMap<String, Double>();
					mapper.put("scored", Double.valueOf(scored));
					studMapping.put(mapping, mapper);
				}
				count++;

			}

			String rNo = sapIdToRoll.get(sapId);// "A00" + rollNo++;
			log.info("Value" + studMapping);
			String toCopy = getIcaMappingPdf(rNo, sapId, name, studMapping);
			File rNoFile = new File(dir.getAbsolutePath() + File.separator
					+ rNo + ".pdf");
			try {
				FileUtils.copyFile(new File(toCopy), rNoFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Exception", e);
			}

		}

		ZipUtil.pack(dir, new File(zippedPath));

		return zippedPath;

	}

	public String getStudentWiseFinalReport(String currentAcadSession) {

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}

		File dir = new File(tempDir + File.separator + "StudentWise"
				+ Instant.now().toEpochMilli());

		String zippedPath = dir.getAbsolutePath() + ".zip";
		dir.mkdir();

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		Map<String, List<ICETEEConsolidated>> totalList = marksService
				.getMapOfAllScores(currentAcadSession.trim());
		int rollNo = 1;
		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();
			String name = Utils.getBlankIfNull(stud.get("fname")) + " "
					+ Utils.getBlankIfNull(stud.get("lname"));

			String rNo = sapIdToRoll.get(sapId);
			List<ICETEEConsolidated> marksList = totalList.get(sapId);

			String toCopy = getTotalPdf(rNo, sapId, name, marksList);

			File rNoFile = new File(dir.getAbsolutePath() + File.separator
					+ rNo + ".pdf");
			try {
				FileUtils.copyFile(new File(toCopy), rNoFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Exception", e);
			}

		}

		ZipUtil.pack(dir, new File(zippedPath));

		return zippedPath;

	}

	public String getOverall(String currentAcadSession, String courseId,
			String courseName) {

		String path = "";
		int size = 0;

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}
		Map<String, Map<String, Map<String, Double>>> studMap = new LinkedHashMap<String, Map<String, Map<String, Double>>>();

		int rollNo = 1;
		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();
			String name = Utils.getBlankIfNull(stud.get("fname")) + " "
					+ Utils.getBlankIfNull(stud.get("lname"));
			int count = 0;

			List<Map> results = markSheetDao.getQueryResultsCourse(
					Utils.getBlankIfNull(stud.get("sapId")), courseId,
					currentAcadSession);
			log.info("results sapId" + results);
			Map<String, Map<String, Double>> studMapping = new HashMap<String, Map<String, Double>>();

			for (Map<String, Object> m : results) {

				String mapping = Utils.getBlankIfNull(m.get("mapping_desc"));
				String weightage = Utils.getBlankIfNull(m
						.get("criteria_weightage"));

				Map<String, Double> valueMap = null;
				if (studMapping.containsKey(mapping)) {

					Map<String, Double> mapper = studMapping.get(mapping);

					if (mapper == null) {
						mapper = new HashMap<String, Double>();
						mapper.put("total", Double.valueOf(weightage));
					} else if (mapper.containsKey("total"))
						mapper.put("total",
								mapper.get("total") + Double.valueOf(weightage));
					else
						mapper.put("total", Double.valueOf(weightage));
					studMapping.put(mapping, mapper);
				} else {
					Map<String, Double> mapper = new HashMap<String, Double>();
					mapper.put("total", Double.valueOf(weightage));
					studMapping.put(mapping, mapper);
				}
				double scored = 0;
				if (count == 0) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w1")));

				} else if (count == 1) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w2")));

				} else if (count == 2) {
					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w3")));

				} else if (count == 3) {

					scored = Double.valueOf(Utils.getBlankIfNull(m.get("w4")));
					count = -1;
				}

				if (studMapping.containsKey(mapping)) {

					Map<String, Double> mapper = studMapping.get(mapping);
					if (mapper == null) {
						mapper = new HashMap<String, Double>();
						mapper.put("scored", Double.valueOf(scored));
					} else if (mapper.containsKey("scored"))
						mapper.put("scored",
								mapper.get("scored") + Double.valueOf(scored));
					else
						mapper.put("scored", Double.valueOf(scored));

					studMapping.put(mapping, mapper);
				} else {
					Map<String, Double> mapper = new HashMap<String, Double>();
					mapper.put("scored", Double.valueOf(scored));
					studMapping.put(mapping, mapper);
				}
				count++;

			}

			String rNo = sapIdToRoll.get(sapId);// rollNo <= 9 ? "A00" +
												// rollNo++ : "A0" + rollNo++;
			log.info("Value" + studMapping);
			size = studMapping.size();
			studMap.put(rNo + "~" + sapId + "~" + name, studMapping);

		}

		path = getMappingPdf(studMap, size, courseName);
		return path;

	}

	public String getIcaReportBulksPdf(String currentAcadSession) {

		List<Map> students = markSheetDao
				.getStudents(currentAcadSession.trim());

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}

		File dir = new File(tempDir + File.separator + "IcaReportBulk"
				+ Instant.now().toEpochMilli());

		String zippedPath = dir.getAbsolutePath() + ".zip";
		dir.mkdir();

		// style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		int rollNo = 1;

		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();

			String name = Utils.getBlankIfNull(stud.get("fname")) + " "
					+ Utils.getBlankIfNull(stud.get("lname"));

			List<Map> results = markSheetDao.getIcaTotalCourseWise(
					Utils.getBlankIfNull(sapId), currentAcadSession);
			log.info("results sapId" + results);
			Map<String, List<Map<String, String>>> marksMap = new HashMap();

			for (Map<String, Object> m : results) {

				String courseName = Utils.getBlankIfNull(m.get("courseName"));
				String iceName = Utils.getBlankIfNull(m.get("assignmentName"));
				String iceTotal = Utils.getBlankIfNull(m.get("total"));

				if (marksMap.containsKey(courseName)) {
					Map<String, String> value = new HashMap();
					value.put("assignmentName", iceName);
					value.put("total", iceTotal);

					List r = marksMap.get(courseName);
					r.add(value);

				} else {
					Map<String, String> value = new HashMap();
					value.put("assignmentName", iceName);
					value.put("total", iceTotal);
					List r = new ArrayList();
					r.add(value);
					marksMap.put(courseName, r);
				}

			}
			String rNo = sapIdToRoll.get(sapId);
			// rollNo <= 9 ? "A00" + rollNo++ : "A0" + rollNo++;
			String toCopy = getIcaPdf(rNo, sapId, name, marksMap);
			File rNoFile = new File(dir.getAbsolutePath() + File.separator
					+ rNo + ".pdf");
			try {
				FileUtils.copyFile(new File(toCopy), rNoFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				log.error("Exception", e);
			}

			// end of results loop
		}

		ZipUtil.pack(dir, new File(zippedPath));

		return zippedPath;

	}

	public String getMappingDescReports() {
		String filePath = tempDir + File.separator + "MappingReport"
				+ Instant.now().toEpochMilli() + ".xlsx";
		List<Map> result = markSheetDao.getQueryResultsTotal();
		List<Map<String, Object>> resultMap = new ArrayList();
		List<String> headers = new ArrayList();

		Map<String, Map<String, Object>> studentRecords = new HashMap();

		int count = 0;
		for (Map<String, Object> m : result) {

			String sapId = m.get("sapId").toString();
			String icId = m.get("id").toString();
			Map<String, Object> rsMap = new LinkedHashMap<>();
			Map<String, Object> sr = null;
			sr = studentRecords.get(sapId);
			if (sr == null) {
				sr = new HashMap();
				sr.put("SAPId", sapId);
				sr.put("First Name", m.get("fname"));
				sr.put("Last Name", m.get("lname"));

			}
			String mapping = Utils.getBlankIfNull(m.get("mapping"));
			String cweightage = Utils.getBlankIfNull(m
					.get("criteria_weightage"));

			if (!mapping.isEmpty()) {
				String mpName = "Mapper" + mapping;
				if (!sr.containsKey(mpName)) {
					sr.put(mpName, mpName);
					/*
					 * if (!headers.contains(mpName)) headers.add(mpName);
					 */
				}

				String scored = mapping + "Scored";
				String outOff = mapping + "Out Of";

				/*
				 * if (!headers.contains(scored)) { headers.add(scored); }
				 * 
				 * if (!headers.contains(outOff)) { headers.add(outOff); }
				 */

				if (!sr.containsKey(outOff)) {
					sr.put(outOff, cweightage);
				} else {
					double total = Double.valueOf(cweightage)
							+ Double.valueOf(sr.get(outOff).toString());
					sr.put(outOff, total + "");
				}

				if (count == 0) {

					String obtained = Utils.getBlankIfNull(m.get("w1"));

					log.info("obtained-----" + obtained);
					if (!sr.containsKey(scored)) {
						sr.put(scored, obtained);

					} else {
						if (obtained.isEmpty()) {
							double scoredTotal = Double.valueOf(0)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						} else {
							double scoredTotal = Double.valueOf(obtained)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						}

					}

				} else if (count == 1) {

					String obtained = Utils.getBlankIfNull(m.get("w2"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						if (obtained.isEmpty()) {
							double scoredTotal = Double.valueOf(0)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						} else {
							double scoredTotal = Double.valueOf(obtained)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						}

					}

				} else if (count == 2) {

					String obtained = Utils.getBlankIfNull(m.get("w3"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						if (obtained.isEmpty()) {
							double scoredTotal = Double.valueOf(0)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						} else {
							double scoredTotal = Double.valueOf(obtained)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						}

					}

				} else if (count == 3) {

					String obtained = Utils.getBlankIfNull(m.get("w4"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						if (obtained.isEmpty()) {
							double scoredTotal = Double.valueOf(0)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						} else {
							double scoredTotal = Double.valueOf(obtained)
									+ Double.valueOf(sr.get(scored).toString());
							sr.put(scored, scoredTotal + "");
						}

					}

					count = -1;
				}
				count++;
				studentRecords.put(sapId, sr);

			}
		}
		headers.add("SAPId");
		headers.add("First Name");
		headers.add("Last Name");
		headers.add("Mapping Description");
		headers.add("Student Score");
		headers.add("Total  Score(OutOff)");
		headers.add("Percentage");
		for (Map.Entry<String, Map<String, Object>> m : studentRecords
				.entrySet()) {

			Map<String, Object> res = m.getValue();

			Set<String> keys = res.keySet();
			List<String> mappers = new ArrayList();
			for (String k : keys) {
				if (k.indexOf("Mapper") != -1) {
					mappers.add(k);
				}

			}
			List<String> sapIds = new ArrayList<String>();

			for (String k : mappers) {
				Map<String, Object> rs = new HashMap();
				String id = res.get("SAPId").toString();
				if (!sapIds.contains(id)) {
					rs.put("SAPId", id);
					rs.put("First Name", res.get("First Name"));
					rs.put("Last Name", res.get("Last Name"));
					sapIds.add(id);
				} else {
					rs.put("SAPId", "");
					rs.put("First Name", "");
					rs.put("Last Name", "");
				}
				String mapping = StringUtils.replace(k, "Mapper", "");
				String scored = mapping + "Scored";
				String outOff = mapping + "Out Of";
				String score = Utils.getBlankIfNull(res.get(scored));
				String out = Utils.getBlankIfNull(res.get(outOff));
				rs.put("Mapping Description", mapping);
				rs.put("Student Score", score);
				rs.put("Total  Score(OutOff)", out);
				if (!score.isEmpty() && !out.isEmpty())
					rs.put("Percentage",
							(Math.round((Double.valueOf(score) / Double
									.valueOf(out)) * 100)) + "");
				resultMap.add(rs);

			}

		}
		ExcelCreater.CreateExcelFile(resultMap, headers, filePath);

		return filePath;

	}

	public String getIcaSessionAndCourseWise(String session, String courseId,
			CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		log.info("Session - " + session);
		FileOutputStream fileOut = null;
		XSSFWorkbook wb = null;
		try {
			if (session.equalsIgnoreCase("Semester I")) {
				wb = new XSSFWorkbook(new FileInputStream(icaFileTemplate1));
			}
			if (session.equalsIgnoreCase("Semester II")) {
				wb = new XSSFWorkbook(new FileInputStream(icaFileTemplate2));
			}

			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("ica");
			XSSFRow secondRow = template.getRow(1);

			secondRow.getCell(0).setCellValue(year);

			secondRow.getCell(2).setCellValue(courseBean.getModule_abbr());

			XSSFRow thirdRow = template.getRow(2);
			Double icaSplit = Double.valueOf(courseBean.getIca_tee_split());
			thirdRow.getCell(3).setCellValue(
					"S0" + courseBean.getIca_tee_split());
			thirdRow.getCell(4).setCellValue(
					"S0" + courseBean.getIca_tee_split());

			int i = 4;

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.mapOfSapIdAndCourseId(courseId);
			List<ICETEEConsolidated> courseWiseTeeAndIca = marksService
					.getAllRecordsForDashboardBasedOnCourseId(
							mapOfSapIdAndCourseId, courseId);

			for (ICETEEConsolidated total : courseWiseTeeAndIca) {
				log.info("SAPID----" + total.getSapId() + " ICA Total-----"
						+ total.getIceTotal() + " ICA WTotal-------"
						+ total.getIceWeightedTotal());
				log.info("total bean ------------------------------ "
						+ total.toString());

				double v = Math.ceil(((Double.valueOf(total
						.getIceWeightedTotal()) / 100) * icaSplit));

				XSSFRow row = template.getRow(i);
				row.getCell(2).setCellValue(total.getSapId());
				double totalIca = Math.round(Double.parseDouble(total
						.getIceWeightedTotal()));
				row.getCell(3).setCellValue(v);
				i++;

			}

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);
		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		return filePath;

	}

	public String getTeeSessionAndCourseWise(String session, String courseId,
			CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		FileOutputStream fileOut = null;
		XSSFWorkbook wb = null;
		try {
			if (session.equalsIgnoreCase("Semester I")) {
				log.info("Inside getTeeSessionAndCourseWise " + teeTemplate1);
				wb = new XSSFWorkbook(new FileInputStream(teeTemplate1));
			} else {
				wb = new XSSFWorkbook(new FileInputStream(teeTemplate2));
			}
			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("tee");
			XSSFRow secondRow = template.getRow(1);

			secondRow.getCell(0).setCellValue(year);

			secondRow.getCell(2).setCellValue(courseBean.getModule_abbr());

			XSSFRow thirdRow = template.getRow(2);
			Integer icaSplit = Integer.valueOf(courseBean.getIca_tee_split());
			Integer teeSplit = 100 - icaSplit;
			thirdRow.getCell(3).setCellValue("S0" + teeSplit);
			thirdRow.getCell(4).setCellValue("S0" + teeSplit);

			int i = 4;

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.mapOfSapIdAndCourseId(courseId);
			List<ICETEEConsolidated> courseWiseTeeAndIca = marksService
					.getAllRecordsForDashboardBasedOnCourseId(
							mapOfSapIdAndCourseId, courseId);

			for (ICETEEConsolidated total : courseWiseTeeAndIca) {

				XSSFRow row = template.getRow(i);
				row.getCell(2).setCellValue(total.getSapId());
				if (total != null && total.getTeeWeightedTotal() != null) {
					log.info("total" + total.getTeeWeightedTotal());
					try {
						double v = Math.ceil(((Double.valueOf(total
								.getTeeWeightedTotal()) / 100) * teeSplit));

						log.info("TEE  calculated" + v);

						row.getCell(3).setCellValue(v);
					} catch (Exception e) {
						log.error("Exception", e);
					}
				}
				i++;

			}

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);

		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		log.info("Done  getTeeSessionAndCourseWise " + filePath);

		return filePath;

	}

	public String getClassWise(String session, String courseId,
			CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator
				+ "ConsolidatedClassWiseReportFor"
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		FileOutputStream fileOut = null;

		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}
		try {
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(classSheet));
			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("classwise");
			XSSFRow secondRow = template.getRow(1);

			secondRow.getCell(0).setCellValue(
					"Bachelor of Design (Humanising Technology) Year " + session + "   " + year);

			XSSFRow courseRow = template.getRow(2);

			courseRow.getCell(0).setCellValue(
					courseBean.getCourse_name() + "   Report");

			XSSFRow seventhRow = template.getRow(7);
			Integer icaSplit = Integer.valueOf(courseBean.getIca_tee_split());
			Integer teeSplit = 100 - icaSplit;
			seventhRow.getCell(4).setCellValue("ICA out /" + icaSplit);
			seventhRow.getCell(5).setCellValue("Term End out/" + teeSplit);

			LocalDateTime now = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");

			String formatDateTime = now.format(formatter);

			template.getRow(4).getCell(7).setCellValue(formatDateTime);

			int i = 9;

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.mapOfSapIdAndCourseId(courseId);
			List<ICETEEConsolidated> courseWiseTeeAndIca = marksService
					.getAllRecordsForDashboardBasedOnCourseId(
							mapOfSapIdAndCourseId, courseId);
			int count = 1;
			for (ICETEEConsolidated total : courseWiseTeeAndIca) {

				XSSFRow row = template.getRow(i);
				/*
				 * if (count <= 9) row.getCell(1).setCellValue("A00" + count++);
				 * else
				 */
				row.getCell(1).setCellValue(sapIdToRoll.get(total.getSapId()));
				row.getCell(2).setCellValue(total.getSapId());
				row.getCell(3).setCellValue(total.getFirstName());
				double icaTotal = (Double.valueOf(total.getIceWeightedTotal()) / 100)
						* icaSplit;
				Double teeTotal = null;
				double result;
				if (total.getTeeWeightedTotal() != null) {
					teeTotal = (Double.valueOf(total.getTeeWeightedTotal()) / 100)
							* teeSplit;
					result = teeTotal == null ? icaTotal : icaTotal + teeTotal;
				} else {

					result = icaTotal;
				}
				row.getCell(4).setCellValue((Math.ceil(icaTotal)));
				if (teeTotal != null) {
					row.getCell(6).setCellValue((Math.ceil(teeTotal)));
				} else {
					row.getCell(6).setCellValue("");
				}
				// row.getCell(6).setCellValue((Math.ceil(result)));
				row.getCell(7).setCellValue((Math.ceil(result)));
				i++;

			}

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);
		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		return filePath;

	}

	public String getTeeMarksheet(String session, String courseId,
			CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator + "TeeMarkSheet"
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		List<Map<String, Object>> resultList = markSheetDao.getJdbcTemplate()
				.queryForList("select * from sap_roll where active='Y'");
		Map<String, String> sapIdToRoll = new HashMap<String, String>();
		if (resultList != null) {
			for (Map<String, Object> m : resultList)
				sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
		}
		FileOutputStream fileOut = null;
		try {
			XSSFWorkbook wb = null;
			log.info("sapIdToRoll------------------- " + sapIdToRoll);

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.getStudentsForCouse(courseId);
			if (session.equalsIgnoreCase("Semester I")) {
				wb = new XSSFWorkbook(new FileInputStream(newteeMarkSheet1));
			}

			if (session.equalsIgnoreCase("Semester II")) {
				wb = new XSSFWorkbook(new FileInputStream(newteeMarkSheet2));
			}

			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("tee");
			XSSFRow secondRow = template.getRow(1);

			List<TEEBean> teeBeans = teeService
					.getAllInternalTeeForCourse(courseId);
			List<TEECriteriaBean> teeCriteriaList = new ArrayList<TEECriteriaBean>();
			if (teeBeans != null) {
				for (TEEBean tb : teeBeans) {
					int i = 5;
					int j = 1;
					teeCriteriaList = teeService.getCriteriaListBasedOnId(tb
							.getId() + "");
					for (TEECriteriaBean bean : teeCriteriaList) {
						XSSFRow row = template.getRow(i);
						row.getCell(j++).setCellValue(bean.getWeightage());
						row.getCell(j++).setCellValue(bean.getCriteria_desc());
						row.getCell(j++).setCellValue(bean.getMapping_desc());
						i++;
						j = 1;
					}

				}
			}

			int start = 2;
			for (TEECriteriaBean bean : teeCriteriaList) {
				template.getRow(10).getCell(start)
						.setCellValue(bean.getMapping_desc());
				template.getRow(11).getCell(start)
						.setCellValue(bean.getCriteria_desc());
				start++;
			}

			int nextRow = 14;
			int roll = 1;
			log.info("mapOfSapIdAndCourseId------------------"
					+ mapOfSapIdAndCourseId);

			// for (String name : mapOfSapIdAndCourseId.values()) {

			for (Entry<String, String> entry : mapOfSapIdAndCourseId.entrySet()) {
				log.info("Key is ----------- " + entry.getKey());
				XSSFRow row = template.getRow(nextRow);
				String sapId = null;
				sapId = entry.getKey();
				row.getCell(0).setCellValue(sapIdToRoll.get(sapId));
				 row.getCell(1).setCellValue(sapId);
				row.getCell(2).setCellValue(
						mapOfSapIdAndCourseId.get(entry.getKey()));
				roll++;
				nextRow++;

			}

			// }

			XSSFRow thirdRow = template.getRow(2);

			String courseHeader = "TEE Marksheet " + " for course "
					+ courseBean.getCourse_name();
			thirdRow.getCell(0).setCellValue(courseHeader);

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);
		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		return filePath;

	}

	public String getTeeMarksheetForInternalAndExternal(String session,
			String courseId, CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator + "TEEInternalExternal"
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		FileOutputStream fileOut = null;
		try {

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.getStudentsForCouse(courseId);
			List<TEEBean> allTeeLst = teeService.getAllTeeForCourse(courseId);

			List<Map<String, String>> lstMap = marksService
					.getInternalExternalTee(mapOfSapIdAndCourseId, courseId);
			XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(teeIntExt));
			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("tee");
			XSSFRow secondRow = template.getRow(1);
			secondRow.createCell(1).setCellValue(
					courseBean.getCourse_name().toUpperCase());

			for (TEEBean bean : allTeeLst) {
				switch (bean.getTeeType()) {
				case "Internal":
					XSSFRow row = template.getRow(2);
					row.createCell(1).setCellValue(bean.getTee_percent());
					break;

				case "External":
					XSSFRow rw = template.getRow(3);
					rw.createCell(1).setCellValue(bean.getTee_percent());
					break;
				}
			}

			int i = 5;

			for (Map<String, String> mp : lstMap) {
				XSSFRow row = template.createRow(i);
				double total = 0;
				row.createCell(0).setCellValue(mp.get("sapId"));
				row.createCell(1).setCellValue(mp.get("studentName"));
				row.createCell(2).setCellValue(mp.get("teeInternalTotal"));

				if (mp.containsKey("teeExternalTotal"))
					row.createCell(3).setCellValue(mp.get("teeExternalTotal"));
				row.createCell(4).setCellValue(mp.get("teeInternalWtTotal"));
				total = Double.valueOf(mp.get("teeInternalWtTotal"));
				if (mp.containsKey("teeExternalWtTotal")) {
					row.createCell(5)
							.setCellValue(mp.get("teeExternalWtTotal"));
					total = total
							+ Double.valueOf(mp.get("teeExternalWtTotal"));
				}
				row.createCell(6).setCellValue(total + "");
				i++;
			}

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);
		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		return filePath;

	}

	public String getTeeMarksheetExternal(String session, String courseId,
			CourseBean courseBean, String year) {
		String filePath = tempDir + File.separator + "TeeMarkSheet"
				+ courseBean.getCourse_name() + Instant.now().toEpochMilli()
				+ ".xlsx";
		FileOutputStream fileOut = null;
		try {
			XSSFWorkbook wb = null;
			List<Map<String, Object>> resultList = markSheetDao
					.getJdbcTemplate().queryForList(
							"select * from sap_roll where active='Y'");
			Map<String, String> sapIdToRoll = new HashMap<String, String>();
			if (resultList != null) {
				for (Map<String, Object> m : resultList)
					sapIdToRoll.put("" + m.get("sap_id"), "" + m.get("rollNo"));
			}

			Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
					.getStudentsForCouse(courseId);
			if (session.equalsIgnoreCase("Semester I")) {
				wb = new XSSFWorkbook(new FileInputStream(newteeMarkSheet1));
			}

			if (session.equalsIgnoreCase("Semester II")) {
				wb = new XSSFWorkbook(new FileInputStream(newteeMarkSheet2));
			}

			/*
			 * XSSFWorkbook wb = new XSSFWorkbook( new
			 * FileInputStream(teeMarkSheet));
			 */
			fileOut = new FileOutputStream(filePath);
			// Sheet mySheet = wb.getSheetAt(0);
			XSSFSheet template = wb.getSheet("tee");
			XSSFRow secondRow = template.getRow(1);

			List<TEEBean> teeBeans = teeService
					.getAllExternalTeeForCourse(courseId);
			List<TEECriteriaBean> teeCriteriaList = new ArrayList<TEECriteriaBean>();
			if (teeBeans != null) {
				for (TEEBean tb : teeBeans) {
					int i = 5;
					int j = 1;
					teeCriteriaList = teeService.getCriteriaListBasedOnId(tb
							.getId() + "");
					for (TEECriteriaBean bean : teeCriteriaList) {
						XSSFRow row = template.getRow(i);
						row.getCell(j++).setCellValue(bean.getWeightage());
						row.getCell(j++).setCellValue(bean.getCriteria_desc());
						row.getCell(j++).setCellValue(bean.getMapping_desc());
						i++;
						j = 1;
					}

				}
			}

			int start = 2;
			for (TEECriteriaBean bean : teeCriteriaList) {
				template.getRow(10).getCell(start)
						.setCellValue(bean.getMapping_desc());
				template.getRow(11).getCell(start)
						.setCellValue(bean.getCriteria_desc());
				start++;
			}

			int nextRow = 14;
			int roll = 1;

			/*
			 * for (String sapId : mapOfSapIdAndCourseId.values()) { XSSFRow row
			 * = template.getRow(nextRow);
			 * row.getCell(0).setCellValue(sapIdToRoll.get(sapId));
			 * row.getCell(1).setCellValue(sapId); roll++; nextRow++; }
			 */

			for (Entry<String, String> entry : mapOfSapIdAndCourseId.entrySet()) {
				log.info("Key is ----------- " + entry.getKey());
				XSSFRow row = template.getRow(nextRow);
				String sapId = null;
				sapId = entry.getKey();
				row.getCell(0).setCellValue(sapIdToRoll.get(sapId));
				// row.getCell(1).setCellValue(sapId);
				row.getCell(1).setCellValue(
						mapOfSapIdAndCourseId.get(entry.getKey()));
				roll++;
				nextRow++;

			}

			XSSFRow thirdRow = template.getRow(2);

			String courseHeader = "TEE Marksheet " + " for course "
					+ courseBean.getCourse_name();
			thirdRow.getCell(0).setCellValue(courseHeader);

			wb.write(fileOut);
			wb.close();

		} catch (Exception e) {
			log.info("Exception", e);
		} finally {
			if (fileOut != null) {
				IOUtils.closeQuietly(fileOut);
			}
		}

		return filePath;

	}

	public String getCourseWiseMapper() {
		String filePath = tempDir + File.separator + "abc" + Math.random()
				+ ".xlsx";
		List<Map> result = markSheetDao.getQueryResultsTotal();
		List<Map<String, Object>> resultMap = new ArrayList();
		List<String> headers = new ArrayList();

		Map<String, Map<String, Object>> studentRecords = new HashMap();

		int count = 0;
		for (Map<String, Object> m : result) {

			String sapId = m.get("sapId").toString();
			String icId = m.get("id").toString();
			Map<String, Object> rsMap = new LinkedHashMap<>();
			Map<String, Object> sr = null;
			sr = studentRecords.get(sapId);
			if (sr == null) {
				sr = new HashMap();
				sr.put("SAPId", sapId);
				sr.put("First Name", m.get("fname"));
				sr.put("Last Name", m.get("lname"));

			}
			String mapping = Utils.getBlankIfNull(m.get("mapping"));
			String cweightage = Utils.getBlankIfNull(m
					.get("criteria_weightage"));
			String courseName = (String) m.get("courseName");

			if (!mapping.isEmpty()) {
				String mpName = courseName + "Mapper" + mapping;
				if (!sr.containsKey(mpName)) {
					sr.put(mpName, mpName);
					/*
					 * if (!headers.contains(mpName)) headers.add(mpName);
					 */
				}

				String scored = courseName + mapping + "Scored";
				String outOff = courseName + mapping + "Out Of";

				/*
				 * if (!headers.contains(scored)) { headers.add(scored); }
				 * 
				 * if (!headers.contains(outOff)) { headers.add(outOff); }
				 */

				if (!sr.containsKey(outOff)) {
					sr.put(outOff, cweightage);
				} else {
					double total = Double.valueOf(cweightage)
							+ Double.valueOf(sr.get(outOff).toString());
					sr.put(outOff, total + "");
				}

				if (count == 0) {

					String obtained = Utils.getBlankIfNull(m.get("w1"));
					if (!sr.containsKey(scored)) {
						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 1) {

					String obtained = Utils.getBlankIfNull(m.get("w2"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 2) {

					String obtained = Utils.getBlankIfNull(m.get("w3"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 3) {

					String obtained = Utils.getBlankIfNull(m.get("w4"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

					count = -1;
				}
				count++;
				studentRecords.put(sapId, sr);

			}
		}
		headers.add("SAPId");
		headers.add("First Name");
		headers.add("Last Name");
		headers.add("Course Name");
		headers.add("Mapping Description");
		headers.add("Student Score");
		headers.add("Total  Score(OutOff)");
		headers.add("Percentage");
		for (Map.Entry<String, Map<String, Object>> m : studentRecords
				.entrySet()) {

			Map<String, Object> res = m.getValue();

			Set<String> keys = res.keySet();
			List<String> mappers = new ArrayList();
			for (String k : keys) {
				if (k.indexOf("Mapper") != -1) {
					mappers.add(k);
				}

			}
			List<String> sapIds = new ArrayList<String>();

			for (String k : mappers) {
				Map<String, Object> rs = new HashMap();
				String id = res.get("SAPId").toString();
				if (!sapIds.contains(id)) {
					rs.put("SAPId", id);
					rs.put("First Name", res.get("First Name"));
					rs.put("Last Name", res.get("Last Name"));
					sapIds.add(id);
				} else {
					rs.put("SAPId", "");
					rs.put("First Name", "");
					rs.put("Last Name", "");
				}
				String courseName = org.apache.commons.lang3.StringUtils
						.substring(k, 0, org.apache.commons.lang3.StringUtils
								.indexOf(k, "Mapper"));
				rs.put("Course Name", courseName);
				;
				String mapping = org.apache.commons.lang3.StringUtils
						.substring(k, org.apache.commons.lang3.StringUtils
								.indexOf(k, "Mapper") + 6);
				String scored = courseName + mapping + "Scored";
				String outOff = courseName + mapping + "Out Of";
				String score = Utils.getBlankIfNull(res.get(scored));
				String out = Utils.getBlankIfNull(res.get(outOff));
				rs.put("Mapping Description", mapping);
				if (!score.isEmpty())
					rs.put("Student Score",
							doublePrecisionFormatter.format(score));
				else
					rs.put("Student Score", score);
				if (!out.isEmpty())
					rs.put("Total  Score(OutOff)",
							doublePrecisionFormatter.format(out));
				else
					rs.put("Total  Score(OutOff)", out);
				if (!score.isEmpty() && !out.isEmpty())
					rs.put("Percentage",
							doublePrecisionFormatter.format((Double
									.valueOf(score) / Double.valueOf(out)) * 100)
									+ "");
				resultMap.add(rs);

			}

		}
		ExcelCreater.CreateExcelFile(resultMap, headers, filePath);

		return filePath;

	}

	public static Color darken(Color color, double fraction) {

		int red = (int) Math
				.round(Math.max(0, color.getRed() - 255 * fraction));
		int green = (int) Math.round(Math.max(0, color.getGreen() - 255
				* fraction));
		int blue = (int) Math.round(Math.max(0, color.getBlue() - 255
				* fraction));

		int alpha = color.getAlpha();

		return new Color(red, green, blue, alpha);

	}

	public String getMarks(String studentId) {
		String filePath = tempDir + File.separator
				+ Instant.now().toEpochMilli() + ".png";

		Document document = new Document();
		String fileName = tempDir + File.separator
				+ RandomStringUtils.randomAlphabetic(4)
				+ Instant.now().toEpochMilli() + ".pdf";

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
		} catch (FileNotFoundException | DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		List<Map> result = markSheetDao.getMarksPerStudent(studentId);

		DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		JFreeChart chart = ChartFactory.createBarChart3D("ICA reports", "",
				"Weighted Total", dataset, PlotOrientation.VERTICAL, true,
				true, false);

		ChartPanel c = new ChartPanel(chart);
		ItemLabelPosition e = new ItemLabelPosition();
		chart.setBackgroundPaint(Color.LIGHT_GRAY);
		CategoryPlot p = chart.getCategoryPlot();
		// p.setRenderer(new MySBRenderer());

		p.setNoDataMessage("No data found");

		CategoryAxis domainAxis = (CategoryAxis) p.getDomainAxis();

		for (Map m : result) {
			String iceName = Utils.getBlankIfNull(m.get("iceName"));
			String marks = Utils.getBlankIfNull(m.get("weighted_total"));
			dataset.setValue(Double.valueOf(marks), iceName, iceName);
		}

		domainAxis.setVisible(false);
		// domainAxis.setMaximumCategoryLabelWidthRatio(1.0f);
		// domainAxis.setMaximumCategoryLabelLines(3);// -> this is the
		// point, you could
		// set the label
		// ratio to 3 times
		// the size of the
		// width of the
		// category.

		try {

			((BarRenderer) p.getRenderer())
					.setBarPainter(new StandardBarPainter());

			BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();

			int j = 0;
			for (int i = 0; i < dataset.getRowCount(); i++) {
				if (i < colorPool.length) {
					r.setSeriesPaint(i, colorPool[(i)]);

				} else {
					if (j < colorPool.length)
						r.setSeriesPaint(i, colorPool[(i)]);
					else {
						j = 0;
						r.setSeriesPaint(i, colorPool[(i)]);
					}
				}
			}
			ChartUtilities.saveChartAsPNG(new File(filePath), chart,
					(int) Math.floor(PageSize.A4.getWidth() - 80),
					(int) Math.floor(PageSize.A4.getHeight() - 450));

			document.open();
			Image img = Image.getInstance(filePath);
			// img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
			// document.add(new Paragraph("ICA marks distribution."));
			document.add(img);
			document.close();
		} catch (Exception r) {
			r.printStackTrace();
		}

		return fileName;
	}

	public String getIcaPdf(String rollNo, String sapId, String name,
			Map<String, List<Map<String, String>>> result) {

		Document document = new Document();
		String fileName = tempDir + File.separator + rollNo + ".pdf";

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();

			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
			Chunk collegNameChunk = new Chunk("Individual ICA Report", font3);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);

			PdfPTable table = new PdfPTable(3);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Roll No"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("SAP Id"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Name"));

			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);

			PdfPCell rollNoCell = new PdfPCell(new Paragraph(rollNo));
			table.addCell(rollNoCell);

			PdfPCell sapIdCell = new PdfPCell(new Paragraph(sapId));
			table.addCell(sapIdCell);

			PdfPCell nameCell = new PdfPCell(new Paragraph(name));
			table.addCell(nameCell);
			document.add(table);

			// document.newPage();
			for (Map.Entry<String, List<Map<String, String>>> entry : result
					.entrySet()) {
				String moduleName = entry.getKey();

				try {

					DefaultCategoryDataset dataset = new DefaultCategoryDataset();

					JFreeChart chart = ChartFactory.createBarChart3D(
							moduleName, "", "Weighted Total", dataset,
							PlotOrientation.VERTICAL, true, true, false);

					ChartPanel c = new ChartPanel(chart);
					ItemLabelPosition e = new ItemLabelPosition();
					chart.setBackgroundPaint(Color.LIGHT_GRAY);
					CategoryPlot p = chart.getCategoryPlot();
					// p.setRenderer(new MySBRenderer());

					p.setNoDataMessage("No data found");

					CategoryAxis domainAxis = (CategoryAxis) p.getDomainAxis();

					domainAxis
							.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
					java.awt.Font font31 = new java.awt.Font("Dialog",
							Font.NORMAL, 10);
					// axis.setTickLabelFont(font31);
					domainAxis.setLabelFont(font31);

					for (Map m : result.get(moduleName)) {

						String iceName = Utils.getBlankIfNull(m
								.get("assignmentName"));
						String marks = Utils.getBlankIfNull(m.get("total"));
						if (!marks.isEmpty())
							dataset.setValue(Double.valueOf(marks), iceName,
									iceName);
					}

					domainAxis.setVisible(false);

					((BarRenderer) p.getRenderer())
							.setBarPainter(new StandardBarPainter());

					BarRenderer r = (BarRenderer) chart.getCategoryPlot()
							.getRenderer();

					int j = 0;
					for (int i = 0; i < dataset.getRowCount(); i++) {
						if (i < colorPool.length) {
							r.setSeriesPaint(i, new Color(0x69, 0xd8, 0x4f));

						} else {
							if (j < colorPool.length)
								r.setSeriesPaint(i, new Color(0x69, 0xd8, 0x4f));
							else {
								j = 0;
								r.setSeriesPaint(i, new Color(0x69, 0xd8, 0x4f));
							}
						}
					}

					String filePath = tempDir + File.separator
							+ Instant.now().toEpochMilli() + ".png";
					ChartUtilities.saveChartAsPNG(new File(filePath), chart,
							(int) Math.floor(PageSize.A4.getWidth() - 80),
							(int) Math.floor(PageSize.A4.getHeight() - 100));
					Image img = Image.getInstance(filePath);
					// img.scaleToFit(PageSize.A4.getWidth(),
					// PageSize.A4.getHeight());
					// document.add(new Paragraph("ICA marks distribution."));
					document.add(img);
				} catch (Exception e) {
					log.error("Exception", e);
				}

			}
			document.close();
		} catch (Exception r) {
			r.printStackTrace();
		}

		return fileName;
	}

	public String getTotalPdf(String rollNo, String sapId, String name,
			List<ICETEEConsolidated> marksList) {

		Document document = new Document();
		String fileName = tempDir + File.separator + rollNo + ".pdf";

		try {

			Map<String, CourseBean> courses = marksService.findCourse();
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();

			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
			Chunk collegNameChunk = new Chunk(
					"SVKM's NMIMS SCHOOL OF DESIGN							", font3);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);

			Chunk programChunk = new Chunk("Bachelor of Design (Humanising Technology) Year ", font3);
			programChunk.setUnderline(0.1f, -2f);
			Paragraph progName = new Paragraph();
			progName.add(programChunk);
			progName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			progName.setSpacingAfter(15);
			document.add(progName);
			PdfPTable table = new PdfPTable(3);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Roll No"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("SAP Id"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Name"));

			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);

			PdfPCell rollNoCell = new PdfPCell(new Paragraph(rollNo));
			table.addCell(rollNoCell);

			PdfPCell sapIdCell = new PdfPCell(new Paragraph(sapId));
			table.addCell(sapIdCell);

			PdfPCell nameCell = new PdfPCell(new Paragraph(name));
			table.addCell(nameCell);
			document.add(table);

			// document.newPage();

			PdfPTable marksTable = new PdfPTable(6);

			marksTable.setHeaderRows(1);
			marksTable.setWidthPercentage(100);
			marksTable.getDefaultCell().setUseAscender(true);
			marksTable.getDefaultCell().setUseDescender(true);
			PdfPCell srNo = new PdfPCell(new Paragraph("Sr.No"));
			PdfPCell modNo = new PdfPCell(new Paragraph("Module No"));
			PdfPCell modName = new PdfPCell(new Paragraph("Module Name"));
			PdfPCell ica = new PdfPCell(new Paragraph("ICA"));
			PdfPCell tee = new PdfPCell(new Paragraph("TEE"));
			PdfPCell total = new PdfPCell(new Paragraph("Total"));

			marksTable.setHorizontalAlignment(Element.ALIGN_LEFT);
			marksTable.addCell(srNo);
			marksTable.addCell(modNo);
			marksTable.addCell(modName);
			marksTable.addCell(ica);
			marksTable.addCell(tee);
			marksTable.addCell(total);

			int i = 1;

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			JFreeChart chart = ChartFactory.createStackedBarChart3D(
					"Overall Performance Report for " + name, "", "", dataset,
					PlotOrientation.VERTICAL, true, true, false);

			ChartPanel c = new ChartPanel(chart);
			ItemLabelPosition e = new ItemLabelPosition();
			chart.setBackgroundPaint(Color.LIGHT_GRAY);
			CategoryPlot p = chart.getCategoryPlot();

			CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();

			axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
			java.awt.Font font31 = new java.awt.Font("Dialog", Font.NORMAL, 10);
			// axis.setTickLabelFont(font31);
			axis.setLabelFont(font31);
			// p.setRenderer(new MySBRenderer());

			p.setNoDataMessage("No data found");

			// p.setRenderer(new MySBRenderer());
			log.info("marksList" + marksList);
			if (marksList != null)
				for (ICETEEConsolidated mrk : marksList) {

					CourseBean bn = courses.get(mrk.getCourseId());
					PdfPCell value1 = new PdfPCell(new Paragraph(i + "."));
					PdfPCell value2 = new PdfPCell(new Paragraph(
							bn.getModule_abbr()));
					PdfPCell value3 = new PdfPCell(new Paragraph(
							bn.getCourse_name()));
					Integer icaSplit = Integer.valueOf(bn.getIca_tee_split());
					Integer teeSplit = 100 - icaSplit;

					double icaTotal = (Double
							.valueOf(mrk.getIceWeightedTotal()) / 100)
							* icaSplit;
					Double teeTotal = mrk.getTeeWeightedTotal() != null ? (Double
							.valueOf(mrk.getTeeWeightedTotal()) / 100)
							* teeSplit : 0;
					double result = icaTotal + teeTotal;

					PdfPCell value4 = new PdfPCell(new Paragraph(
							doublePrecisionFormatter.format(icaTotal) + ""));
					PdfPCell value5 = new PdfPCell(new Paragraph(
							doublePrecisionFormatter.format(teeTotal) + ""));
					PdfPCell value6 = new PdfPCell(new Paragraph(
							doublePrecisionFormatter.format(result) + ""));
					marksTable.addCell(value1);
					marksTable.addCell(value2);
					marksTable.addCell(value3);
					marksTable.addCell(value4);
					marksTable.addCell(value5);
					marksTable.addCell(value6);

					dataset.addValue(icaTotal, "ICA", bn.getCourse_name());
					dataset.addValue(teeTotal, "TEE", bn.getCourse_name());

					i++;
				}
			document.add(marksTable);

			String filePath = tempDir + File.separator
					+ Instant.now().toEpochMilli() + ".png";
			ChartUtilities.saveChartAsPNG(new File(filePath), chart,
					(int) Math.floor(PageSize.A4.getWidth() - 80),
					(int) Math.floor(PageSize.A4.getHeight() - 100));
			Image img = Image.getInstance(filePath);
			// img.scaleToFit(PageSize.A4.getWidth(),
			// PageSize.A4.getHeight());
			// document.add(new Paragraph("ICA marks distribution."));
			document.newPage();
			document.add(img);
			document.close();
		} catch (Exception r) {
			r.printStackTrace();
		}

		return fileName;
	}

	static BaseFont baseFont3 = null;

	public String getIcaMappingPdf(String rollNo, String sapId, String name,
			Map<String, Map<String, Double>> result) {
		DecimalFormat newFormat = new DecimalFormat("#.##");

		Document document = new Document();
		String fileName = tempDir + File.separator + rollNo + ".pdf";

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();
			if (baseFont3 == null)
				baseFont3 = BaseFont.createFont(fontPath, BaseFont.WINANSI,
						BaseFont.EMBEDDED);

			Font biggerFont = new Font(baseFont3.createFont(), 20, Font.BOLD);
			Font font3 = new Font(baseFont3.createFont(), 15, Font.BOLD);
			Chunk collegNameChunk = new Chunk("SVKM's NMIMS SCHOOL OF DESIGN ",
					biggerFont);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);

			Chunk programme = new Chunk("Bachelor of Design (Humanising Technology)  ", font3);
			programme.setUnderline(0.1f, -2f);
			Paragraph programmeP = new Paragraph();
			programmeP.add(programme);
			programmeP.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			programmeP.setSpacingAfter(15);

			LocalDateTime now = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");

			String formatDateTime = now.format(formatter);
			Chunk datePara = new Chunk("Report Date " + formatDateTime, font3);
			datePara.setUnderline(0.1f, -2f);
			Paragraph dateParaP = new Paragraph();
			dateParaP.add(datePara);
			dateParaP.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			dateParaP.setSpacingAfter(15);

			document.add(programmeP);

			document.add(dateParaP);

			// Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14,
			// Font.BOLD);
			Chunk subjectNameChunk = new Chunk("Student Attribute Map", font3);
			subjectNameChunk.setUnderline(0.1f, -2f);
			Paragraph subjectName = new Paragraph();
			subjectName.add(subjectNameChunk);
			subjectName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			subjectName.setSpacingAfter(15);
			document.add(subjectName);

			PdfPTable table = new PdfPTable(3);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			PdfPCell cell1 = new PdfPCell(new Paragraph("ROLL No"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("SAP ID"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("NAME"));

			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);

			PdfPCell rollNoCell = new PdfPCell(new Paragraph(rollNo));
			table.addCell(rollNoCell);

			PdfPCell sapIdCell = new PdfPCell(new Paragraph(sapId));
			table.addCell(sapIdCell);

			PdfPCell nameCell = new PdfPCell(new Paragraph(name));
			table.addCell(nameCell);
			document.add(table);

			// document.newPage();

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			JFreeChart chart = ChartFactory.createStackedBarChart("", "", "",
					dataset, PlotOrientation.VERTICAL, true, true, false);

			ChartPanel c = new ChartPanel(chart);
			ItemLabelPosition e = new ItemLabelPosition();
			chart.setBackgroundPaint(Color.WHITE);
			CategoryPlot p = chart.getCategoryPlot();
			// p.setRenderer(new MySBRenderer());

			p.setNoDataMessage("No data found");

			Set<String> md = new HashSet();
			for (Map.Entry<String, Map<String, Double>> entry : result
					.entrySet()) {
				String mappingDesc = entry.getKey();
				md.add(mappingDesc);
				try {

					Map<String, Double> m = entry.getValue();
					Double total = (m.get("total"));
					Double score = (m.get("scored"));

					dataset.addValue(Double.valueOf(newFormat.format(Double
							.valueOf(score))), "Attribute Level Reached ",
							mappingDesc);
					dataset.addValue(
							Double.valueOf(newFormat.format(Double
									.valueOf(total) - Double.valueOf(score))),
							"Expected Level ", mappingDesc);

					((BarRenderer) p.getRenderer())
							.setBarPainter(new StandardBarPainter());

					BarRenderer r = (BarRenderer) chart.getCategoryPlot()
							.getRenderer();

					int j = 0;
					for (int i = 0; i < dataset.getRowCount(); i++) {
						if (i == 0 || i % 2 == 0) {
							r.setSeriesPaint(i, new Color(0, 255, 0));

						} else {
							r.setSeriesPaint(i, new Color(0, 0, 0));
						}
					}

				} catch (Exception e1) {
					log.error("Exception", e);
				}

			}

			SubCategoryAxis domainAxis = new SubCategoryAxis("");
			// domainAxis.setCategoryMargin(0.05);
			for (String m : md) {
				// domainAxis.addSubCategory(m);
			}

			// r.setSeriesPaint(i, colorPool[(i)]);

			// renderer.setSeriesToGroupMap(map);
			// p.setRenderer(renderer);

			String filePath = tempDir + File.separator
					+ Instant.now().toEpochMilli() + ".png";

			if (baseFont3 == null)
				baseFont3 = BaseFont.createFont(fontPath, BaseFont.WINANSI,
						BaseFont.EMBEDDED);
			try {
				java.awt.Font font = java.awt.Font.createFont(
						java.awt.Font.TRUETYPE_FONT, new FileInputStream(
								fontPath));
				font = font.deriveFont(12F);

				chart.getLegend().setItemFont(font);
				chart.getTitle().setFont(font);

			} catch (Exception ee) {
				log.error("Exception", ee);
				ee.printStackTrace();
			}

			p.setDomainAxis(domainAxis);
			BasicStroke stroke = new BasicStroke(2.0f);

			p.setRangeGridlineStroke(stroke);
			p.setRangeGridlinesVisible(true);
			p.setDomainGridlineStroke(stroke);
			p.setRangeGridlinePaint(Color.black);
			p.setBackgroundPaint(new Color(255, 255, 255, 0));
			ChartUtilities.saveChartAsPNG(new File(filePath), chart,
					(int) Math.floor(PageSize.A4.getWidth() - 80),
					(int) Math.floor(PageSize.A4.getHeight() - 450));
			Image img = Image.getInstance(filePath);
			// img.scaleToFit(PageSize.A4.getWidth(),
			// PageSize.A4.getHeight());
			// document.add(new Paragraph("ICA marks distribution."));
			document.add(img);
			document.close();
		} catch (Exception r) {
			log.error("Exception", r);
		}

		return fileName;
	}

	public String getMappingPdf(
			Map<String, Map<String, Map<String, Double>>> studMap, int size,
			String courseName) {

		Document document = new Document();
		String fileName = tempDir + File.separator + courseName
				+ Instant.now().toEpochMilli() + ".pdf";
		DecimalFormat newFormat = new DecimalFormat("#.##");

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();
			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
			Chunk collegNameChunk = new Chunk(courseName.toUpperCase(), font3);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);
			PdfPTable table = new PdfPTable(3 + size);
			int start = 0;

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();

			for (Map.Entry<String, Map<String, Map<String, Double>>> entry : studMap
					.entrySet()) {

				String key = entry.getKey();

				Map<String, Map<String, Double>> values = entry.getValue();
				String[] array = org.apache.commons.lang3.StringUtils.split(
						key, "~");
				String rollNo = array[0];
				String sapId = array[1];
				String name = array[2];

				table.setHeaderRows(1);
				table.setWidthPercentage(100);
				table.getDefaultCell().setUseAscender(true);
				table.getDefaultCell().setUseDescender(true);

				if (start == 0) {

					PdfPCell cell1 = new PdfPCell(new Paragraph("Roll No"));
					PdfPCell cell2 = new PdfPCell(new Paragraph("SAP Id"));
					PdfPCell cell3 = new PdfPCell(new Paragraph("Name"));

					table.setHorizontalAlignment(Element.ALIGN_LEFT);
					table.addCell(cell1);
					table.addCell(cell2);
					table.addCell(cell3);

					Set<String> mp = values.keySet();
					for (String m : mp) {
						PdfPCell c = new PdfPCell(new Paragraph(m));
						table.addCell(c);
					}

					PdfPCell rollNoCell = new PdfPCell(new Paragraph(rollNo));
					table.addCell(rollNoCell);

					PdfPCell sapIdCell = new PdfPCell(new Paragraph(sapId));
					table.addCell(sapIdCell);

					PdfPCell nameCell = new PdfPCell(new Paragraph(name));
					table.addCell(nameCell);

					for (String k : mp) {
						Map<String, Double> calc = values.get(k);
						double percent = calc.get("scored") / calc.get("total");
						percent = percent * 100;
						PdfPCell percentCell = new PdfPCell(new Paragraph(
								doublePrecisionFormatter.format(percent) + ""));
						table.addCell(percentCell);
						dataset.addValue(percent, k, rollNo);
					}

					start = 1;

				} else {

					PdfPCell rollNoCell = new PdfPCell(new Paragraph(rollNo));
					table.addCell(rollNoCell);

					PdfPCell sapIdCell = new PdfPCell(new Paragraph(sapId));
					table.addCell(sapIdCell);

					PdfPCell nameCell = new PdfPCell(new Paragraph(name));
					table.addCell(nameCell);

					Set<String> mp = values.keySet();
					for (String k : mp) {
						Map<String, Double> calc = values.get(k);
						double percent = calc.get("scored") / calc.get("total");
						percent = percent * 100;

						PdfPCell percentCell = new PdfPCell(new Paragraph(
								doublePrecisionFormatter.format(percent) + ""));
						table.addCell(percentCell);
						dataset.addValue(percent, k, rollNo);
					}

				}

			}
			document.add(table);
			document.newPage();

			final JFreeChart chart = ChartFactory.createLineChart(
					"Line Chart Demo 1", // chart title
					"Type", // domain axis label
					"Value", // range axis label
					dataset, // data
					PlotOrientation.HORIZONTAL, // orientation
					true, // include legend
					true, // tooltips
					false // urls
					);
			ChartPanel c = new ChartPanel(chart);
			ItemLabelPosition e = new ItemLabelPosition();
			chart.setBackgroundPaint(Color.LIGHT_GRAY);
			CategoryPlot p = chart.getCategoryPlot();
			// p.setRenderer(new MySBRenderer());

			p.setNoDataMessage("No data found");

			// renderer.setSeriesToGroupMap(map);
			// p.setRenderer(renderer);

			String filePath = tempDir + File.separator
					+ Instant.now().toEpochMilli() + ".png";
			ChartUtilities.saveChartAsPNG(new File(filePath), chart,
					(int) Math.floor(PageSize.A4.getWidth() - 80),
					(int) Math.floor(PageSize.A4.getHeight() - 450));
			Image img = Image.getInstance(filePath);
			// img.scaleToFit(PageSize.A4.getWidth(),
			// PageSize.A4.getHeight());
			// document.add(new Paragraph("ICA marks distribution."));
			document.add(img);
			document.close();
		} catch (Exception r) {
			r.printStackTrace();
		}

		return fileName;
	}

	public String getMinMax(String currentAcadSession) {

		DecimalFormat newFormat = new DecimalFormat("#.##");

		List<Map> students = markSheetDao.getMinMax(currentAcadSession.trim());

		int rollNo = 1;
		String oldCourseName = "";
		String oldSapId = "";
		String oldName = "";
		double minValue = 0;
		Map<String, Map<String, Object>> marksMap = new HashMap();
		for (Map<String, Object> stud : students) {
			String sapId = stud.get("sapId").toString();
			String name = Utils.getBlankIfNull(stud.get("name"));
			String courseName = Utils.getBlankIfNull(stud.get("cname"));
			if (oldCourseName.isEmpty() || !oldCourseName.equals(courseName)) {
				double max = Double.valueOf(Utils.getBlankIfNull(stud
						.get("markcourse")));
				if (marksMap.containsKey(courseName)) {
					marksMap.get(courseName).put("max",
							doublePrecisionFormatter.format(max));
					marksMap.get(courseName).put("maxname", name);
					marksMap.get(courseName).put("maxsapId", sapId);
				} else {
					Map<String, Object> m = new HashMap<String, Object>();
					m.put("max", doublePrecisionFormatter.format(max));
					m.put("maxname", name);

					m.put("maxsapId", sapId);
					marksMap.put(courseName, m);
				}
				if (!oldCourseName.isEmpty()) {

					if (marksMap.containsKey(oldCourseName)) {
						marksMap.get(oldCourseName).put("min",
								doublePrecisionFormatter.format(minValue));
						marksMap.get(oldCourseName).put("minname", oldName);
						marksMap.get(oldCourseName).put("minsapId", oldSapId);
					}
				}
			}

			minValue = Double.valueOf(Utils.getBlankIfNull(stud
					.get("markcourse")));

			oldSapId = sapId;
			oldName = name;
			oldCourseName = courseName;
		}

		marksMap.get(oldCourseName).put("min",
				doublePrecisionFormatter.format(minValue));
		marksMap.get(oldCourseName).put("minname", oldName);
		marksMap.get(oldCourseName).put("minsapId", oldSapId);

		log.info("marksMap" + marksMap);

		Document document = new Document();
		String fileName = tempDir + File.separator + rollNo + ".pdf";

		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();

			/*
			 * renderer.setBaseStroke(new BasicStroke(2.0f));
			 * renderer.setSeriesPaint(0, Color.blue);
			 */
			/*
			 * DateAxis axis = (DateAxis) plot.getDomainAxis();
			 * axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);
			 * 
			 * NumberAxis yAxis1 = (NumberAxis) plot.getRangeAxis();
			 * yAxis1.setAutoRangeIncludesZero(false);
			 */
			int i = 1;

			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
			Chunk collegNameChunk = new Chunk("SVKM's NMIMS SCHOOL OF DESIGN ",
					font3);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);

			Chunk programme = new Chunk("Bachelor of Design (Humanising Technology)  " + currentAcadSession,
					font3);
			programme.setUnderline(0.1f, -2f);
			Paragraph programmeP = new Paragraph();
			programmeP.add(programme);
			programmeP.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			programmeP.setSpacingAfter(15);

			LocalDateTime now = LocalDateTime.now();

			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");

			String formatDateTime = now.format(formatter);
			Chunk datePara = new Chunk("Report Date" + formatDateTime, font3);
			datePara.setUnderline(0.1f, -2f);
			Paragraph dateParaP = new Paragraph();
			dateParaP.add(datePara);
			dateParaP.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			dateParaP.setSpacingAfter(15);

			document.add(programmeP);

			document.add(dateParaP);

			PdfPTable table = new PdfPTable(6);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Subject "));
			PdfPCell cell2 = new PdfPCell(new Paragraph("Max"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Name"));
			PdfPCell cell5 = new PdfPCell(new Paragraph("Min"));
			PdfPCell cell6 = new PdfPCell(new Paragraph("Name"));
			PdfPCell cell7 = new PdfPCell(new Paragraph("Average"));
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);

			table.addCell(cell5);
			table.addCell(cell6);
			table.addCell(cell7);

			DefaultCategoryDataset dataset = new DefaultCategoryDataset();
			for (Map.Entry<String, Map<String, Object>> entry : marksMap
					.entrySet()) {
				String courseName = entry.getKey();
				Map<String, Object> valueMap = entry.getValue();
				Double max = Double.valueOf(valueMap.get("max").toString());
				Double min = Double.valueOf(valueMap.get("min").toString());
				Double avg = (max + min) / 2;

				PdfPCell courseNameCell = new PdfPCell(
						new Paragraph(courseName));
				table.addCell(courseNameCell);

				PdfPCell maxCell = new PdfPCell(new Paragraph(max + ""));
				table.addCell(maxCell);

				PdfPCell maxNameCell = new PdfPCell(new Paragraph(
						valueMap.get("maxname") + ""));
				table.addCell(maxNameCell);

				PdfPCell mincell = new PdfPCell(new Paragraph(min + ""));
				table.addCell(mincell);

				PdfPCell avCell = new PdfPCell(new Paragraph(
						valueMap.get("minname") + ""));
				table.addCell(avCell);

				PdfPCell minNameCell = new PdfPCell(new Paragraph(
						doublePrecisionFormatter.format(avg) + ""));
				table.addCell(minNameCell);

				dataset.addValue(min, "Min", courseName);
				dataset.addValue(max, "Max", courseName);
				i++;
				/*
				 * NumberAxis yAxis2 = new NumberAxis("Marks");
				 * yAxis2.setAutoRangeIncludesZero(false); plot.setRangeAxis(1,
				 * yAxis2);
				 */

				// plot.mapDatasetToRangeAxis(1, 1);
				i++;
				// plot.setRenderer(, new CandlestickRenderer(10.0));
				// plot.mapDatasetToRangeAxis(1, 1);
			}

			document.add(table);

			JFreeChart chart = ChartFactory.createBarChart(
					"Min/Max Category Plot", // chart title
					"Category", // domain axis label
					"Marks", // range axis label
					dataset, // data
					PlotOrientation.VERTICAL, true, // include legend
					true, // tooltips
					false // urls
					);

			CategoryPlot plot = (CategoryPlot) chart.getPlot();
			CategoryAxis domainAxis = (CategoryAxis) plot.getDomainAxis();

			/*
			 * domainAxis.setMaximumCategoryLabelWidthRatio(0.5f);
			 * domainAxis.setCategoryLabelPositions
			 * (CategoryLabelPositions.UP_90);
			 */
			domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
			domainAxis.setMaximumCategoryLabelWidthRatio(0.5f);
			domainAxis.setUpperMargin(0.07);

			// plot.setRangePannable(true);
			MinMaxCategoryRenderer renderer = new MinMaxCategoryRenderer();

			renderer.setDrawLines(true);
			plot.setRenderer(renderer);

			// renderer.setSeriesToGroupMap(map);
			// p.setRenderer(renderer);

			String filePath = tempDir + File.separator
					+ Instant.now().toEpochMilli() + ".png";
			ChartUtilities.saveChartAsPNG(new File(filePath), chart,
					(int) Math.floor(PageSize.A4.getWidth() - 80),
					(int) Math.floor(PageSize.A4.getHeight() - 410));
			Image img = Image.getInstance(filePath);
			// img.scaleToFit(PageSize.A4.getWidth(),
			// PageSize.A4.getHeight());
			// document.add(new Paragraph("ICA marks distribution."));
			document.add(img);
			document.close();
		} catch (Exception r) {
			log.error("Exception", r);
		}

		return fileName;
	}

	static List<Color> colorList = new ArrayList<Color>();

	private static Color[] colorPool = {
			// new Color(0x99, 0x00, 0x99), //purple
			// new Color(0xcc, 0x33, 0x00), //red
			// new Color(0xff, 0xcc, 0x66), //beige?
			// // new Color(0x00, 0x00, 0xff),
			// // new Color(0x66, 0x00, 0x99),
			// new Color(0x99, 0x66, 0x00), //brown
			// new Color(0xcc, 0x66, 0x00), //tan
			// new Color(0xff, 0xcc, 0xff), //pink
			// new Color(0x0, 0x66, 0x00), //dark green
			// new Color(0x66, 0x00, 0xff), //blue
			// new Color(0x99, 0x66, 0xff), //off-blue
			// new Color(0xcc, 0x66, 0x66), //another tan
			// new Color(0xff, 0xff, 0x00), //yellow
			// new Color(0x0, 0x99, 0x00), //green
			// new Color(0x66, 0x66, 0x99), //slate
			// new Color(0x99, 0x99, 0x99), //grey
			// new Color(0xcc, 0x99, 0x00), //off-tan
			// new Color(0x00, 0x99, 0x99), //puce
			// new Color(0x66, 0xcc, 0xff), //aqua
			// new Color(0x99, 0xcc, 0x00), //peagreen
			// new Color(0xcc, 0x99, 0xcc), //washedoutpurple
			// new Color(0x00, 0xff, 0x66), //limegreen
			// new Color(0xcc, 0xff, 0x00), //yellow-green
			// new Color(0x00, 0xff, 0xff), //light blue
			// new Color(0x66, 0x00, 0x00),
			// new Color(0x00, 0x00, 0x99)
			new Color(0xac, 0xc2, 0xd9), // cloudy blue
			new Color(0x56, 0xae, 0x57), // dark pastel green
			new Color(0xb2, 0x99, 0x6e), // dust
			new Color(0xa8, 0xff, 0x04), // electric lime
			new Color(0x69, 0xd8, 0x4f), // fresh green
			new Color(0x89, 0x45, 0x85), // light eggplant
			new Color(0x70, 0xb2, 0x3f), // nasty green
			new Color(0xd4, 0xff, 0xff), // really light blue
			new Color(0x65, 0xab, 0x7c), // tea
			new Color(0x95, 0x2e, 0x8f), // warm purple
			new Color(0xfc, 0xfc, 0x81), // yellowish tan
			new Color(0xa5, 0xa3, 0x91), // cement
			new Color(0x38, 0x80, 0x04), // dark grass green
			new Color(0x4c, 0x90, 0x85), // dusty teal
			new Color(0x5e, 0x9b, 0x8a), // grey teal
			new Color(0xef, 0xb4, 0x35), // macaroni and cheese
			new Color(0xd9, 0x9b, 0x82), // pinkish tan
			new Color(0x0a, 0x5f, 0x38), // spruce
			new Color(0x0c, 0x06, 0xf7), // strong blue
			new Color(0x61, 0xde, 0x2a), // toxic green
			new Color(0x37, 0x78, 0xbf), // windows blue
			new Color(0x22, 0x42, 0xc7), // blue blue
			new Color(0x53, 0x3c, 0xc6), // blue with a hint of purple
			new Color(0x9b, 0xb5, 0x3c), // booger
			new Color(0x05, 0xff, 0xa6), // bright sea green
			new Color(0x1f, 0x63, 0x57), // dark green blue
			new Color(0x01, 0x73, 0x74), // deep turquoise
			new Color(0x0c, 0xb5, 0x77), // green teal
			new Color(0xff, 0x07, 0x89), // strong pink
			new Color(0xaf, 0xa8, 0x8b), // bland
			new Color(0x08, 0x78, 0x7f), // deep aqua
			new Color(0xdd, 0x85, 0xd7), // lavender pink
			new Color(0xa6, 0xc8, 0x75), // light moss green
			new Color(0xa7, 0xff, 0xb5), // light seafoam green
			new Color(0xc2, 0xb7, 0x09), // olive yellow
			new Color(0xe7, 0x8e, 0xa5), // pig pink
			new Color(0x96, 0x6e, 0xbd), // deep lilac
			new Color(0xcc, 0xad, 0x60), // desert
			new Color(0xac, 0x86, 0xa8), // dusty lavender
			new Color(0x94, 0x7e, 0x94), // purpley grey
			new Color(0x98, 0x3f, 0xb2), // purply
			new Color(0xff, 0x63, 0xe9), // candy pink
			new Color(0xb2, 0xfb, 0xa5), // light pastel green
			new Color(0x63, 0xb3, 0x65), // boring green
			new Color(0x8e, 0xe5, 0x3f), // kiwi green
			new Color(0xb7, 0xe1, 0xa1), // light grey green
			new Color(0xff, 0x6f, 0x52), // orange pink
			new Color(0xbd, 0xf8, 0xa3), // tea green
			new Color(0xd3, 0xb6, 0x83), // very light brown
			new Color(0xff, 0xfc, 0xc4), // egg shell
			new Color(0x43, 0x05, 0x41), // eggplant purple
			new Color(0xff, 0xb2, 0xd0), // powder pink
			new Color(0x99, 0x75, 0x70), // reddish grey
			new Color(0xad, 0x90, 0x0d), // baby shit brown
			new Color(0xc4, 0x8e, 0xfd), // liliac
			new Color(0x50, 0x7b, 0x9c), // stormy blue
			new Color(0x7d, 0x71, 0x03), // ugly brown
			new Color(0xff, 0xfd, 0x78), // custard
			new Color(0xda, 0x46, 0x7d), // darkish pink
			new Color(0x41, 0x02, 0x00), // deep brown
			new Color(0xc9, 0xd1, 0x79), // greenish beige
			new Color(0xff, 0xfa, 0x86), // manilla
			new Color(0x56, 0x84, 0xae), // off blue
			new Color(0x6b, 0x7c, 0x85), // battleship grey
			new Color(0x6f, 0x6c, 0x0a), // browny green
			new Color(0x7e, 0x40, 0x71), // bruise
			new Color(0x00, 0x93, 0x37), // kelley green
			new Color(0xd0, 0xe4, 0x29), // sickly yellow
			new Color(0xff, 0xf9, 0x17), // sunny yellow
			new Color(0x1d, 0x5d, 0xec), // azul
			new Color(0x05, 0x49, 0x07), // darkgreen
			new Color(0xb5, 0xce, 0x08), // green/yellow
			new Color(0x8f, 0xb6, 0x7b), // lichen
			new Color(0xc8, 0xff, 0xb0), // light light green
			new Color(0xfd, 0xde, 0x6c), // pale gold
			new Color(0xff, 0xdf, 0x22), // sun yellow
			new Color(0xa9, 0xbe, 0x70), // tan green
			new Color(0x68, 0x32, 0xe3), // burple
			new Color(0xfd, 0xb1, 0x47), // butterscotch
			new Color(0xc7, 0xac, 0x7d), // toupe
			new Color(0xff, 0xf3, 0x9a), // dark cream
			new Color(0x85, 0x0e, 0x04), // indian red
			new Color(0xef, 0xc0, 0xfe), // light lavendar
			new Color(0x40, 0xfd, 0x14), // poison green
			new Color(0xb6, 0xc4, 0x06), // baby puke green
			new Color(0x9d, 0xff, 0x00), // bright yellow green
			new Color(0x3c, 0x41, 0x42), // charcoal grey
			new Color(0xf2, 0xab, 0x15), // squash
			new Color(0xac, 0x4f, 0x06), // cinnamon
			new Color(0xc4, 0xfe, 0x82), // light pea green
			new Color(0x2c, 0xfa, 0x1f), // radioactive green
			new Color(0x9a, 0x62, 0x00), // raw sienna
			new Color(0xca, 0x9b, 0xf7), // baby purple
			new Color(0x87, 0x5f, 0x42), // cocoa
			new Color(0x3a, 0x2e, 0xfe), // light royal blue
			new Color(0xfd, 0x8d, 0x49), // orangeish
			new Color(0x8b, 0x31, 0x03), // rust brown
			new Color(0xcb, 0xa5, 0x60), // sand brown
			new Color(0x69, 0x83, 0x39), // swamp
			new Color(0x0c, 0xdc, 0x73), // tealish green
			new Color(0xb7, 0x52, 0x03), // burnt siena
			new Color(0x7f, 0x8f, 0x4e), // camo
			new Color(0x26, 0x53, 0x8d), // dusk blue
			new Color(0x63, 0xa9, 0x50), // fern
			new Color(0xc8, 0x7f, 0x89), // old rose
			new Color(0xb1, 0xfc, 0x99), // pale light green
			new Color(0xff, 0x9a, 0x8a), // peachy pink
			new Color(0xf6, 0x68, 0x8e), // rosy pink
			new Color(0x76, 0xfd, 0xa8), // light bluish green
			new Color(0x53, 0xfe, 0x5c), // light bright green
			new Color(0x4e, 0xfd, 0x54), // light neon green
			new Color(0xa0, 0xfe, 0xbf), // light seafoam
			new Color(0x7b, 0xf2, 0xda), // tiffany blue
			new Color(0xbc, 0xf5, 0xa6), // washed out green
			new Color(0xca, 0x6b, 0x02), // browny orange
			new Color(0x10, 0x7a, 0xb0), // nice blue
			new Color(0x21, 0x38, 0xab), // sapphire
			new Color(0x71, 0x9f, 0x91), // greyish teal
			new Color(0xfd, 0xb9, 0x15), // orangey yellow
			new Color(0xfe, 0xfc, 0xaf), // parchment
			new Color(0xfc, 0xf6, 0x79), // straw
			new Color(0x1d, 0x02, 0x00), // very dark brown
			new Color(0xcb, 0x68, 0x43), // terracota
			new Color(0x31, 0x66, 0x8a), // ugly blue
			new Color(0x24, 0x7a, 0xfd), // clear blue
			new Color(0xff, 0xff, 0xb6), // creme
			new Color(0x90, 0xfd, 0xa9), // foam green
			new Color(0x86, 0xa1, 0x7d), // grey/green
			new Color(0xfd, 0xdc, 0x5c), // light gold
			new Color(0x78, 0xd1, 0xb6), // seafoam blue
			new Color(0x13, 0xbb, 0xaf), // topaz
			new Color(0xfb, 0x5f, 0xfc), // violet pink
			new Color(0x20, 0xf9, 0x86), // wintergreen
			new Color(0xff, 0xe3, 0x6e), // yellow tan
			new Color(0x9d, 0x07, 0x59), // dark fuchsia
			new Color(0x3a, 0x18, 0xb1), // indigo blue
			new Color(0xc2, 0xff, 0x89), // light yellowish green
			new Color(0xd7, 0x67, 0xad), // pale magenta
			new Color(0x72, 0x00, 0x58), // rich purple
			new Color(0xff, 0xda, 0x03), // sunflower yellow
			new Color(0x01, 0xc0, 0x8d), // green/blue
			new Color(0xac, 0x74, 0x34), // leather
			new Color(0x01, 0x46, 0x00), // racing green
			new Color(0x99, 0x00, 0xfa), // vivid purple
			new Color(0x02, 0x06, 0x6f), // dark royal blue
			new Color(0x8e, 0x76, 0x18), // hazel
			new Color(0xd1, 0x76, 0x8f), // muted pink
			new Color(0x96, 0xb4, 0x03), // booger green
			new Color(0xfd, 0xff, 0x63), // canary
			new Color(0x95, 0xa3, 0xa6), // cool grey
			new Color(0x7f, 0x68, 0x4e), // dark taupe
			new Color(0x75, 0x19, 0x73), // darkish purple
			new Color(0x08, 0x94, 0x04), // true green
			new Color(0xff, 0x61, 0x63), // coral pink
			new Color(0x59, 0x85, 0x56), // dark sage
			new Color(0x21, 0x47, 0x61), // dark slate blue
			new Color(0x3c, 0x73, 0xa8), // flat blue
			new Color(0xba, 0x9e, 0x88), // mushroom
			new Color(0x02, 0x1b, 0xf9), // rich blue
			new Color(0x73, 0x4a, 0x65), // dirty purple
			new Color(0x23, 0xc4, 0x8b), // greenblue
			new Color(0x8f, 0xae, 0x22), // icky green
			new Color(0xe6, 0xf2, 0xa2), // light khaki
			new Color(0x4b, 0x57, 0xdb), // warm blue
			new Color(0xd9, 0x01, 0x66), // dark hot pink
			new Color(0x01, 0x54, 0x82), // deep sea blue
			new Color(0x9d, 0x02, 0x16), // carmine
			new Color(0x72, 0x8f, 0x02), // dark yellow green
			new Color(0xff, 0xe5, 0xad), // pale peach
			new Color(0x4e, 0x05, 0x50), // plum purple
			new Color(0xf9, 0xbc, 0x08), // golden rod
			new Color(0xff, 0x07, 0x3a), // neon red
			new Color(0xc7, 0x79, 0x86), // old pink
			new Color(0xd6, 0xff, 0xfe), // very pale blue
			new Color(0xfe, 0x4b, 0x03), // blood orange
			new Color(0xfd, 0x59, 0x56), // grapefruit
			new Color(0xfc, 0xe1, 0x66), // sand yellow
			new Color(0xb2, 0x71, 0x3d), // clay brown
			new Color(0x1f, 0x3b, 0x4d), // dark blue grey
			new Color(0x69, 0x9d, 0x4c), // flat green
			new Color(0x56, 0xfc, 0xa2), // light green blue
			new Color(0xfb, 0x55, 0x81), // warm pink
			new Color(0x3e, 0x82, 0xfc), // dodger blue
			new Color(0xa0, 0xbf, 0x16), // gross green
			new Color(0xd6, 0xff, 0xfa), // ice
			new Color(0x4f, 0x73, 0x8e), // metallic blue
			new Color(0xff, 0xb1, 0x9a), // pale salmon
			new Color(0x5c, 0x8b, 0x15), // sap green
			new Color(0x54, 0xac, 0x68), // algae
			new Color(0x89, 0xa0, 0xb0), // bluey grey
			new Color(0x7e, 0xa0, 0x7a), // greeny grey
			new Color(0x1b, 0xfc, 0x06), // highlighter green
			new Color(0xca, 0xff, 0xfb), // light light blue
			new Color(0xb6, 0xff, 0xbb), // light mint
			new Color(0xa7, 0x5e, 0x09), // raw umber
			new Color(0x15, 0x2e, 0xff), // vivid blue
			new Color(0x8d, 0x5e, 0xb7), // deep lavender
			new Color(0x5f, 0x9e, 0x8f), // dull teal
			new Color(0x63, 0xf7, 0xb4), // light greenish blue
			new Color(0x60, 0x66, 0x02), // mud green
			new Color(0xfc, 0x86, 0xaa), // pinky
			new Color(0x8c, 0x00, 0x34), // red wine
			new Color(0x75, 0x80, 0x00), // shit green
			new Color(0xab, 0x7e, 0x4c), // tan brown
			new Color(0x03, 0x07, 0x64), // darkblue
			new Color(0xfe, 0x86, 0xa4), // rosa
			new Color(0xd5, 0x17, 0x4e), // lipstick
			new Color(0xfe, 0xd0, 0xfc), // pale mauve
			new Color(0x68, 0x00, 0x18), // claret
			new Color(0xfe, 0xdf, 0x08), // dandelion
			new Color(0xfe, 0x42, 0x0f), // orangered
			new Color(0x6f, 0x7c, 0x00), // poop green
			new Color(0xca, 0x01, 0x47), // ruby
			new Color(0x1b, 0x24, 0x31), // dark
			new Color(0x00, 0xfb, 0xb0), // greenish turquoise
			new Color(0xdb, 0x58, 0x56), // pastel red
			new Color(0xdd, 0xd6, 0x18), // piss yellow
			new Color(0x41, 0xfd, 0xfe), // bright cyan
			new Color(0xcf, 0x52, 0x4e), // dark coral
			new Color(0x21, 0xc3, 0x6f), // algae green
			new Color(0xa9, 0x03, 0x08), // darkish red
			new Color(0x6e, 0x10, 0x05), // reddy brown
			new Color(0xfe, 0x82, 0x8c), // blush pink
			new Color(0x4b, 0x61, 0x13), // camouflage green
			new Color(0x4d, 0xa4, 0x09), // lawn green
			new Color(0xbe, 0xae, 0x8a), // putty
			new Color(0x03, 0x39, 0xf8), // vibrant blue
			new Color(0xa8, 0x8f, 0x59), // dark sand
			new Color(0x5d, 0x21, 0xd0), // purple/blue
			new Color(0xfe, 0xb2, 0x09), // saffron
			new Color(0x4e, 0x51, 0x8b), // twilight
			new Color(0x96, 0x4e, 0x02), // warm brown
			new Color(0x85, 0xa3, 0xb2), // bluegrey
			new Color(0xff, 0x69, 0xaf), // bubble gum pink
			new Color(0xc3, 0xfb, 0xf4), // duck egg blue
			new Color(0x2a, 0xfe, 0xb7), // greenish cyan
			new Color(0x00, 0x5f, 0x6a), // petrol
			new Color(0x0c, 0x17, 0x93), // royal
			new Color(0xff, 0xff, 0x81), // butter
			new Color(0xf0, 0x83, 0x3a), // dusty orange
			new Color(0xf1, 0xf3, 0x3f), // off yellow
			new Color(0xb1, 0xd2, 0x7b), // pale olive green
			new Color(0xfc, 0x82, 0x4a), // orangish
			new Color(0x71, 0xaa, 0x34), // leaf
			new Color(0xb7, 0xc9, 0xe2), // light blue grey
			new Color(0x4b, 0x01, 0x01), // dried blood
			new Color(0xa5, 0x52, 0xe6), // lightish purple
			new Color(0xaf, 0x2f, 0x0d), // rusty red
			new Color(0x8b, 0x88, 0xf8), // lavender blue
			new Color(0x9a, 0xf7, 0x64), // light grass green
			new Color(0xa6, 0xfb, 0xb2), // light mint green
			new Color(0xff, 0xc5, 0x12), // sunflower
			new Color(0x75, 0x08, 0x51), // velvet
			new Color(0xc1, 0x4a, 0x09), // brick orange
			new Color(0xfe, 0x2f, 0x4a), // lightish red
			new Color(0x02, 0x03, 0xe2), // pure blue
			new Color(0x0a, 0x43, 0x7a), // twilight blue
			new Color(0xa5, 0x00, 0x55), // violet red
			new Color(0xae, 0x8b, 0x0c), // yellowy brown
			new Color(0xfd, 0x79, 0x8f), // carnation
			new Color(0xbf, 0xac, 0x05), // muddy yellow
			new Color(0x3e, 0xaf, 0x76), // dark seafoam green
			new Color(0xc7, 0x47, 0x67), // deep rose
			new Color(0xb9, 0x48, 0x4e), // dusty red
			new Color(0x64, 0x7d, 0x8e), // grey/blue
			new Color(0xbf, 0xfe, 0x28), // lemon lime
			new Color(0xd7, 0x25, 0xde), // purple/pink
			new Color(0xb2, 0x97, 0x05), // brown yellow
			new Color(0x67, 0x3a, 0x3f), // purple brown
			new Color(0xa8, 0x7d, 0xc2), // wisteria
			new Color(0xfa, 0xfe, 0x4b), // banana yellow
			new Color(0xc0, 0x02, 0x2f), // lipstick red
			new Color(0x0e, 0x87, 0xcc), // water blue
			new Color(0x8d, 0x84, 0x68), // brown grey
			new Color(0xad, 0x03, 0xde), // vibrant purple
			new Color(0x8c, 0xff, 0x9e), // baby green
			new Color(0x94, 0xac, 0x02), // barf green
			new Color(0xc4, 0xff, 0xf7), // eggshell blue
			new Color(0xfd, 0xee, 0x73), // sandy yellow
			new Color(0x33, 0xb8, 0x64), // cool green
			new Color(0xff, 0xf9, 0xd0), // pale
			new Color(0x75, 0x8d, 0xa3), // blue/grey
			new Color(0xf5, 0x04, 0xc9), // hot magenta
			new Color(0x77, 0xa1, 0xb5), // greyblue
			new Color(0x87, 0x56, 0xe4), // purpley
			new Color(0x88, 0x97, 0x17), // baby shit green
			new Color(0xc2, 0x7e, 0x79), // brownish pink
			new Color(0x01, 0x73, 0x71), // dark aquamarine
			new Color(0x9f, 0x83, 0x03), // diarrhea
			new Color(0xf7, 0xd5, 0x60), // light mustard
			new Color(0xbd, 0xf6, 0xfe), // pale sky blue
			new Color(0x75, 0xb8, 0x4f), // turtle green
			new Color(0x9c, 0xbb, 0x04), // bright olive
			new Color(0x29, 0x46, 0x5b), // dark grey blue
			new Color(0x69, 0x60, 0x06), // greeny brown
			new Color(0xad, 0xf8, 0x02), // lemon green
			new Color(0xc1, 0xc6, 0xfc), // light periwinkle
			new Color(0x35, 0xad, 0x6b), // seaweed green
			new Color(0xff, 0xfd, 0x37), // sunshine yellow
			new Color(0xa4, 0x42, 0xa0), // ugly purple
			new Color(0xf3, 0x61, 0x96), // medium pink
			new Color(0x94, 0x77, 0x06), // puke brown
			new Color(0xff, 0xf4, 0xf2), // very light pink
			new Color(0x1e, 0x91, 0x67), // viridian
			new Color(0xb5, 0xc3, 0x06), // bile
			new Color(0xfe, 0xff, 0x7f), // faded yellow
			new Color(0xcf, 0xfd, 0xbc), // very pale green
			new Color(0x0a, 0xdd, 0x08), // vibrant green
			new Color(0x87, 0xfd, 0x05), // bright lime
			new Color(0x1e, 0xf8, 0x76), // spearmint
			new Color(0x7b, 0xfd, 0xc7), // light aquamarine
			new Color(0xbc, 0xec, 0xac), // light sage
			new Color(0xbb, 0xf9, 0x0f), // yellowgreen
			new Color(0xab, 0x90, 0x04), // baby poo
			new Color(0x1f, 0xb5, 0x7a), // dark seafoam
			new Color(0x00, 0x55, 0x5a), // deep teal
			new Color(0xa4, 0x84, 0xac), // heather
			new Color(0xc4, 0x55, 0x08), // rust orange
			new Color(0x3f, 0x82, 0x9d), // dirty blue
			new Color(0x54, 0x8d, 0x44), // fern green
			new Color(0xc9, 0x5e, 0xfb), // bright lilac
			new Color(0x3a, 0xe5, 0x7f), // weird green
			new Color(0x01, 0x67, 0x95), // peacock blue
			new Color(0x87, 0xa9, 0x22), // avocado green
			new Color(0xf0, 0x94, 0x4d), // faded orange
			new Color(0x5d, 0x14, 0x51), // grape purple
			new Color(0x25, 0xff, 0x29), // hot green
			new Color(0xd0, 0xfe, 0x1d), // lime yellow
			new Color(0xff, 0xa6, 0x2b), // mango
			new Color(0x01, 0xb4, 0x4c), // shamrock
			new Color(0xff, 0x6c, 0xb5), // bubblegum
			new Color(0x6b, 0x42, 0x47), // purplish brown
			new Color(0xc7, 0xc1, 0x0c), // vomit yellow
			new Color(0xb7, 0xff, 0xfa), // pale cyan
			new Color(0xae, 0xff, 0x6e), // key lime
			new Color(0xec, 0x2d, 0x01), // tomato red
			new Color(0x76, 0xff, 0x7b), // lightgreen
			new Color(0x73, 0x00, 0x39), // merlot
			new Color(0x04, 0x03, 0x48), // night blue
			new Color(0xdf, 0x4e, 0xc8), // purpleish pink
			new Color(0x6e, 0xcb, 0x3c), // apple
			new Color(0x8f, 0x98, 0x05), // baby poop green
			new Color(0x5e, 0xdc, 0x1f), // green apple
			new Color(0xd9, 0x4f, 0xf5), // heliotrope
			new Color(0xc8, 0xfd, 0x3d), // yellow/green
			new Color(0x07, 0x0d, 0x0d), // almost black
			new Color(0x49, 0x84, 0xb8), // cool blue
			new Color(0x51, 0xb7, 0x3b), // leafy green
			new Color(0xac, 0x7e, 0x04), // mustard brown
			new Color(0x4e, 0x54, 0x81), // dusk
			new Color(0x87, 0x6e, 0x4b), // dull brown
			new Color(0x58, 0xbc, 0x08), // frog green
			new Color(0x2f, 0xef, 0x10), // vivid green
			new Color(0x2d, 0xfe, 0x54), // bright light green
			new Color(0x0a, 0xff, 0x02), // fluro green
			new Color(0x9c, 0xef, 0x43), // kiwi
			new Color(0x18, 0xd1, 0x7b), // seaweed
			new Color(0x35, 0x53, 0x0a), // navy green
			new Color(0x18, 0x05, 0xdb), // ultramarine blue
			new Color(0x62, 0x58, 0xc4), // iris
			new Color(0xff, 0x96, 0x4f), // pastel orange
			new Color(0xff, 0xab, 0x0f), // yellowish orange
			new Color(0x8f, 0x8c, 0xe7), // perrywinkle
			new Color(0x24, 0xbc, 0xa8), // tealish
			new Color(0x3f, 0x01, 0x2c), // dark plum
			new Color(0xcb, 0xf8, 0x5f), // pear
			new Color(0xff, 0x72, 0x4c), // pinkish orange
			new Color(0x28, 0x01, 0x37), // midnight purple
			new Color(0xb3, 0x6f, 0xf6), // light urple
			new Color(0x48, 0xc0, 0x72), // dark mint
			new Color(0xbc, 0xcb, 0x7a), // greenish tan
			new Color(0xa8, 0x41, 0x5b), // light burgundy
			new Color(0x06, 0xb1, 0xc4), // turquoise blue
			new Color(0xcd, 0x75, 0x84), // ugly pink
			new Color(0xf1, 0xda, 0x7a), // sandy
			new Color(0xff, 0x04, 0x90), // electric pink
			new Color(0x80, 0x5b, 0x87), // muted purple
			new Color(0x50, 0xa7, 0x47), // mid green
			new Color(0xa8, 0xa4, 0x95), // greyish
			new Color(0xcf, 0xff, 0x04), // neon yellow
			new Color(0xff, 0xff, 0x7e), // banana
			new Color(0xff, 0x7f, 0xa7), // carnation pink
			new Color(0xef, 0x40, 0x26), // tomato
			new Color(0x3c, 0x99, 0x92), // sea
			new Color(0x88, 0x68, 0x06), // muddy brown
			new Color(0x04, 0xf4, 0x89), // turquoise green
			new Color(0xfe, 0xf6, 0x9e), // buff
			new Color(0xcf, 0xaf, 0x7b), // fawn
			new Color(0x3b, 0x71, 0x9f), // muted blue
			new Color(0xfd, 0xc1, 0xc5), // pale rose
			new Color(0x20, 0xc0, 0x73), // dark mint green
			new Color(0x9b, 0x5f, 0xc0), // amethyst
			new Color(0x0f, 0x9b, 0x8e), // blue/green
			new Color(0x74, 0x28, 0x02), // chestnut
			new Color(0x9d, 0xb9, 0x2c), // sick green
			new Color(0xa4, 0xbf, 0x20), // pea
			new Color(0xcd, 0x59, 0x09), // rusty orange
			new Color(0xad, 0xa5, 0x87), // stone
			new Color(0xbe, 0x01, 0x3c), // rose red
			new Color(0xb8, 0xff, 0xeb), // pale aqua
			new Color(0xdc, 0x4d, 0x01), // deep orange
			new Color(0xa2, 0x65, 0x3e), // earth
			new Color(0x63, 0x8b, 0x27), // mossy green
			new Color(0x41, 0x9c, 0x03), // grassy green
			new Color(0xb1, 0xff, 0x65), // pale lime green
			new Color(0x9d, 0xbc, 0xd4), // light grey blue
			new Color(0xfd, 0xfd, 0xfe), // pale grey
			new Color(0x77, 0xab, 0x56), // asparagus
			new Color(0x46, 0x41, 0x96), // blueberry
			new Color(0x99, 0x01, 0x47), // purple red
			new Color(0xbe, 0xfd, 0x73), // pale lime
			new Color(0x32, 0xbf, 0x84), // greenish teal
			new Color(0xaf, 0x6f, 0x09), // caramel
			new Color(0xa0, 0x02, 0x5c), // deep magenta
			new Color(0xff, 0xd8, 0xb1), // light peach
			new Color(0x7f, 0x4e, 0x1e), // milk chocolate
			new Color(0xbf, 0x9b, 0x0c), // ocher
			new Color(0x6b, 0xa3, 0x53), // off green
			new Color(0xf0, 0x75, 0xe6), // purply pink
			new Color(0x7b, 0xc8, 0xf6), // lightblue
			new Color(0x47, 0x5f, 0x94), // dusky blue
			new Color(0xf5, 0xbf, 0x03), // golden
			new Color(0xff, 0xfe, 0xb6), // light beige
			new Color(0xff, 0xfd, 0x74), // butter yellow
			new Color(0x89, 0x5b, 0x7b), // dusky purple
			new Color(0x43, 0x6b, 0xad), // french blue
			new Color(0xd0, 0xc1, 0x01), // ugly yellow
			new Color(0xc6, 0xf8, 0x08), // greeny yellow
			new Color(0xf4, 0x36, 0x05), // orangish red
			new Color(0x02, 0xc1, 0x4d), // shamrock green
			new Color(0xb2, 0x5f, 0x03), // orangish brown
			new Color(0x2a, 0x7e, 0x19), // tree green
			new Color(0x49, 0x06, 0x48), // deep violet
			new Color(0x53, 0x62, 0x67), // gunmetal
			new Color(0x5a, 0x06, 0xef), // blue/purple
			new Color(0xcf, 0x02, 0x34), // cherry
			new Color(0xc4, 0xa6, 0x61), // sandy brown
			new Color(0x97, 0x8a, 0x84), // warm grey
			new Color(0x1f, 0x09, 0x54), // dark indigo
			new Color(0x03, 0x01, 0x2d), // midnight
			new Color(0x2b, 0xb1, 0x79), // bluey green
			new Color(0xc3, 0x90, 0x9b), // grey pink
			new Color(0xa6, 0x6f, 0xb5), // soft purple
			new Color(0x77, 0x00, 0x01), // blood
			new Color(0x92, 0x2b, 0x05), // brown red
			new Color(0x7d, 0x7f, 0x7c), // medium grey
			new Color(0x99, 0x0f, 0x4b), // berry
			new Color(0x8f, 0x73, 0x03), // poo
			new Color(0xc8, 0x3c, 0xb9), // purpley pink
			new Color(0xfe, 0xa9, 0x93), // light salmon
			new Color(0xac, 0xbb, 0x0d), // snot
			new Color(0xc0, 0x71, 0xfe), // easter purple
			new Color(0xcc, 0xfd, 0x7f), // light yellow green
			new Color(0x00, 0x02, 0x2e), // dark navy blue
			new Color(0x82, 0x83, 0x44), // drab
			new Color(0xff, 0xc5, 0xcb), // light rose
			new Color(0xab, 0x12, 0x39), // rouge
			new Color(0xb0, 0x05, 0x4b), // purplish red
			new Color(0x99, 0xcc, 0x04), // slime green
			new Color(0x93, 0x7c, 0x00), // baby poop
			new Color(0x01, 0x95, 0x29), // irish green
			new Color(0xef, 0x1d, 0xe7), // pink/purple
			new Color(0x00, 0x04, 0x35), // dark navy
			new Color(0x42, 0xb3, 0x95), // greeny blue
			new Color(0x9d, 0x57, 0x83), // light plum
			new Color(0xc8, 0xac, 0xa9), // pinkish grey
			new Color(0xc8, 0x76, 0x06), // dirty orange
			new Color(0xaa, 0x27, 0x04), // rust red
			new Color(0xe4, 0xcb, 0xff), // pale lilac
			new Color(0xfa, 0x42, 0x24), // orangey red
			new Color(0x08, 0x04, 0xf9), // primary blue
			new Color(0x5c, 0xb2, 0x00), // kermit green
			new Color(0x76, 0x42, 0x4e), // brownish purple
			new Color(0x6c, 0x7a, 0x0e), // murky green
			new Color(0xfb, 0xdd, 0x7e), // wheat
			new Color(0x2a, 0x01, 0x34), // very dark purple
			new Color(0x04, 0x4a, 0x05), // bottle green
			new Color(0xfd, 0x46, 0x59), // watermelon
			new Color(0x0d, 0x75, 0xf8), // deep sky blue
			new Color(0xfe, 0x00, 0x02), // fire engine red
			new Color(0xcb, 0x9d, 0x06), // yellow ochre
			new Color(0xfb, 0x7d, 0x07), // pumpkin orange
			new Color(0xb9, 0xcc, 0x81), // pale olive
			new Color(0xed, 0xc8, 0xff), // light lilac
			new Color(0x61, 0xe1, 0x60), // lightish green
			new Color(0x8a, 0xb8, 0xfe), // carolina blue
			new Color(0x92, 0x0a, 0x4e), // mulberry
			new Color(0xfe, 0x02, 0xa2), // shocking pink
			new Color(0x9a, 0x30, 0x01), // auburn
			new Color(0x65, 0xfe, 0x08), // bright lime green
			new Color(0xbe, 0xfd, 0xb7), // celadon
			new Color(0xb1, 0x72, 0x61), // pinkish brown
			new Color(0x88, 0x5f, 0x01), // poo brown
			new Color(0x02, 0xcc, 0xfe), // bright sky blue
			new Color(0xc1, 0xfd, 0x95), // celery
			new Color(0x83, 0x65, 0x39), // dirt brown
			new Color(0xfb, 0x29, 0x43), // strawberry
			new Color(0x84, 0xb7, 0x01), // dark lime
			new Color(0xb6, 0x63, 0x25), // copper
			new Color(0x7f, 0x51, 0x12), // medium brown
			new Color(0x5f, 0xa0, 0x52), // muted green
			new Color(0x6d, 0xed, 0xfd), // robin's egg
			new Color(0x0b, 0xf9, 0xea), // bright aqua
			new Color(0xc7, 0x60, 0xff), // bright lavender
			new Color(0xff, 0xff, 0xcb), // ivory
			new Color(0xf6, 0xce, 0xfc), // very light purple
			new Color(0x15, 0x50, 0x84), // light navy
			new Color(0xf5, 0x05, 0x4f), // pink red
			new Color(0x64, 0x54, 0x03), // olive brown
			new Color(0x7a, 0x59, 0x01), // poop brown
			new Color(0xa8, 0xb5, 0x04), // mustard green
			new Color(0x3d, 0x99, 0x73), // ocean green
			new Color(0x00, 0x01, 0x33), // very dark blue
			new Color(0x76, 0xa9, 0x73), // dusty green
			new Color(0x2e, 0x5a, 0x88), // light navy blue
			new Color(0x0b, 0xf7, 0x7d), // minty green
			new Color(0xbd, 0x6c, 0x48), // adobe
			new Color(0xac, 0x1d, 0xb8), // barney
			new Color(0x2b, 0xaf, 0x6a), // jade green
			new Color(0x26, 0xf7, 0xfd), // bright light blue
			new Color(0xae, 0xfd, 0x6c), // light lime
			new Color(0x9b, 0x8f, 0x55), // dark khaki
			new Color(0xff, 0xad, 0x01), // orange yellow
			new Color(0xc6, 0x9c, 0x04), // ocre
			new Color(0xf4, 0xd0, 0x54), // maize
			new Color(0xde, 0x9d, 0xac), // faded pink
			new Color(0x05, 0x48, 0x0d), // british racing green
			new Color(0xc9, 0xae, 0x74), // sandstone
			new Color(0x60, 0x46, 0x0f), // mud brown
			new Color(0x98, 0xf6, 0xb0), // light sea green
			new Color(0x8a, 0xf1, 0xfe), // robin egg blue
			new Color(0x2e, 0xe8, 0xbb), // aqua marine
			new Color(0x11, 0x87, 0x5d), // dark sea green
			new Color(0xfd, 0xb0, 0xc0), // soft pink
			new Color(0xb1, 0x60, 0x02), // orangey brown
			new Color(0xf7, 0x02, 0x2a), // cherry red
			new Color(0xd5, 0xab, 0x09), // burnt yellow
			new Color(0x86, 0x77, 0x5f), // brownish grey
			new Color(0xc6, 0x9f, 0x59), // camel
			new Color(0x7a, 0x68, 0x7f), // purplish grey
			new Color(0x04, 0x2e, 0x60), // marine
			new Color(0xc8, 0x8d, 0x94), // greyish pink
			new Color(0xa5, 0xfb, 0xd5), // pale turquoise
			new Color(0xff, 0xfe, 0x71), // pastel yellow
			new Color(0x62, 0x41, 0xc7), // bluey purple
			new Color(0xff, 0xfe, 0x40), // canary yellow
			new Color(0xd3, 0x49, 0x4e), // faded red
			new Color(0x98, 0x5e, 0x2b), // sepia
			new Color(0xa6, 0x81, 0x4c), // coffee
			new Color(0xff, 0x08, 0xe8), // bright magenta
			new Color(0x9d, 0x76, 0x51), // mocha
			new Color(0xfe, 0xff, 0xca), // ecru
			new Color(0x98, 0x56, 0x8d), // purpleish
			new Color(0x9e, 0x00, 0x3a), // cranberry
			new Color(0x28, 0x7c, 0x37), // darkish green
			new Color(0xb9, 0x69, 0x02), // brown orange
			new Color(0xba, 0x68, 0x73), // dusky rose
			new Color(0xff, 0x78, 0x55), // melon
			new Color(0x94, 0xb2, 0x1c), // sickly green
			new Color(0xc5, 0xc9, 0xc7), // silver
			new Color(0x66, 0x1a, 0xee), // purply blue
			new Color(0x61, 0x40, 0xef), // purpleish blue
			new Color(0x9b, 0xe5, 0xaa), // hospital green
			new Color(0x7b, 0x58, 0x04), // shit brown
			new Color(0x27, 0x6a, 0xb3), // mid blue
			new Color(0xfe, 0xb3, 0x08), // amber
			new Color(0x8c, 0xfd, 0x7e), // easter green
			new Color(0x64, 0x88, 0xea), // soft blue
			new Color(0x05, 0x6e, 0xee), // cerulean blue
			new Color(0xb2, 0x7a, 0x01), // golden brown
			new Color(0x0f, 0xfe, 0xf9), // bright turquoise
			new Color(0xfa, 0x2a, 0x55), // red pink
			new Color(0x82, 0x07, 0x47), // red purple
			new Color(0x7a, 0x6a, 0x4f), // greyish brown
			new Color(0xf4, 0x32, 0x0c), // vermillion
			new Color(0xa1, 0x39, 0x05), // russet
			new Color(0x6f, 0x82, 0x8a), // steel grey
			new Color(0xa5, 0x5a, 0xf4), // lighter purple
			new Color(0xad, 0x0a, 0xfd), // bright violet
			new Color(0x00, 0x45, 0x77), // prussian blue
			new Color(0x65, 0x8d, 0x6d), // slate green
			new Color(0xca, 0x7b, 0x80), // dirty pink
			new Color(0x00, 0x52, 0x49), // dark blue green
			new Color(0x2b, 0x5d, 0x34), // pine
			new Color(0xbf, 0xf1, 0x28), // yellowy green
			new Color(0xb5, 0x94, 0x10), // dark gold
			new Color(0x29, 0x76, 0xbb), // bluish
			new Color(0x01, 0x41, 0x82), // darkish blue
			new Color(0xbb, 0x3f, 0x3f), // dull red
			new Color(0xfc, 0x26, 0x47), // pinky red
			new Color(0xa8, 0x79, 0x00), // bronze
			new Color(0x82, 0xcb, 0xb2), // pale teal
			new Color(0x66, 0x7c, 0x3e), // military green
			new Color(0xfe, 0x46, 0xa5), // barbie pink
			new Color(0xfe, 0x83, 0xcc), // bubblegum pink
			new Color(0x94, 0xa6, 0x17), // pea soup green
			new Color(0xa8, 0x89, 0x05), // dark mustard
			new Color(0x7f, 0x5f, 0x00), // shit
			new Color(0x9e, 0x43, 0xa2), // medium purple
			new Color(0x06, 0x2e, 0x03), // very dark green
			new Color(0x8a, 0x6e, 0x45), // dirt
			new Color(0xcc, 0x7a, 0x8b), // dusky pink
			new Color(0x9e, 0x01, 0x68), // red violet
			new Color(0xfd, 0xff, 0x38), // lemon yellow
			new Color(0xc0, 0xfa, 0x8b), // pistachio
			new Color(0xee, 0xdc, 0x5b), // dull yellow
			new Color(0x7e, 0xbd, 0x01), // dark lime green
			new Color(0x3b, 0x5b, 0x92), // denim blue
			new Color(0x01, 0x88, 0x9f), // teal blue
			new Color(0x3d, 0x7a, 0xfd), // lightish blue
			new Color(0x5f, 0x34, 0xe7), // purpley blue
			new Color(0x6d, 0x5a, 0xcf), // light indigo
			new Color(0x74, 0x85, 0x00), // swamp green
			new Color(0x70, 0x6c, 0x11), // brown green
			new Color(0x3c, 0x00, 0x08), // dark maroon
			new Color(0xcb, 0x00, 0xf5), // hot purple
			new Color(0x00, 0x2d, 0x04), // dark forest green
			new Color(0x65, 0x8c, 0xbb), // faded blue
			new Color(0x74, 0x95, 0x51), // drab green
			new Color(0xb9, 0xff, 0x66), // light lime green
			new Color(0x9d, 0xc1, 0x00), // snot green
			new Color(0xfa, 0xee, 0x66), // yellowish
			new Color(0x7e, 0xfb, 0xb3), // light blue green
			new Color(0x7b, 0x00, 0x2c), // bordeaux
			new Color(0xc2, 0x92, 0xa1), // light mauve
			new Color(0x01, 0x7b, 0x92), // ocean
			new Color(0xfc, 0xc0, 0x06), // marigold
			new Color(0x65, 0x74, 0x32), // muddy green
			new Color(0xd8, 0x86, 0x3b), // dull orange
			new Color(0x73, 0x85, 0x95), // steel
			new Color(0xaa, 0x23, 0xff), // electric purple
			new Color(0x08, 0xff, 0x08), // fluorescent green
			new Color(0x9b, 0x7a, 0x01), // yellowish brown
			new Color(0xf2, 0x9e, 0x8e), // blush
			new Color(0x6f, 0xc2, 0x76), // soft green
			new Color(0xff, 0x5b, 0x00), // bright orange
			new Color(0xfd, 0xff, 0x52), // lemon
			new Color(0x86, 0x6f, 0x85), // purple grey
			new Color(0x8f, 0xfe, 0x09), // acid green
			new Color(0xee, 0xcf, 0xfe), // pale lavender
			new Color(0x51, 0x0a, 0xc9), // violet blue
			new Color(0x4f, 0x91, 0x53), // light forest green
			new Color(0x9f, 0x23, 0x05), // burnt red
			new Color(0x72, 0x86, 0x39), // khaki green
			new Color(0xde, 0x0c, 0x62), // cerise
			new Color(0x91, 0x6e, 0x99), // faded purple
			new Color(0xff, 0xb1, 0x6d), // apricot
			new Color(0x3c, 0x4d, 0x03), // dark olive green
			new Color(0x7f, 0x70, 0x53), // grey brown
			new Color(0x77, 0x92, 0x6f), // green grey
			new Color(0x01, 0x0f, 0xcc), // true blue
			new Color(0xce, 0xae, 0xfa), // pale violet
			new Color(0x8f, 0x99, 0xfb), // periwinkle blue
			new Color(0xc6, 0xfc, 0xff), // light sky blue
			new Color(0x55, 0x39, 0xcc), // blurple
			new Color(0x54, 0x4e, 0x03), // green brown
			new Color(0x01, 0x7a, 0x79), // bluegreen
			new Color(0x01, 0xf9, 0xc6), // bright teal
			new Color(0xc9, 0xb0, 0x03), // brownish yellow
			new Color(0x92, 0x99, 0x01), // pea soup
			new Color(0x0b, 0x55, 0x09), // forest
			new Color(0xa0, 0x04, 0x98), // barney purple
			new Color(0x20, 0x00, 0xb1), // ultramarine
			new Color(0x94, 0x56, 0x8c), // purplish
			new Color(0xc2, 0xbe, 0x0e), // puke yellow
			new Color(0x74, 0x8b, 0x97), // bluish grey
			new Color(0x66, 0x5f, 0xd1), // dark periwinkle
			new Color(0x9c, 0x6d, 0xa5), // dark lilac
			new Color(0xc4, 0x42, 0x40), // reddish
			new Color(0xa2, 0x48, 0x57), // light maroon
			new Color(0x82, 0x5f, 0x87), // dusty purple
			new Color(0xc9, 0x64, 0x3b), // terra cotta
			new Color(0x90, 0xb1, 0x34), // avocado
			new Color(0x01, 0x38, 0x6a), // marine blue
			new Color(0x25, 0xa3, 0x6f), // teal green
			new Color(0x59, 0x65, 0x6d), // slate grey
			new Color(0x75, 0xfd, 0x63), // lighter green
			new Color(0x21, 0xfc, 0x0d), // electric green
			new Color(0x5a, 0x86, 0xad), // dusty blue
			new Color(0xfe, 0xc6, 0x15), // golden yellow
			new Color(0xff, 0xfd, 0x01), // bright yellow
			new Color(0xdf, 0xc5, 0xfe), // light lavender
			new Color(0xb2, 0x64, 0x00), // umber
			new Color(0x7f, 0x5e, 0x00), // poop
			new Color(0xde, 0x7e, 0x5d), // dark peach
			new Color(0x04, 0x82, 0x43), // jungle green
			new Color(0xff, 0xff, 0xd4), // eggshell
			new Color(0x3b, 0x63, 0x8c), // denim
			new Color(0xb7, 0x94, 0x00), // yellow brown
			new Color(0x84, 0x59, 0x7e), // dull purple
			new Color(0x41, 0x19, 0x00), // chocolate brown
			new Color(0x7b, 0x03, 0x23), // wine red
			new Color(0x04, 0xd9, 0xff), // neon blue
			new Color(0x66, 0x7e, 0x2c), // dirty green
			new Color(0xfb, 0xee, 0xac), // light tan
			new Color(0xd7, 0xff, 0xfe), // ice blue
			new Color(0x4e, 0x74, 0x96), // cadet blue
			new Color(0x87, 0x4c, 0x62), // dark mauve
			new Color(0xd5, 0xff, 0xff), // very light blue
			new Color(0x82, 0x6d, 0x8c), // grey purple
			new Color(0xff, 0xba, 0xcd), // pastel pink
			new Color(0xd1, 0xff, 0xbd), // very light green
			new Color(0x44, 0x8e, 0xe4), // dark sky blue
			new Color(0x05, 0x47, 0x2a), // evergreen
			new Color(0xd5, 0x86, 0x9d), // dull pink
			new Color(0x3d, 0x07, 0x34), // aubergine
			new Color(0x4a, 0x01, 0x00), // mahogany
			new Color(0xf8, 0x48, 0x1c), // reddish orange
			new Color(0x02, 0x59, 0x0f), // deep green
			new Color(0x89, 0xa2, 0x03), // vomit green
			new Color(0xe0, 0x3f, 0xd8), // purple pink
			new Color(0xd5, 0x8a, 0x94), // dusty pink
			new Color(0x7b, 0xb2, 0x74), // faded green
			new Color(0x52, 0x65, 0x25), // camo green
			new Color(0xc9, 0x4c, 0xbe), // pinky purple
			new Color(0xdb, 0x4b, 0xda), // pink purple
			new Color(0x9e, 0x36, 0x23), // brownish red
			new Color(0xb5, 0x48, 0x5d), // dark rose
			new Color(0x73, 0x5c, 0x12), // mud
			new Color(0x9c, 0x6d, 0x57), // brownish
			new Color(0x02, 0x8f, 0x1e), // emerald green
			new Color(0xb1, 0x91, 0x6e), // pale brown
			new Color(0x49, 0x75, 0x9c), // dull blue
			new Color(0xa0, 0x45, 0x0e), // burnt umber
			new Color(0x39, 0xad, 0x48), // medium green
			new Color(0xb6, 0x6a, 0x50), // clay
			new Color(0x8c, 0xff, 0xdb), // light aqua
			new Color(0xa4, 0xbe, 0x5c), // light olive green
			new Color(0xcb, 0x77, 0x23), // brownish orange
			new Color(0x05, 0x69, 0x6b), // dark aqua
			new Color(0xce, 0x5d, 0xae), // purplish pink
			new Color(0xc8, 0x5a, 0x53), // dark salmon
			new Color(0x96, 0xae, 0x8d), // greenish grey
			new Color(0x1f, 0xa7, 0x74), // jade
			new Color(0x7a, 0x97, 0x03), // ugly green
			new Color(0xac, 0x93, 0x62), // dark beige
			new Color(0x01, 0xa0, 0x49), // emerald
			new Color(0xd9, 0x54, 0x4d), // pale red
			new Color(0xfa, 0x5f, 0xf7), // light magenta
			new Color(0x82, 0xca, 0xfc), // sky
			new Color(0xac, 0xff, 0xfc), // light cyan
			new Color(0xfc, 0xb0, 0x01), // yellow orange
			new Color(0x91, 0x09, 0x51), // reddish purple
			new Color(0xfe, 0x2c, 0x54), // reddish pink
			new Color(0xc8, 0x75, 0xc4), // orchid
			new Color(0xcd, 0xc5, 0x0a), // dirty yellow
			new Color(0xfd, 0x41, 0x1e), // orange red
			new Color(0x9a, 0x02, 0x00), // deep red
			new Color(0xbe, 0x64, 0x00), // orange brown
			new Color(0x03, 0x0a, 0xa7), // cobalt blue
			new Color(0xfe, 0x01, 0x9a), // neon pink
			new Color(0xf7, 0x87, 0x9a), // rose pink
			new Color(0x88, 0x71, 0x91), // greyish purple
			new Color(0xb0, 0x01, 0x49), // raspberry
			new Color(0x12, 0xe1, 0x93), // aqua green
			new Color(0xfe, 0x7b, 0x7c), // salmon pink
			new Color(0xff, 0x94, 0x08), // tangerine
			new Color(0x6a, 0x6e, 0x09), // brownish green
			new Color(0x8b, 0x2e, 0x16), // red brown
			new Color(0x69, 0x61, 0x12), // greenish brown
			new Color(0xe1, 0x77, 0x01), // pumpkin
			new Color(0x0a, 0x48, 0x1e), // pine green
			new Color(0x34, 0x38, 0x37), // charcoal
			new Color(0xff, 0xb7, 0xce), // baby pink
			new Color(0x6a, 0x79, 0xf7), // cornflower
			new Color(0x5d, 0x06, 0xe9), // blue violet
			new Color(0x3d, 0x1c, 0x02), // chocolate
			new Color(0x82, 0xa6, 0x7d), // greyish green
			new Color(0xbe, 0x01, 0x19), // scarlet
			new Color(0xc9, 0xff, 0x27), // green yellow
			new Color(0x37, 0x3e, 0x02), // dark olive
			new Color(0xa9, 0x56, 0x1e), // sienna
			new Color(0xca, 0xa0, 0xff), // pastel purple
			new Color(0xca, 0x66, 0x41), // terracotta
			new Color(0x02, 0xd8, 0xe9), // aqua blue
			new Color(0x88, 0xb3, 0x78), // sage green
			new Color(0x98, 0x00, 0x02), // blood red
			new Color(0xcb, 0x01, 0x62), // deep pink
			new Color(0x5c, 0xac, 0x2d), // grass
			new Color(0x76, 0x99, 0x58), // moss
			new Color(0xa2, 0xbf, 0xfe), // pastel blue
			new Color(0x10, 0xa6, 0x74), // bluish green
			new Color(0x06, 0xb4, 0x8b), // green blue
			new Color(0xaf, 0x88, 0x4a), // dark tan
			new Color(0x0b, 0x8b, 0x87), // greenish blue
			new Color(0xff, 0xa7, 0x56), // pale orange
			new Color(0xa2, 0xa4, 0x15), // vomit
			new Color(0x15, 0x44, 0x06), // forrest green
			new Color(0x85, 0x67, 0x98), // dark lavender
			new Color(0x34, 0x01, 0x3f), // dark violet
			new Color(0x63, 0x2d, 0xe9), // purple blue
			new Color(0x0a, 0x88, 0x8a), // dark cyan
			new Color(0x6f, 0x76, 0x32), // olive drab
			new Color(0xd4, 0x6a, 0x7e), // pinkish
			new Color(0x1e, 0x48, 0x8f), // cobalt
			new Color(0xbc, 0x13, 0xfe), // neon purple
			new Color(0x7e, 0xf4, 0xcc), // light turquoise
			new Color(0x76, 0xcd, 0x26), // apple green
			new Color(0x74, 0xa6, 0x62), // dull green
			new Color(0x80, 0x01, 0x3f), // wine
			new Color(0xb1, 0xd1, 0xfc), // powder blue
			new Color(0xff, 0xff, 0xe4), // off white
			new Color(0x06, 0x52, 0xff), // electric blue
			new Color(0x04, 0x5c, 0x5a), // dark turquoise
			new Color(0x57, 0x29, 0xce), // blue purple
			new Color(0x06, 0x9a, 0xf3), // azure
			new Color(0xff, 0x00, 0x0d), // bright red
			new Color(0xf1, 0x0c, 0x45), // pinkish red
			new Color(0x51, 0x70, 0xd7), // cornflower blue
			new Color(0xac, 0xbf, 0x69), // light olive
			new Color(0x6c, 0x34, 0x61), // grape
			new Color(0x5e, 0x81, 0x9d), // greyish blue
			new Color(0x60, 0x1e, 0xf9), // purplish blue
			new Color(0xb0, 0xdd, 0x16), // yellowish green
			new Color(0xcd, 0xfd, 0x02), // greenish yellow
			new Color(0x2c, 0x6f, 0xbb), // medium blue
			new Color(0xc0, 0x73, 0x7a), // dusty rose
			new Color(0xd6, 0xb4, 0xfc), // light violet
			new Color(0x02, 0x00, 0x35), // midnight blue
			new Color(0x70, 0x3b, 0xe7), // bluish purple
			new Color(0xfd, 0x3c, 0x06), // red orange
			new Color(0x96, 0x00, 0x56), // dark magenta
			new Color(0x40, 0xa3, 0x68), // greenish
			new Color(0x03, 0x71, 0x9c), // ocean blue
			new Color(0xfc, 0x5a, 0x50), // coral
			new Color(0xff, 0xff, 0xc2), // cream
			new Color(0x7f, 0x2b, 0x0a), // reddish brown
			new Color(0xb0, 0x4e, 0x0f), // burnt sienna
			new Color(0xa0, 0x36, 0x23), // brick
			new Color(0x87, 0xae, 0x73), // sage
			new Color(0x78, 0x9b, 0x73), // grey green
			new Color(0xff, 0xff, 0xff), // white
			new Color(0x98, 0xef, 0xf9), // robin's egg blue
			new Color(0x65, 0x8b, 0x38), // moss green
			new Color(0x5a, 0x7d, 0x9a), // steel blue
			new Color(0x38, 0x08, 0x35), // eggplant
			new Color(0xff, 0xfe, 0x7a), // light yellow
			new Color(0x5c, 0xa9, 0x04), // leaf green
			new Color(0xd8, 0xdc, 0xd6), // light grey
			new Color(0xa5, 0xa5, 0x02), // puke
			new Color(0xd6, 0x48, 0xd7), // pinkish purple
			new Color(0x04, 0x74, 0x95), // sea blue
			new Color(0xb7, 0x90, 0xd4), // pale purple
			new Color(0x5b, 0x7c, 0x99), // slate blue
			new Color(0x60, 0x7c, 0x8e), // blue grey
			new Color(0x0b, 0x40, 0x08), // hunter green
			new Color(0xed, 0x0d, 0xd9), // fuchsia
			new Color(0x8c, 0x00, 0x0f), // crimson
			new Color(0xff, 0xff, 0x84), // pale yellow
			new Color(0xbf, 0x90, 0x05), // ochre
			new Color(0xd2, 0xbd, 0x0a), // mustard yellow
			new Color(0xff, 0x47, 0x4c), // light red
			new Color(0x04, 0x85, 0xd1), // cerulean
			new Color(0xff, 0xcf, 0xdc), // pale pink
			new Color(0x04, 0x02, 0x73), // deep blue
			new Color(0xa8, 0x3c, 0x09), // rust
			new Color(0x90, 0xe4, 0xc1), // light teal
			new Color(0x51, 0x65, 0x72), // slate
			new Color(0xfa, 0xc2, 0x05), // goldenrod
			new Color(0xd5, 0xb6, 0x0a), // dark yellow
			new Color(0x36, 0x37, 0x37), // dark grey
			new Color(0x4b, 0x5d, 0x16), // army green
			new Color(0x6b, 0x8b, 0xa4), // grey blue
			new Color(0x80, 0xf9, 0xad), // seafoam
			new Color(0xa5, 0x7e, 0x52), // puce
			new Color(0xa9, 0xf9, 0x71), // spring green
			new Color(0xc6, 0x51, 0x02), // dark orange
			new Color(0xe2, 0xca, 0x76), // sand
			new Color(0xb0, 0xff, 0x9d), // pastel green
			new Color(0x9f, 0xfe, 0xb0), // mint
			new Color(0xfd, 0xaa, 0x48), // light orange
			new Color(0xfe, 0x01, 0xb1), // bright pink
			new Color(0xc1, 0xf8, 0x0a), // chartreuse
			new Color(0x36, 0x01, 0x3f), // deep purple
			new Color(0x34, 0x1c, 0x02), // dark brown
			new Color(0xb9, 0xa2, 0x81), // taupe
			new Color(0x8e, 0xab, 0x12), // pea green
			new Color(0x9a, 0xae, 0x07), // puke green
			new Color(0x02, 0xab, 0x2e), // kelly green
			new Color(0x7a, 0xf9, 0xab), // seafoam green
			new Color(0x13, 0x7e, 0x6d), // blue green
			new Color(0xaa, 0xa6, 0x62), // khaki
			new Color(0x61, 0x00, 0x23), // burgundy
			new Color(0x01, 0x4d, 0x4e), // dark teal
			new Color(0x8f, 0x14, 0x02), // brick red
			new Color(0x4b, 0x00, 0x6e), // royal purple
			new Color(0x58, 0x0f, 0x41), // plum
			new Color(0x8f, 0xff, 0x9f), // mint green
			new Color(0xdb, 0xb4, 0x0c), // gold
			new Color(0xa2, 0xcf, 0xfe), // baby blue
			new Color(0xc0, 0xfb, 0x2d), // yellow green
			new Color(0xbe, 0x03, 0xfd), // bright purple
			new Color(0x84, 0x00, 0x00), // dark red
			new Color(0xd0, 0xfe, 0xfe), // pale blue
			new Color(0x3f, 0x9b, 0x0b), // grass green
			new Color(0x01, 0x15, 0x3e), // navy
			new Color(0x04, 0xd8, 0xb2), // aquamarine
			new Color(0xc0, 0x4e, 0x01), // burnt orange
			new Color(0x0c, 0xff, 0x0c), // neon green
			new Color(0x01, 0x65, 0xfc), // bright blue
			new Color(0xcf, 0x62, 0x75), // rose
			new Color(0xff, 0xd1, 0xdf), // light pink
			new Color(0xce, 0xb3, 0x01), // mustard
			new Color(0x38, 0x02, 0x82), // indigo
			new Color(0xaa, 0xff, 0x32), // lime
			new Color(0x53, 0xfc, 0xa1), // sea green
			new Color(0x8e, 0x82, 0xfe), // periwinkle
			new Color(0xcb, 0x41, 0x6b), // dark pink
			new Color(0x67, 0x7a, 0x04), // olive green
			new Color(0xff, 0xb0, 0x7c), // peach
			new Color(0xc7, 0xfd, 0xb5), // pale green
			new Color(0xad, 0x81, 0x50), // light brown
			new Color(0xff, 0x02, 0x8d), // hot pink
			new Color(0x00, 0x00, 0x00), // black
			new Color(0xce, 0xa2, 0xfd), // lilac
			new Color(0x00, 0x11, 0x46), // navy blue
			new Color(0x05, 0x04, 0xaa), // royal blue
			new Color(0xe6, 0xda, 0xa6), // beige
			new Color(0xff, 0x79, 0x6c), // salmon
			new Color(0x6e, 0x75, 0x0e), // olive
			new Color(0x65, 0x00, 0x21), // maroon
			new Color(0x01, 0xff, 0x07), // bright green
			new Color(0x35, 0x06, 0x3e), // dark purple
			new Color(0xae, 0x71, 0x81), // mauve
			new Color(0x06, 0x47, 0x0c), // forest green
			new Color(0x13, 0xea, 0xc9), // aqua
			new Color(0x00, 0xff, 0xff), // cyan
			new Color(0xd1, 0xb2, 0x6f), // tan
			new Color(0x00, 0x03, 0x5b), // dark blue
			new Color(0xc7, 0x9f, 0xef), // lavender
			new Color(0x06, 0xc2, 0xac), // turquoise
			new Color(0x03, 0x35, 0x00), // dark green
			new Color(0x9a, 0x0e, 0xea), // violet
			new Color(0xbf, 0x77, 0xf6), // light purple
			new Color(0x89, 0xfe, 0x05), // lime green
			new Color(0x92, 0x95, 0x91), // grey
			new Color(0x75, 0xbb, 0xfd), // sky blue
			new Color(0xff, 0xff, 0x14), // yellow
			new Color(0xc2, 0x00, 0x78), // magenta
			new Color(0x96, 0xf9, 0x7b), // light green
			new Color(0xf9, 0x73, 0x06), // orange
			new Color(0x02, 0x93, 0x86), // teal
			new Color(0x95, 0xd0, 0xfc), // light blue
			new Color(0xe5, 0x00, 0x00), // red
			new Color(0x65, 0x37, 0x00), // brown
			new Color(0xff, 0x81, 0xc0), // pink
			new Color(0x03, 0x43, 0xdf), // blue
			new Color(0x15, 0xb0, 0x1a), // green
			new Color(0x7e, 0x1e, 0x9c), // purple
	};

	public String getMappingDescReportsForTee() {
		String filePath = tempDir + File.separator + "MappingTee"
				+ Instant.now().toEpochMilli() + ".xlsx";
		List<Map> result = markSheetDao.getQueryResultsTotalTee();
		List<Map<String, Object>> resultMap = new ArrayList();
		List<String> headers = new ArrayList();

		Map<String, Map<String, Object>> studentRecords = new HashMap();

		int count = 0;
		for (Map<String, Object> m : result) {

			String sapId = m.get("sapId").toString();
			String icId = m.get("id").toString();
			Map<String, Object> rsMap = new LinkedHashMap<>();
			Map<String, Object> sr = null;
			sr = studentRecords.get(sapId);
			if (sr == null) {
				sr = new HashMap();
				sr.put("SAPId", sapId);
				sr.put("First Name", m.get("fname"));
				sr.put("Last Name", m.get("lname"));

			}
			String mapping = Utils.getBlankIfNull(m.get("mapping"));
			String cweightage = Utils.getBlankIfNull(m
					.get("criteria_weightage"));

			if (!mapping.isEmpty()) {
				String mpName = "Mapper" + mapping;
				if (!sr.containsKey(mpName)) {
					sr.put(mpName, mpName);
					/*
					 * if (!headers.contains(mpName)) headers.add(mpName);
					 */
				}

				String scored = mapping + "Scored";
				String outOff = mapping + "Out Of";

				/*
				 * if (!headers.contains(scored)) { headers.add(scored); }
				 * 
				 * if (!headers.contains(outOff)) { headers.add(outOff); }
				 */

				if (!sr.containsKey(outOff)) {
					sr.put(outOff, cweightage);
				} else {
					double total = Double.valueOf(cweightage)
							+ Double.valueOf(sr.get(outOff).toString());
					sr.put(outOff, total + "");
				}

				if (count == 0) {

					String obtained = Utils.getBlankIfNull(m.get("w1"));
					if (!sr.containsKey(scored)) {
						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 1) {

					String obtained = Utils.getBlankIfNull(m.get("w2"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 2) {

					String obtained = Utils.getBlankIfNull(m.get("w3"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

				} else if (count == 3) {

					String obtained = Utils.getBlankIfNull(m.get("w4"));
					if (!sr.containsKey(scored)) {

						sr.put(scored, obtained);

					} else {
						double scoredTotal = Double.valueOf(obtained)
								+ Double.valueOf(sr.get(scored).toString());
						sr.put(scored, scoredTotal + "");
					}

					count = -1;
				}
				count++;
				studentRecords.put(sapId, sr);

			}
		}
		headers.add("SAPId");
		headers.add("First Name");
		headers.add("Last Name");
		headers.add("Mapping Description");
		headers.add("Student Score");
		headers.add("Total  Score(OutOff)");
		headers.add("Percentage");
		for (Map.Entry<String, Map<String, Object>> m : studentRecords
				.entrySet()) {

			Map<String, Object> res = m.getValue();

			Set<String> keys = res.keySet();
			List<String> mappers = new ArrayList();
			for (String k : keys) {
				if (k.indexOf("Mapper") != -1) {
					mappers.add(k);
				}

			}
			List<String> sapIds = new ArrayList<String>();

			for (String k : mappers) {
				Map<String, Object> rs = new HashMap();
				String id = res.get("SAPId").toString();
				if (!sapIds.contains(id)) {
					rs.put("SAPId", id);
					rs.put("First Name", res.get("First Name"));
					rs.put("Last Name", res.get("Last Name"));
					sapIds.add(id);
				} else {
					rs.put("SAPId", "");
					rs.put("First Name", "");
					rs.put("Last Name", "");
				}
				String mapping = StringUtils.replace(k, "Mapper", "");
				String scored = mapping + "Scored";
				String outOff = mapping + "Out Of";
				String score = Utils.getBlankIfNull(res.get(scored));
				String out = Utils.getBlankIfNull(res.get(outOff));
				rs.put("Mapping Description", mapping);
				rs.put("Student Score", score);
				rs.put("Total  Score(OutOff)", out);
				if (!score.isEmpty() && !out.isEmpty())
					rs.put("Percentage",
							(Math.round((Double.valueOf(score) / Double
									.valueOf(out)) * 100)) + "");
				resultMap.add(rs);

			}

		}
		ExcelCreater.CreateExcelFile(resultMap, headers, filePath);

		return filePath;

	}

}
