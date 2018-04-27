package com.schoolOfDesign.jforce.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.daos.icdao.StudentDao;
import com.schoolOfDesign.jforce.service.ExcelReader;
import com.schoolOfDesign.jforce.service.StudentService;

@Controller
public class StudentMasterController {

	@Value("#{'${tempPath:G:}'}")
	String op;

	@Autowired
	ExcelReader reader;

	Logger log = LoggerFactory.getLogger(IceController.class);

	@Autowired
	ExcelReader excelReader;

	@Autowired
	StudentDao dao;

	@Autowired
	StudentService studentService;

	static List<String> headerList = new ArrayList<String>();

	static {
		String arr[] = { "Student Number", "Last Name", "First Name",
				"Middle Name", "Father Name", "Mother Name", "Gender",
				"Regis. Program abb.", "Regis. Program Description",
				"Enrollment Year", "Session", "EMAILID", "Mobile No",
				"Phone No", "Date Of Birth", "Registration Date", "Address",
				"City", "State", "Country", "PIN", "Current Program Abb",
				"Current Program Description", "Current Academic Year",
				"Current Academic Session" };
		headerList = Arrays.asList(arr);
	}

	@RequestMapping(value = "/uploadStudent", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView submit(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mv = new ModelAndView("uploadStudent");
		List<String> operations = new ArrayList();
		operations.add("CREATE");
		operations.add("UPDATE");
		operations.add("ADDNEW");

		// operations.add("DELETE");
		mv.addObject("operations", operations);
		return mv;

	}

	@RequestMapping(value = "/uploadStudentMaster", method = RequestMethod.POST)
	public @ResponseBody String uploadMasterFile(
			@RequestParam(name = "file", required = true) MultipartFile input,
			@RequestParam("operation") String operation) {
		String statusOfUpload = uploadFileAndReturnStatus(input, operation);
		return statusOfUpload;

	}

	private String uploadFileAndReturnStatus(MultipartFile file,
			String operation) {
		InputStream inputStream = null;
		ArrayList<StudentBean> studentList = new ArrayList<StudentBean>();
		log.info("Operation" + operation);

		String fileName = file.getOriginalFilename();
		log.info("fileName Size = " + file.getSize());

		log.info("fileName = " + fileName);

		fileName = RandomStringUtils.randomAlphanumeric(10)
				+ fileName.substring(fileName.lastIndexOf("."),
						fileName.length());
		try {

			inputStream = file.getInputStream();
			String filePath = op + File.separator + fileName;

			File folderPath = new File(op);
			if (!folderPath.exists()) {
				log.info("Making Folder");
				boolean created = folderPath.mkdirs();
				log.info("created = " + created);
			}

			File dest = new File(filePath);

			FileUtils.copyInputStreamToFile(inputStream, dest);
			List<Map<String, String>> listCells = excelReader.readXLSXFile(dest
					.getAbsolutePath());
			List<String> ids = new ArrayList<String>();
			for (Map<String, String> m : listCells) {
				StudentBean bean = new StudentBean();
				bean.setSap_id(m.get("Student Number"));
				bean.setFirst_name(m.get("First Name"));
				bean.setLast_name(m.get("Last Name"));
				bean.setStudent_name(bean.getFirst_name() + ","
						+ bean.getLast_name());
				bean.setMother_name(m.get("Mother Name"));
				bean.setFather_name(m.get("Father Name"));
				bean.setGender(m.get("Gender"));
				bean.setReg_prog_abbr(m.get("Regis. Program abb."));
				String enroll = m.get("Enrollment Year");
				bean.setRoll_no(m.get("Student Roll No."));

				if (enroll != null) {
					try {
						bean.setEnroll_year(Integer.parseInt(enroll.trim()));
					} catch (Exception e) {
						log.error("Exception ", e);
					}
				}
				/*
				 * bean.setEnroll_year(enroll != null ? Integer.parseInt(enroll)
				 * : null);
				 */
				bean.setCity(m.get("City"));

				bean.setSession(m.get("Session"));
				bean.setEmail_id(m.get("EMAILID"));
				bean.setPhone_no(m.get("Mobile No.") + "," + m.get("Phone No."));
				bean.setDateOfBirth(m.get("Date Of Birth"));
				bean.setAddress(m.get("Address"));
				bean.setCity(m.get("City"));
				bean.setState(m.get("State"));
				bean.setZipcode(m.get("PIN"));
				bean.setCurrent_prog_abbr(m.get("Current Program Abb."));
				bean.setCurrent_prog_desc(m.get("Current Program Description"));
				bean.setCurrent_acad_year(m.get("Current Academic Year"));
				bean.setCurrent_acad_session(m.get("Current Academic Session"));
				bean.setActive("Y");
				studentList.add(bean);
				ids.add(bean.getSap_id());
			}
			if ("CREATE".equals(operation)) {
				studentService.updateStudentList(ids);
				studentService.insertStudentList(studentList);
			}

			if ("ADDNEW".equals(operation)) {
				studentService.insertStudentList(studentList);
			}
			if ("DELETE".equals(operation)) {
				studentService.deleteStudentList(ids);
			}

			if ("UPDATE".equals(operation)) {
				dao.updateBatch(studentList);
			}
			return "SUCCESS";

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR");
			return "ERROR";

		}

	}
}
