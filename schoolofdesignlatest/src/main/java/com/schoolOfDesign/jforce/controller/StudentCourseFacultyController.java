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

import com.schoolOfDesign.jforce.beans.icbeans.StudentCourseFacultyBean;
import com.schoolOfDesign.jforce.daos.icdao.StudentCourseFacultyDao;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.ExcelReader;
import com.schoolOfDesign.jforce.service.FacultyService;
import com.schoolOfDesign.jforce.service.StudentCourseFacultyService;

@Controller
public class StudentCourseFacultyController {

	@Value("#{'${tempPath:G:}'}")
	String op;

	@Autowired
	ExcelReader reader;

	Logger log = LoggerFactory.getLogger(IceController.class);

	@Autowired
	ExcelReader excelReader;

	@Autowired
	StudentCourseFacultyDao dao;

	@Autowired
	StudentCourseFacultyService studentCourseFacultyService;
	
	@Autowired
	StudentCourseFacultyDao studentCourseFacultydao;
	
	@Autowired
	CourseService courseService;
	
	@Autowired
	FacultyService facultyService;

	static List<String> headerList = new ArrayList<String>();

	static {
		String arr[] = { "Faculty id", "Faculty type", "Student id",
				"Student no", "Module abbreviation", "Module Name",
				"Course id", "Event Name", "Acad.Year", "Acad.Session",
				"Acad.Session Text", "Acad.Date", "ICA weightage",
				"Term end weightage" };
		headerList = Arrays.asList(arr);
	}

	@RequestMapping(value = "/addFacultyCourseForm", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addFacultyCourseForm(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("addFacultyCourse");
		mav.addObject("allCourses", courseService.getAllCourses());
		mav.addObject("facultyList", facultyService.getFacultyList());

		return mav;
	}

	@RequestMapping(value = "/addFacultyCourse", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView addFacultyCourse(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("facultyCourse");
		String courseId = allRequestParams.get("sap_course_id");
		String facultyId = allRequestParams.get("sap_id");
		log.info("courseId " + courseId);
		log.info("facultyId " + facultyId);
		ArrayList<StudentCourseFacultyBean> studFacCourList = new ArrayList<StudentCourseFacultyBean>();
		List<StudentCourseFacultyBean> studentCourseList = studentCourseFacultyService
				.findByCourseActive(courseId);
		log.info("studentCourseList " + studentCourseList);
		try {

			for (StudentCourseFacultyBean sfc : studentCourseList) {
				StudentCourseFacultyBean bean = new StudentCourseFacultyBean();
				bean.setStudent_sap_id(sfc.getStudent_sap_id());
				bean.setCourse_id(courseId);
				bean.setFaculty_sap_id(facultyId);
				bean.setAcad_month(sfc.getAcad_month());
				bean.setAcad_year(sfc.getAcad_year());
				bean.setActive("Y");

				studFacCourList.add(bean);

			}
			log.info("studFacCourList - " + studFacCourList);
			/*
			 * studentCourseFacultyService
			 * .insertStudentCourseFacultyList(studFacCourList);
			 */

			studentCourseFacultydao.insertBatch(studFacCourList);
			mav.addObject("status", "Successfully Added!");
		} catch (Exception e) {
			mav.addObject("status", "Error!");
		}
		mav.addObject("allCourses", courseService.getAllCourses());
		mav.addObject("facultyList", facultyService.getFacultyList());

		return mav;
	}

	@RequestMapping(value = "/uploadStudentCourseFaculty", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView submit(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mv = new ModelAndView("uploadStudentCourse");
		List<String> operations = new ArrayList();
		operations.add("CREATE");
		operations.add("UPDATE");
		operations.add("DELETE");
		operations.add("ADDNEW");
		mv.addObject("operations", operations);
		return mv;

	}

	@RequestMapping(value = "/uploadStudentCourseFacultyMaster", method = RequestMethod.POST)
	public @ResponseBody String uploadMasterFile(
			@RequestParam(name = "file", required = true) MultipartFile input,
			@RequestParam("operation") String operation) {
		String statusOfUpload = uploadFileAndReturnStatus(input, operation);

		return statusOfUpload;

	}

	private String uploadFileAndReturnStatus(MultipartFile file,
			String operation) {
		InputStream inputStream = null;
		ArrayList<StudentCourseFacultyBean> studentCourseFacultyList = new ArrayList<StudentCourseFacultyBean>();
		log.info("Operation" + operation);

		String fileName = file.getOriginalFilename();
		log.info("fileName Size = " + file.getSize());

		log.info("fileName = " + fileName);

		fileName =

		RandomStringUtils.randomAlphanumeric(10)
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
				StudentCourseFacultyBean bean = new StudentCourseFacultyBean();

				bean.setStudent_sap_id(m.get("Student no"));
				bean.setCourse_id(m.get("Module ID"));
				bean.setFaculty_sap_id(m.get("Faculty id"));
				bean.setAcad_year(m.get("Acad.Year"));
				bean.setActive("Y");

				studentCourseFacultyList.add(bean);
				ids.add(bean.getStudent_sap_id());
				ids.add(bean.getFaculty_sap_id());
				ids.add(bean.getCourse_id());
			}

			if ("CREATE".equals(operation)) {
				studentCourseFacultyService.updateStudentList(ids);
				studentCourseFacultyService
						.insertStudentCourseFacultyList(studentCourseFacultyList);
			}

			if ("ADDNEW".equals(operation)) {
				studentCourseFacultyService
						.insertStudentCourseFacultyList(studentCourseFacultyList);
			}
			if ("DELETE".equals(operation)) {
				studentCourseFacultyService.deleteStudentList(ids);
			}

			if ("UPDATE".equals(operation)) {
				dao.updateBatch(studentCourseFacultyList);
			}

			return "SUCCESS";

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR");
			return "ERROR";

		}

	}
}
