package com.schoolOfDesign.jforce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.Year;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import model.ConsoleIce;
import model.Marks;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.data.category.DefaultCategoryDataset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.daos.icdao.TermEndMarksDao;
import com.schoolOfDesign.jforce.helpers.JasperReport;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.IceService;
import com.schoolOfDesign.jforce.service.MarksService;
import com.schoolOfDesign.jforce.service.StudentService;
import com.schoolOfDesign.jforce.service.TEEService;

@Controller
public class ReportController {

	@Autowired
	private IceService IceService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	MarksService marksService;

	@Autowired
	TermEndMarksDao temDao;

	@Autowired
	private CourseService courseService;

	@Autowired
	private TEEService teeService;

	@Autowired
	HttpSession session;

	@Value("#{'${outputPath:E:}'}")
	String op;

	@Value("#{'${templatePath:E:}'}")
	String templatePath;

	@Value("${icaCourseTemplate}")
	private String icaCourseTemplate;

	Logger log = LoggerFactory.getLogger(ReportController.class);

	@RequestMapping(value = "/report", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView report(
			@RequestParam Map<String, String> allRequestParams) {
		List<CourseBean> courseList = null;
		ModelAndView mav = new ModelAndView("report");
		Map<String, String> courseData = new HashMap<String, String>();
		Map<String, String> yearData = new HashMap<String, String>();
		courseList = courseService.getAllCourses();

		for (CourseBean courseBean : courseList) {
			courseData.put(courseBean.getSap_course_id().toString(),
					courseBean.getCourse_name());
		}

		int year = 2017;
		// yearData.put(year+"", year+"");

		while (year <= Year.now().getValue()) {

			yearData.put(year + "", year + "");
			year++;
		}

		mav.addObject("courseData", courseData);
		mav.addObject("yearData", yearData);
		mav.addObject("preAssigment", new ArrayList());

		return mav;
	}

	@RequestMapping(value = "/pendingReport", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getPDF(@RequestParam String format) {
		String file = JasperReport.generatePendingReport(
				IceService.getPendingIces(), format, op);
		byte[] contents = null;
		HttpHeaders headers = new HttpHeaders();
		InputStream inputStream;

		try {
			File newFile = new File(file);
			contents = FileUtils.readFileToByteArray(newFile);

			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "output.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		} catch (Exception e) {
			log.error("exception", e);
		}

		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(contents,
				headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping(value = "/getIcePdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getIcePdf(@RequestParam String format,
			@RequestParam String courseId) {

		ResponseEntity<byte[]> response = null;
		CourseBean courseBean = courseService.getCourse(courseId);
		double ratio = Double.valueOf(courseBean.getIca_tee_split());
		List<ConsoleIce> preList = marksService.getConsoliatedIceForStudents(
				courseId, ratio);
		String totalStr = preList.get(0).getTotal();

		int no = preList.get(0).getIceMarks().size();
		Document document = new Document();
		String fileName = op + "/" + RandomStringUtils.randomAlphabetic(4)
				+ "." + format;
		try {
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));

			document.open();

			Font font3 = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD);
			Chunk collegNameChunk = new Chunk("Consolidated ICA Report", font3);
			collegNameChunk.setUnderline(0.1f, -2f);
			Paragraph collegeName = new Paragraph();
			collegeName.add(collegNameChunk);
			collegeName.setAlignment(Element.ALIGN_CENTER);
			// collegeName.setExtraParagraphSpace(10);
			collegeName.setSpacingAfter(15);
			document.add(collegeName);

			PdfPTable table = new PdfPTable(5 + no);
			table.setHeaderRows(1);
			table.setWidthPercentage(100);
			table.getDefaultCell().setUseAscender(true);
			table.getDefaultCell().setUseDescender(true);
			PdfPCell cell1 = new PdfPCell(new Paragraph("Sr.no"));
			PdfPCell cell2 = new PdfPCell(new Paragraph("SAP Id"));
			PdfPCell cell3 = new PdfPCell(new Paragraph("Name"));
			DefaultCategoryDataset result = new DefaultCategoryDataset();
			table.setHorizontalAlignment(Element.ALIGN_LEFT);
			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			for (int i = 1; i <= no; i++) {
				PdfPCell cell = new PdfPCell(new Paragraph("Exercise" + i));
				table.addCell(cell);
			}
			PdfPCell cell5 = new PdfPCell(new Paragraph("out/" + totalStr));
			PdfPCell cell6 = new PdfPCell(new Paragraph("ICA/" + ratio));
			table.addCell(cell5);
			table.addCell(cell6);
			int i = 1;
			for (ConsoleIce ic : preList) {
				PdfPCell cell10 = new PdfPCell(new Paragraph(""
						+ Integer.valueOf(i)));
				i++;
				table.addCell(cell10);
				cell10 = new PdfPCell(new Paragraph(ic.getSapId()));
				table.addCell(cell10);

				cell10 = new PdfPCell(new Paragraph(ic.getName()));
				table.addCell(cell10);

				Map<String, String> iceMarks = ic.getIceMarks();
				Collection<String> values = iceMarks.values();
				int j = 1;
				for (String v : values) {
					cell10 = new PdfPCell(new Paragraph(v));
					table.addCell(cell10);
					result.addValue(Double.valueOf(v), "Exercise" + j,
							ic.getName());
					j++;
				}
				cell10 = new PdfPCell(new Paragraph(ic.getScored()));
				table.addCell(cell10);
				cell10 = new PdfPCell(new Paragraph(ic.getCalc()));
				table.addCell(cell10);

			}

			document.add(table);

			/*
			 * final JFreeChart chart = ChartFactory.createStackedBarChart(
			 * "ICE Consolidated", // chart title "Category", // domain axis
			 * label "Score", // range axis label result, // data
			 * PlotOrientation.VERTICAL, // the plot orientation true, // legend
			 * true, // tooltips false // urls );
			 * 
			 * ; int width = 570; int height = 400;
			 * 
			 * PdfTemplate template = contentByte.createTemplate(width, height);
			 * Graphics2D graphics2d = template.createGraphics(width, height,
			 * new DefaultFontMapper()); Rectangle2D rectangle2d = new
			 * Rectangle2D.Double(0, 0, width, height);
			 * 
			 * chart.draw(graphics2d, rectangle2d);
			 * 
			 * graphics2d.dispose(); Image img = Image.getInstance(template);
			 * img.setAbsolutePosition(0, 0);
			 * 
			 * img.setWidthPercentage(100); document.newPage();
			 * document.add(img);
			 */

			document.close();

			byte[] contents = null;
			HttpHeaders headers = new HttpHeaders();

			contents = FileUtils.readFileToByteArray(new File(fileName));

			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "output.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			response = new ResponseEntity<byte[]>(contents, headers,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception", e);
		}

		return response;
	}

	@RequestMapping(value = "/downloadGradedTee", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadGradedTee(@RequestParam String teeId) {

		ResponseEntity<byte[]> response = null;
		TEEBean teeBean = teeService.loadTEE(teeId);
		CourseBean courseBean = courseService.getCourse(teeBean.getCourse_id());
		List<TEECriteriaBean> criteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		List<String> header = new ArrayList<String>();
		List<String> weightage = new ArrayList<String>();

		for (TEECriteriaBean teeCriteria : criteriaList) {
			header.add(teeCriteria.getCriteria_desc());
			weightage.add(teeCriteria.getWeightage());
		}
		// headers.add("Total");
		List<Marks> studentList = null;
		UserBean userBean = (UserBean) session.getAttribute("user");
		switch (userBean.getRoleName()) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(
					teeBean.getCourse_id(), teeBean.getOwner_faculty());
			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(teeBean
					.getCourse_id());

		}
		List<Marks> mrkList = marksService.populateStudentMarksForTeeIfAny(
				studentList, teeId);

		// int no = preList.get(0).getIceMarks().size();
		// Document document = new Document(PageSize.A4, 36, 36, 20 +
		// event.getTableHeight(), 36);
		String fileName = op + "/" + RandomStringUtils.randomAlphabetic(4)
				+ ".pdf";
		Font font = new Font(FontFamily.TIMES_ROMAN, 11);
		try {
			/*
			 * PdfWriter writer = PdfWriter.getInstance(document, new
			 * FileOutputStream(fileName)); // step 2 document.open();
			 */
			class HeaderTable extends PdfPageEventHelper {
				protected PdfPTable table1;
				protected float tableHeight;

				public HeaderTable() {
					table1 = new PdfPTable(9);
					table1.setTotalWidth(523);
					table1.setLockedWidth(true);
					PdfPCell cell01 = new PdfPCell(new Phrase("TEE ID", font));
					cell01.setColspan(4);
					table1.addCell(cell01);
					PdfPCell cell02 = new PdfPCell(new Phrase(
							String.valueOf(teeBean.getId()), font));
					cell02.setColspan(5);
					table1.addCell(cell02);

					PdfPCell cell03 = new PdfPCell(new Phrase("Owner Faculty",
							font));
					cell03.setColspan(4);
					table1.addCell(cell03);
					PdfPCell cell04 = new PdfPCell(new Phrase(
							teeBean.getOwner_faculty(), font));
					cell04.setColspan(5);
					table1.addCell(cell04);

					PdfPCell cell05 = new PdfPCell(new Phrase(
							"Assigned Faculty", font));
					cell05.setColspan(4);
					table1.addCell(cell05);
					PdfPCell cell06 = new PdfPCell(new Phrase(
							teeBean.getAssigned_faculty(), font));
					cell06.setColspan(5);
					table1.addCell(cell06);

					PdfPCell cell07 = new PdfPCell(new Phrase("Course", font));
					cell07.setColspan(4);
					table1.addCell(cell07);
					PdfPCell cell08 = new PdfPCell(new Phrase(
							courseBean.getCourse_name(), font));
					cell08.setColspan(5);
					table1.addCell(cell08);

					PdfPCell cell09 = new PdfPCell(new Phrase("Tee Type", font));
					cell09.setColspan(4);
					table1.addCell(cell09);
					PdfPCell cell10 = new PdfPCell(new Phrase(
							teeBean.getTeeType(), font));
					cell10.setColspan(5);
					table1.addCell(cell10);

					PdfPCell cell11 = new PdfPCell(new Phrase(
							"Tee weight Percentage", font));
					cell11.setColspan(4);
					table1.addCell(cell11);
					PdfPCell cell12 = new PdfPCell(new Phrase(
							teeBean.getTee_percent(), font));
					cell12.setColspan(5);
					table1.addCell(cell12);

					tableHeight = table1.getTotalHeight();

					// PdfPTable table2 = new PdfPTable(9);
					// table1.setTotalWidth(100);

					PdfPCell cell1 = new PdfPCell(new Phrase("S/N", font));
					cell1.setRowspan(2);
					table1.addCell(cell1);
					PdfPCell cell2 = new PdfPCell(new Phrase("Roll No", font));
					cell2.setRowspan(2);
					table1.addCell(cell2);
					PdfPCell cell3 = new PdfPCell(new Phrase("Name", font));
					cell3.setRowspan(2);
					table1.addCell(cell3);
					table1.addCell(new PdfPCell(new Phrase("Criteria", font)));
					PdfPCell cell = new PdfPCell(new Phrase("Weighted Value",
							font));

					for (TEECriteriaBean teeCriteria : criteriaList) {
						table1.addCell(teeCriteria.getCriteria_desc());

						// weightage.add(teeCriteria.getWeightage());
					}

					table1.addCell(new PdfPCell(new Phrase("Total", font)));
					table1.addCell(cell);
					for (TEECriteriaBean teeCriteria : criteriaList) {
						table1.addCell(teeCriteria.getWeightage());

						// weightage.add(teeCriteria.getWeightage());
					}
					table1.addCell(new PdfPCell(new Phrase("100", font)));
					// table1.setSpacingAfter(500);
					/*
					 * PdfPCell emptyCell = new PdfPCell();
					 * emptyCell.setColspan(9); table1.addCell(emptyCell);
					 */

				}

				public float getTableHeight() {
					System.out.println("tableHeight ----- " + tableHeight);
					return tableHeight;
				}

				public void onEndPage(PdfWriter writer, Document document) {
					table1.writeSelectedRows(
							0,
							-1,
							document.left(),
							document.top()
									+ ((document.topMargin() + tableHeight) / 2),
							writer.getDirectContent());

					/*
					 * try { document.open(); document.add(new Paragraph());
					 * document.add( Chunk.NEWLINE ); document.add(
					 * Chunk.NEWLINE ); document.add( Chunk.NEWLINE ); } catch
					 * (DocumentException e) { // TODO Auto-generated catch
					 * block e.printStackTrace(); }
					 */

				}
			}
			HeaderTable event = new HeaderTable();
			// step 1
			Document document = new Document(PageSize.A4, 36, 36,
					20 + event.getTableHeight(), 36);
			// step 2
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			writer.setPageEvent(event);
			// step 3
			document.open();

			/*
			 * document.add(new Paragraph());
			 * 
			 * document.add(Chunk.NEWLINE); document.add(Chunk.NEWLINE);
			 * document.add(Chunk.NEWLINE);
			 */
			PdfPTable table2 = new PdfPTable(9);
			table2.setTotalWidth(523);
			table2.setLockedWidth(true);
			// table2.setSpacingBefore(5000);
			PdfPCell emptyCell = new PdfPCell();
			emptyCell.setColspan(9);
			table2.addCell(emptyCell);

			int count = 1;
			int oldPageNo = document.getPageNumber();
			log.info("oldPageNo - " + oldPageNo);

			for (Marks m : mrkList) {

				log.info("Current page no - " + document.getPageNumber());

				log.info("Count - " + count);

				PdfPCell cell11 = new PdfPCell(new Phrase(
						String.valueOf(count), font));
				cell11.setRowspan(2);
				table2.addCell(cell11);
				PdfPCell cell12 = new PdfPCell(new Phrase(m.getSapId(), font));
				cell12.setRowspan(2);
				table2.addCell(cell12);
				PdfPCell cell13 = new PdfPCell(new Phrase(m.getRollNo(), font));
				cell13.setRowspan(2);
				table2.addCell(cell13);
				table2.addCell(new PdfPCell(new Phrase("Out of 100", font)));
				table2.addCell(new Phrase(m.getCriteria_1_marks(), font));
				table2.addCell(new Phrase(m.getCriteria_2_marks(), font));
				table2.addCell(new Phrase(m.getCriteria_3_marks(), font));
				table2.addCell(new Phrase(m.getCriteria_3_marks(), font));
				table2.addCell(new Phrase(m.getCriteriaTotal(), font));

				table2.addCell(new PdfPCell(new Phrase("Weightage", font)));
				table2.addCell(new Phrase(m.getWeightage_1(), font));
				table2.addCell(new Phrase(m.getWeightage_2(), font));
				table2.addCell(new Phrase(m.getWeightage_3(), font));
				table2.addCell(new Phrase(m.getWeightage_4(), font));
				if (m.getWeightedTotal() == null) {
					table2.addCell(new Phrase(0));
				} else {
					table2.addCell(new Phrase(String.valueOf(Math.round(Double
							.parseDouble(m.getWeightedTotal()))), font));
				}
				count++;

			}

			// document.add(table1);
			log.info("Top margin ---- " + document.topMargin());
			/*
			 * table2.writeSelectedRows(12, 11, document.left(), document.top()
			 * + ((document.topMargin() + 12) / 2), writer.getDirectContent());
			 */
			/*
			 * document.add(new Paragraph());
			 * 
			 * document.add( Chunk.NEWLINE ); document.add( Chunk.NEWLINE );
			 * document.add( Chunk.NEWLINE ); document.add(new Paragraph());
			 */
			document.add(table2);

			document.close();

			byte[] contents = null;
			HttpHeaders headers = new HttpHeaders();

			contents = FileUtils.readFileToByteArray(new File(fileName));

			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "output.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			response = new ResponseEntity<byte[]>(contents, headers,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception", e);
		}

		return response;
	}

	@RequestMapping(value = "/downloadGradedTeePdf", method = RequestMethod.GET)
	public ResponseEntity<byte[]> downloadGradedTeePdf(
			@RequestParam String teeId) {

		ResponseEntity<byte[]> response = null;
		TEEBean teeBean = teeService.loadTEE(teeId);
		CourseBean courseBean = courseService.getCourse(teeBean.getCourse_id());
		List<TEECriteriaBean> criteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		List<String> header = new ArrayList<String>();
		List<String> weightage = new ArrayList<String>();

		for (TEECriteriaBean teeCriteria : criteriaList) {
			header.add(teeCriteria.getCriteria_desc());
			weightage.add(teeCriteria.getWeightage());
		}
		// headers.add("Total");
		List<Marks> studentList = null;
		UserBean userBean = (UserBean) session.getAttribute("user");
		switch (userBean.getRoleName()) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(
					teeBean.getCourse_id(), teeBean.getOwner_faculty());
			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(teeBean
					.getCourse_id());

		}
		List<Marks> mrkList = marksService.populateStudentMarksForTeeIfAny(
				studentList, teeId);

		// int no = preList.get(0).getIceMarks().size();
		// Document document = new Document(PageSize.A4, 36, 36, 20 +
		// event.getTableHeight(), 36);
		String fileName = op + "/" + RandomStringUtils.randomAlphabetic(4)
				+ ".pdf";
		Font font = new Font(FontFamily.TIMES_ROMAN, 11);
		try {

			Document document = new Document();
			document.setPageSize(new Rectangle(700,850));
			document.setMargins(36, 36, 20, 36);
			PdfWriter writer = PdfWriter.getInstance(document,
					new FileOutputStream(fileName));
			document.open();

			PdfPTable table1 = new PdfPTable(10);
			table1.setTotalWidth(650);
			table1.setLockedWidth(true);
			table1.setLockedWidth(true);
			PdfPCell cell01 = new PdfPCell(new Phrase("TEE ID", font));
			cell01.setColspan(5);
			table1.addCell(cell01);
			PdfPCell cell02 = new PdfPCell(new Phrase(String.valueOf(teeBean
					.getId()), font));
			cell02.setColspan(5);
			table1.addCell(cell02);

			PdfPCell cell03 = new PdfPCell(new Phrase("Owner Faculty", font));
			cell03.setColspan(5);
			table1.addCell(cell03);
			PdfPCell cell04 = new PdfPCell(new Phrase(
					teeBean.getOwner_faculty(), font));
			cell04.setColspan(5);
			table1.addCell(cell04);

			PdfPCell cell05 = new PdfPCell(new Phrase("Assigned Faculty", font));
			cell05.setColspan(5);
			table1.addCell(cell05);
			PdfPCell cell06 = new PdfPCell(new Phrase(
					teeBean.getAssigned_faculty(), font));
			cell06.setColspan(5);
			table1.addCell(cell06);

			PdfPCell cell07 = new PdfPCell(new Phrase("Course", font));
			cell07.setColspan(5);
			table1.addCell(cell07);
			PdfPCell cell08 = new PdfPCell(new Phrase(
					courseBean.getCourse_name(), font));
			cell08.setColspan(5);
			table1.addCell(cell08);

			PdfPCell cell09 = new PdfPCell(new Phrase("Tee Type", font));
			cell09.setColspan(5);
			table1.addCell(cell09);
			PdfPCell cell10 = new PdfPCell(new Phrase(teeBean.getTeeType(),
					font));
			cell10.setColspan(5);
			table1.addCell(cell10);

			PdfPCell cell11 = new PdfPCell(new Phrase("Tee weight Percentage",
					font));
			cell11.setColspan(5);
			table1.addCell(cell11);
			PdfPCell cell12 = new PdfPCell(new Phrase(teeBean.getTee_percent(),
					font));
			cell12.setColspan(5);
			table1.addCell(cell12);

			float tableHeight = table1.getTotalHeight();

			// PdfPTable table2 = new PdfPTable(9);
			// table1.setTotalWidth(100);

			PdfPCell cell1 = new PdfPCell(new Phrase("S/N", font));
			cell1.setRowspan(2);
			table1.addCell(cell1);
			PdfPCell cell2 = new PdfPCell(new Phrase("Roll No", font));
			cell2.setRowspan(2);
			table1.addCell(cell2);
			PdfPCell cell3 = new PdfPCell(new Phrase("SAP ID", font));
			cell3.setRowspan(2);
			table1.addCell(cell3);
			PdfPCell cell4 = new PdfPCell(new Phrase("Name", font));
			cell4.setRowspan(2);
			table1.addCell(cell4);
			table1.addCell(new PdfPCell(new Phrase("Criteria", font)));

			int n = 6;
			for (TEECriteriaBean teeCriteria : criteriaList) {
				log.info("Cell no - " + n);
				table1.addCell(new Phrase(teeCriteria.getCriteria_desc(), font));
				n++;
				// weightage.add(teeCriteria.getWeightage());
			}

			table1.addCell(new PdfPCell(new Phrase("Total", font)));
			log.info("total cell no --- " + table1.getNumberOfColumns());
			table1.addCell(new PdfPCell(new Phrase("Weighted Value", font)));
			for (TEECriteriaBean teeCriteria : criteriaList) {
				table1.addCell(new Phrase(teeCriteria.getWeightage(), font));

				// weightage.add(teeCriteria.getWeightage());
			}
			table1.addCell(new PdfPCell(new Phrase("100", font)));

			table1.setHeaderRows(8);

			int count = 1;

			for (Marks m : mrkList) {

				log.info("Count - " + count);

				PdfPCell celld1 = new PdfPCell(new Phrase(
						String.valueOf(count), font));
				celld1.setRowspan(2);
				table1.addCell(celld1);
				PdfPCell celld2 = new PdfPCell(new Phrase(m.getRollNo(), font));
				celld2.setRowspan(2);
				table1.addCell(celld2);
				PdfPCell cell13 = new PdfPCell(new Phrase(m.getSapId(), font));
				cell13.setRowspan(2);
				table1.addCell(cell13);

				PdfPCell cell14 = new PdfPCell(new Phrase(m.getName(), font));
				cell14.setRowspan(2);
				table1.addCell(cell14);

				table1.addCell(new PdfPCell(new Phrase("Out of 100", font)));
				table1.addCell(new Phrase(m.getCriteria_1_marks(), font));
				table1.addCell(new Phrase(m.getCriteria_2_marks(), font));
				table1.addCell(new Phrase(m.getCriteria_3_marks(), font));
				table1.addCell(new Phrase(m.getCriteria_3_marks(), font));
				table1.addCell(new Phrase(m.getCriteriaTotal(), font));

				table1.addCell(new PdfPCell(new Phrase("Weightage", font)));
				table1.addCell(new Phrase(m.getWeightage_1(), font));
				table1.addCell(new Phrase(m.getWeightage_2(), font));
				table1.addCell(new Phrase(m.getWeightage_3(), font));
				table1.addCell(new Phrase(m.getWeightage_4(), font));
				if (m.getWeightedTotal() == null) {
					table1.addCell(new Phrase(0));
				} else {
					table1.addCell(new Phrase(String.valueOf(Math.round(Double
							.parseDouble(m.getWeightedTotal()))), font));
				}
				count++;

			}

			log.info("Top margin ---- " + document.topMargin());

			document.add(table1);

			document.close();

			byte[] contents = null;
			HttpHeaders headers = new HttpHeaders();

			contents = FileUtils.readFileToByteArray(new File(fileName));

			headers.setContentType(MediaType.parseMediaType("application/pdf"));
			String filename = "output.pdf";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			response = new ResponseEntity<byte[]>(contents, headers,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception", e);
		}

		return response;
	}

	@RequestMapping(value = "/getIceXlsx", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getIceXls(@RequestParam String format,
			@RequestParam String courseId) {
		log.info("courseId - " + courseId);

		CourseBean courseBean = courseService.getCourse(courseId);
		log.info("courseBean - " + courseBean);
		double ratio = Double.valueOf(courseBean.getIca_tee_split());
		List<ConsoleIce> preList = marksService.getConsoliatedIceForStudents(
				courseId, ratio);
		String totalStr = preList.get(0).getTotal();
		String fileName = op + "/" + RandomStringUtils.randomAlphabetic(4)
				+ "." + format;

		int no = preList.get(0).getIceMarks().size();
		String templateFile = templatePath + File.separator + "ICETotal" + no
				+ ".xlsx";
		Workbook workbook;
		try {
			/*
			 * workbook = new XSSFWorkbook(OPCPackage.open(new
			 * FileInputStream(op + File.separator + "StackedChart.xlsx")));
			 */
			workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(
					templateFile)));

			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sh = workbook.getSheetAt(0);
			String sheetName = sh.getSheetName();

			sh.getRow(0).createCell(0).setCellValue("SAPID");
			sh.getRow(0).createCell(1).setCellValue("NAME");

			int i = 3;
			int j;

			for (i = 2, j = 1; i < no + 2; i++, j++) {
				sh.getRow(0).createCell(i).setCellValue("ICE" + j);
			}

			sh.getRow(0).createCell(i).setCellValue("out/" + totalStr);
			sh.getRow(0).createCell(no + 3).setCellValue("out/" + ratio);

			i = 1;
			for (ConsoleIce ic : preList) {

				Row r = sh.getRow(i);
				if (r == null) {
					r = sh.createRow(i);
				}

				r.createCell(0).setCellValue(ic.getSapId());
				r.createCell(1).setCellValue(ic.getName());

				Map<String, String> iceMarks = ic.getIceMarks();
				Collection<String> values = iceMarks.values();
				j = 2;

				for (String v : values) {
					Cell cell = r.createCell(j);
					cell.setCellValue(new Double(v));
					cell.setCellType(XSSFCell.CELL_TYPE_NUMERIC);
					CellStyle style = workbook.createCellStyle();
					style.setDataFormat(workbook.createDataFormat().getFormat(
							"0.00"));
					cell.setCellStyle(style);
					j++;
				}

				r.createCell(j++).setCellValue(new Double(ic.getScored()));
				r.createCell(j++).setCellValue(ic.getCalc());

				i++;
			}

			// System.out.println("Final Row " + i);
			int k = 1;

			Name student_name = workbook.getName("student_name");
			String referenceStudentName = sheetName + "!$B$2:$B$"
					+ String.valueOf(i);
			student_name.setRefersToFormula(referenceStudentName);
			char ref = 'C';
			for (k = 1; k <= no; k++) {

				Name ice_total1 = workbook.getName("ice_total" + k);
				String referenceICTotal = sheetName + "!$" + ref + "$2:$" + ref
						+ "$" + String.valueOf(i);
				log.info("referenceICTotal" + referenceICTotal);
				ice_total1.setRefersToFormula(referenceICTotal);
				ref++;
			}

			FileOutputStream f = new FileOutputStream(fileName);
			workbook.write(f);
			f.close();

		} catch (InvalidFormatException | IOException e1) {
			log.error("exception", e1);
		}

		ResponseEntity<byte[]> response = null;

		try {

			byte[] contents = null;
			HttpHeaders headers = new HttpHeaders();

			contents = FileUtils.readFileToByteArray(new File(fileName));

			headers.setContentType(MediaType
					.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			String filename = "ConsolidatedReportFor"
					+ courseBean.getCourse_name()
					+ Instant.now().toEpochMilli() + ".xlsx";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			response = new ResponseEntity<byte[]>(contents, headers,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception", e);
		}

		return response;
	}

	@RequestMapping(value = "/getIceCourseXlsx", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getIceCourseXlsx(@RequestParam String format,
			@RequestParam String courseId) {
		log.info("courseId - " + courseId);

		CourseBean courseBean = courseService.getCourse(courseId);
		log.info("courseBean - " + courseBean);
		double ratio = Double.valueOf(courseBean.getIca_tee_split());
		List<ConsoleIce> preList = marksService.getConsoliatedIceForStudents(
				courseId, ratio);
		String totalStr = preList.get(0).getTotal();
		String fileName = op + "/" + RandomStringUtils.randomAlphabetic(4)
				+ "." + format;

		int no = preList.get(0).getIceMarks().size();
		String templateFile = icaCourseTemplate;
		Workbook workbook;
		try {
			/*
			 * workbook = new XSSFWorkbook(OPCPackage.open(new
			 * FileInputStream(op + File.separator + "StackedChart.xlsx")));
			 */
			workbook = new XSSFWorkbook(OPCPackage.open(new FileInputStream(
					templateFile)));

			CreationHelper createHelper = workbook.getCreationHelper();
			Sheet sh = workbook.getSheetAt(0);
			String sheetName = sh.getSheetName();

			Cell cell1 = sh.getRow(7).getCell(0);
			cell1.setCellValue("Module Name: " + courseBean.getModule_name());

			Cell cell2 = sh.getRow(7).getCell(3);
			cell2.setCellValue("Module Number: "
					+ courseBean.getModule_abbr());
			SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");  
			
			formatter = new SimpleDateFormat("dd MMMM yyyy");  
			String strDate = formatter.format(new Date());  
			sh.getRow(0).createCell(4).setCellValue("Date : " + strDate);
			Cell cell3 = sh.getRow(8).getCell(0);
			cell3.setCellValue("Assesment Ratio :  ICA - "
					+ courseBean.getIca_tee_split() + " ,  TEE - "
					+ (100 - Integer.parseInt(courseBean.getIca_tee_split())));

			Cell cell4 = sh.getRow(10).getCell(4);
			cell4.setCellValue("ICA Out of / " + courseBean.getIca_tee_split());

			//List<MarksBean> icaTotalList = marksService.getTotalICA(courseId);
			//log.info("icaTotalList ----------------- " + icaTotalList);
			int j = 11;
			for (ConsoleIce m : preList) {
				log.info("M is ------- " + m);
				log.info("J is ------- " + j);
				String sapId = m.getSapId();
				sh.getRow(j).getCell(1).setCellValue(studentService.findRollNo(sapId));
				sh.getRow(j).getCell(2).setCellValue(m.getSapId());
				sh.getRow(j).getCell(3).setCellValue(m.getName());
				double total = Math.round(Double.parseDouble(m.getCalc()));
				sh.getRow(j).getCell(4).setCellValue(total);
				j++;

			}

			FileOutputStream f = new FileOutputStream(fileName);
			workbook.write(f);
			f.close();

		} catch (InvalidFormatException | IOException e1) {
			log.error("exception", e1);
		}

		ResponseEntity<byte[]> response = null;

		try {

			byte[] contents = null;
			HttpHeaders headers = new HttpHeaders();

			contents = FileUtils.readFileToByteArray(new File(fileName));

			headers.setContentType(MediaType
					.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"));
			String filename = "CourseICAReportFor"
					+ courseBean.getCourse_name()
					+ Instant.now().toEpochMilli() + ".xlsx";
			headers.setContentDispositionFormData(filename, filename);
			headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
			response = new ResponseEntity<byte[]>(contents, headers,
					HttpStatus.OK);
		} catch (Exception e) {
			log.error("exception", e);
		}

		return response;
	}
}
