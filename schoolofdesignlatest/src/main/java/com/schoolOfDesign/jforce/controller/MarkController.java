package com.schoolOfDesign.jforce.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.daos.icdao.IceTotalDao;
import com.schoolOfDesign.jforce.daos.icdao.MarksDao;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.IceService;
import com.schoolOfDesign.jforce.service.MarksService;
import com.schoolOfDesign.jforce.service.StudentCourseFacultyService;
import com.schoolOfDesign.jforce.service.StudentService;
import com.schoolOfDesign.jforce.service.TEEService;

import model.ConsoleIce;
import model.ICETEEConsolidated;
import model.Marks;

@Controller
public class MarkController {

	@Autowired
	MarksDao markDAo;

	@Autowired
	private IceService iceService;

	@Autowired
	private TEEService teeService;

	@Autowired
	private StudentService studentService;

	@Autowired
	private StudentCourseFacultyService studentCourseFacultyService;

	@Autowired
	private CourseService courseService;

	@Autowired
	MarksService marksService;

	@Autowired
	IceTotalDao iceTotalDao;

	@Autowired
	HttpSession session;

	Logger log = LoggerFactory.getLogger(MarkController.class);

	@RequestMapping(value = "/saveasdraft", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody String saveasdraft(
			@RequestParam Map<String, String> allRequestParams) {
		log.info("allRequestParams----------------"+allRequestParams);
		marksService.fillMarksAndIceTotal(allRequestParams, "DRAFT");
		return "saved";
	}

	@RequestMapping(value = "/saveasdraftTEE", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody String saveasdraftTEE(
			@RequestParam Map<String, String> allRequestParams) {
		teeService.fillMarksAndTEETotal(allRequestParams, "DRAFT");
		return "saved";
	}

	@RequestMapping(value = "/gradeSubmitTEE", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView gradeSubmitTEE(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("gradeReportTEE");
		String teeId = allRequestParams.get("teeId");

		teeService.fillMarksAndTEETotal(allRequestParams, "SUBMIT");
		TEEBean teeBean = teeService.loadTEE(teeId);
		CourseBean courseBean = courseService.getCourse(teeBean.getCourse_id());
		List<TEECriteriaBean> criteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();

		for (TEECriteriaBean teeCriteria : criteriaList) {
			headers.add(teeCriteria.getCriteria_desc());
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

		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("teeBean", teeBean);
		mav.addObject("subDate", new Date());
		return mav;
	}

	@RequestMapping(value = "/lookGradedTee", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView lookGradedTee(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("gradeReportTEE");
		String teeId = allRequestParams.get("teeId");

		TEEBean teeBean = teeService.loadTEE(teeId);
		CourseBean courseBean = courseService.getCourse(teeBean.getCourse_id());
		List<TEECriteriaBean> criteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();

		for (TEECriteriaBean teeCriteria : criteriaList) {
			headers.add(teeCriteria.getCriteria_desc());
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

		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("teeBean", teeBean);
		mav.addObject("subDate", new Date());
		return mav;
	}

	@RequestMapping(value = "/gradeSubmit", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView gradeSubmit(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("gradereport");
		String icId = allRequestParams.get("icId");

		marksService.fillMarksAndIceTotal(allRequestParams, "SUBMIT");
		IceBean bean = iceService.loadIc(icId);
		CourseBean courseBean = courseService.getCourse(bean.getCourse_id());
		List<IceCriteriaBean> criteriaList = iceService.loadIcÇriteria(icId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();

		for (IceCriteriaBean icb : criteriaList) {
			headers.add(icb.getCriteria_desc());
			weightage.add(icb.getWeightage());
		}
		// headers.add("Total");
		List<Marks> studentList = null;
		UserBean userBean = (UserBean) session.getAttribute("user");
		switch (userBean.getRoleName()) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(bean.getCourse_id(),
					bean.getOwner_faculty());
			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(bean
					.getCourse_id());

		}
		List<Marks> mrkList = marksService.populateStudentMarksIfAny(
				studentList, icId);

		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("icBean", bean);
		mav.addObject("subDate", new Date());
		return mav;
	}

	@RequestMapping(value = "/lookGradedIc", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView lookGradedIc(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("icreport");
		String icId = allRequestParams.get("icId");

		IceBean bean = iceService.loadIc(icId);
		CourseBean courseBean = courseService.getCourse(bean.getCourse_id());
		List<IceCriteriaBean> criteriaList = iceService.loadIcÇriteria(icId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();

		for (IceCriteriaBean icb : criteriaList) {
			headers.add(icb.getCriteria_desc());
			weightage.add(icb.getWeightage());
		}
		// headers.add("Total");
		List<Marks> studentList = null;
		UserBean userBean = (UserBean) session.getAttribute("user");
		switch (userBean.getRoleName()) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(bean.getCourse_id(),
					bean.getOwner_faculty());
			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(bean
					.getCourse_id());

		}
		List<Marks> mrkList = marksService.populateStudentMarksIfAny(
				studentList, icId);

		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("icBean", bean);
		mav.addObject("subDate", new Date());
		return mav;
	}

	@RequestMapping(value = "/grade", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView grade(@RequestParam Map<String, String> allRequestParams) {

		ModelAndView mav = new ModelAndView("grade");
		String icId = allRequestParams.get("icId");

		IceBean bean = iceService.loadIc(icId);
		CourseBean courseBean = courseService.getCourse(bean.getCourse_id());
		List<IceCriteriaBean> criteriaList = iceService.loadIcÇriteria(icId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();
		for (IceCriteriaBean icb : criteriaList) {
			headers.add(icb.getCriteria_desc());
			weightage.add(icb.getWeightage());
		}
		// headers.add("Total");

		List<Marks> studentList = null;
		UserBean userBean = (UserBean) session.getAttribute("user");
		switch (userBean.getRoleName()) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(bean.getCourse_id(),
					bean.getOwner_faculty());
			log.info("bean.getCourse_id()" + bean.getCourse_id());
			log.info("bean.getOwner_faculty()" + bean.getOwner_faculty());
			log.info("studentList" + studentList);

			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(bean
					.getCourse_id());

		}

		List<Marks> mrkList = studentList == null || studentList.isEmpty() ? new ArrayList()
				: marksService.populateStudentMarksIfAny(studentList, icId);

		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("icId", icId);
		mav.addObject("icName", bean.getIceName());
		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		return mav;
	}

	@RequestMapping(value = "/gradeTEE", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView gradeTEE(
			@RequestParam Map<String, String> allRequestParams) {

		ModelAndView mav = new ModelAndView("gradeTEE");
		String teeId = allRequestParams.get("teeId");

		TEEBean teeBean = teeService.loadTEE(teeId);
		CourseBean courseBean = courseService.getCourse(teeBean.getCourse_id());
		List<TEECriteriaBean> criteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		List<String> headers = getHeaders();
		List<String> weightage = new ArrayList<String>();
		for (TEECriteriaBean teCb : criteriaList) {
			headers.add(teCb.getCriteria_desc());
			weightage.add(teCb.getWeightage());
		}
		// headers.add("Total");
		List<Marks> studentList = null;

		UserBean bean = (UserBean) session.getAttribute("user");
		switch (bean.getRoleName()) {

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
		for (Marks m : mrkList) {
			log.info("Roll no - " + m.getRollNo());
		}
		log.info("mrkList ---- " + mrkList);

		mav.addObject("tableheader", headers);
		mav.addObject("weightage", weightage);
		mav.addObject("mrkList", mrkList);
		mav.addObject("icId", teeBean.getCourse_id());
		mav.addObject("tee_percent", teeBean.getTee_percent());
		mav.addObject("teeId", teeId);
		mav.addObject("teeType", teeBean.getTeeType());
		mav.addObject("courseName", courseBean.getCourse_name());
		mav.addObject("year", courseBean.getYear());
		mav.addObject("sem", courseBean.getSession());
		return mav;
	}

	private List<String> getHeaders() {
		List<String> headers = new ArrayList<String>();
		/*
		 * headers.add("Sr.No"); headers.add("Sap Id"); headers.add("Name");
		 * headers.add("Criteria");
		 */
		return headers;

	}

	@RequestMapping(value = "/preConsole", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView preConsole(
			@RequestParam Map<String, String> allRequestParams) {
		List<CourseBean> courseList = null;
		ModelAndView mav = new ModelAndView("preConsole");
		Map<String, String> courseData = new HashMap<String, String>();
		courseList = courseService.getAllCourses();

		for (CourseBean courseBean : courseList) {
			courseData.put(courseBean.getSap_course_id().toString(),
					courseBean.getCourse_name());
		}
		mav.addObject("courseData", courseData);

		return mav;
	}

	@RequestMapping(value = "/iceTeeDashBoard", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView iceTeeDashBoard() {
		ModelAndView mav = new ModelAndView();
		UserBean bean = (UserBean) session.getAttribute("user");
		Map<String, String> courseData = new HashMap<String, String>();
		List<CourseBean> courseList = null;
		switch (bean.getRoleName()) {
		case "faculty":
			String facultyId = bean.getUsername();

			courseList = courseService.getCoursesAssignedToFaculty(facultyId);

			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}

			mav.addObject("courseData", courseData);
			break;

		case "authority":
		case "cordinator":
		case "exam":
			courseList = courseService.getAllCourses();
			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}
			mav.addObject("courseData", courseData);

			break;

		}

		mav.setViewName("iceTEEDashBoard");
		return mav;
	}

	@RequestMapping(value = "/getConsolidatedDetailsBasedOnCourse", method = {
			RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody String getConsolidatedDetailsBasedOnCourse(
			@RequestParam("courseId") String courseId) {

		Gson gson = new Gson();
		String jsonInString = "";
		Map<String, String> mapOfSapIdAndCourseId = studentCourseFacultyService
				.mapOfSapIdAndCourseId(courseId);
		List<ICETEEConsolidated> getCourseWiseTEEICERecords = marksService
				.getAllRecordsForDashboardBasedOnCourseId(
						mapOfSapIdAndCourseId, courseId);
		for (ICETEEConsolidated c : getCourseWiseTEEICERecords) {
			String sapId = c.getSapId();
			log.info("sapId ---- " + sapId);
			String rollNo = studentService.findRollNo(sapId);
			c.setRollNo(rollNo);
			getCourseWiseTEEICERecords.set(
					getCourseWiseTEEICERecords.indexOf(c), c);
		}
		jsonInString = gson.toJson(getCourseWiseTEEICERecords);
		log.info("jsonInString" + jsonInString);
		return jsonInString;
	}

	@RequestMapping(value = "/getConsilatedIce", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView getConsilatedIce(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("consoleIce");
		String courseId = allRequestParams.get("courseId");
		UserBean bean = (UserBean) session.getAttribute("user");
		CourseBean courseBean = courseService.getCourse(courseId);
		double ratio = Double.valueOf(courseBean.getIca_tee_split());
		List<ConsoleIce> preList = marksService.getConsoliatedIceForStudents(
				courseId, ratio);
		mav.addObject("ratio", ratio);
		String total = "";
		if (!preList.isEmpty()) {
			for (ConsoleIce c : preList) {
				String sap_id = c.getSapId();
				String rollNo = studentService.findRollNo(sap_id);
				c.setRollNo(rollNo);
				preList.set(preList.indexOf(c), c);

			}
			total = preList.get(0).getTotal();
			mav.addObject("total", total);
			mav.addObject("iceCount", "" + preList.get(0).getIceMarks().size());
		}
		mav.addObject("reportUrl", "getIceXlsx?courseId=" + courseId
				+ "&format=xlsx");
		mav.addObject("preList", preList);

		return mav;
	}

	private List<Marks> getStudentList(String courseId, String faculty,
			String roleName) {
		List<Marks> studentList = null;
		switch (roleName) {

		case "faculty":
			studentList = marksService.getStudentsToGrade(courseId, faculty);
			break;
		case "authority":
		case "cordinator":
		case "exam":
			studentList = marksService.getAllStudentsToCourse(courseId);

		}
		return studentList;

	}

}