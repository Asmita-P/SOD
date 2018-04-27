package com.schoolOfDesign.jforce.controller;

import java.io.File;
import java.io.InputStream;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.daos.icdao.CourseDao;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.ExcelReader;
import com.schoolOfDesign.jforce.service.StudentService;

@Controller
public class CourseMasterControlller {

	@Value("#{'${tempPath:G:}'}")
	String op;

	@Autowired
	ExcelReader reader;

	Logger log = LoggerFactory.getLogger(IceController.class);

	@Autowired
	ExcelReader excelReader;

	@Autowired
	CourseService courseService;

	@Autowired
	CourseDao dao;

	@Autowired
	HttpSession session;
	static List<String> headerList = new ArrayList<String>();

	static {
		String arr[] = { "Faculty id", "Faculty type", "Student id",
				"Student no", "Module abbreviation", "Module Name",
				"Course id", "Event Name", "Acad.Year", "Acad.Session",
				"Acad.Session Text", "Acad.Date", "ICA weightage",
				"Term end weightage" };
		headerList = Arrays.asList(arr);
	}

	@RequestMapping(value = "/uploadCourse", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView submit(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mv = new ModelAndView("uploadCourse");
		List<String> operations = new ArrayList();
		operations.add("CREATE");
		operations.add("UPDATE");
		operations.add("DELETE");
		operations.add("ADDNEW");
		mv.addObject("operations", operations);
		return mv;

	}

	@RequestMapping(value = "/uploadCourseMaster", method = RequestMethod.POST)
	public @ResponseBody String uploadMasterFile(
			@RequestParam(name = "file", required = true) MultipartFile input,
			@RequestParam("operation") String operation) {
		String statusOfUpload = uploadFileAndReturnStatus(input, operation);

		return statusOfUpload;

	}

	private String uploadFileAndReturnStatus(MultipartFile file,
			String operation) {

		InputStream inputStream = null;
		ArrayList<CourseBean> courseList = new ArrayList<CourseBean>();

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
				CourseBean bean = new CourseBean();

				bean.setCourse_name(m.get("Module Name"));
				bean.setModule_name(m.get("Module Name"));
				bean.setSap_course_id(m.get("Module ID"));
				bean.setProg_name(m.get("Module Name"));
				bean.setModule_abbr(m.get("Module abbreviation"));
				bean.setSession(m.get("Acad.Session"));
				bean.setYear(m.get("Acad.Year"));
				bean.setIca_tee_split(m.get("ICA weightage"));
				bean.setActive("Y");
				ids.add(bean.getSap_course_id());

				courseList.add(bean);
			}
			if ("CREATE".equals(operation)) {
				courseService.updateCourse(ids);
				courseService.insertCourseList(courseList);
			}
			if ("ADDNEW".equals(operation)) {
				courseService.insertCourseList(courseList);
			}
			if ("DELETE".equals(operation)) {
				courseService.deleteCourseList(ids);
			}

			if ("UPDATE".equals(operation)) {
				dao.updateBatch(courseList);
			}

			return "SUCCESS";

		} catch (Exception e) {
			e.printStackTrace();
			log.error("ERROR");
			return "ERROR";

		}

	}

	@RequestMapping(value = "/getCourseBySemester", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getCourseBySemester(
			@RequestParam(name = "semesterid") String semesterid,
			Principal principal) {
		String json = "";
		UserBean bean = (UserBean) session.getAttribute("user");
		String roleName = bean.getRoleName();
		List<CourseBean> courseList = null;
		log.info("semesterid " + semesterid);
		log.info("roleName ----- "+roleName);
		switch (roleName) {
		case "faculty":
			String facultyId = bean.getUsername();

			courseList = courseService.getCoursesAssignedToFacultyAndSemester(
					facultyId, semesterid);

			break;

		case "authority":
			courseList = courseService.getAllCoursesBySemester(semesterid);
			log.info("courseList ------- " + courseList);

			break;
		case "cordinator":
			courseList = courseService.getAllCoursesBySemester(semesterid);
			log.info("courseList ------- " + courseList);

			break;
		case "exam":
			courseList = courseService.getAllCoursesBySemester(semesterid);
			log.info("courseList ------- " + courseList);

			break;

		}

		List<Map<String, String>> res = new ArrayList<Map<String, String>>();

		for (CourseBean ass : courseList) {
			Map<String, String> returnMap = new HashMap();
			returnMap.put(String.valueOf(ass.getSap_course_id()),
					ass.getCourse_name());
			res.add(returnMap);
		}
		ObjectMapper mapper = new ObjectMapper();
		try {
			json = mapper.writeValueAsString(res);

		} catch (JsonProcessingException e) {
			log.error("Exception", e);
		}

		log.info("json" + json);
		return json;

	}

}
