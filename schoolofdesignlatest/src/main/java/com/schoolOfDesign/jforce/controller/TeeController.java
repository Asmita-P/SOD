package com.schoolOfDesign.jforce.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpSession;

import model.ConsoleIce;
import model.Ic;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.TermEndMarksBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.daos.icdao.TermEndMarksDao;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.MarksService;
import com.schoolOfDesign.jforce.service.TEEService;

@Controller
public class TeeController {

	@Autowired
	private TEEService teeService;

	@Autowired
	private CourseService courseService;

	@Autowired
	MarksService marksService;

	@Autowired
	TermEndMarksDao temDao;

	@Autowired
	HttpSession session;

	int criteriaCount = 4;

	@Value("#{'${skillProperty:G:}'}")
	String skillProperty;

	Logger log = LoggerFactory.getLogger(TeeController.class);

	@RequestMapping(value = "/preTee", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView preConsole(
			@RequestParam Map<String, String> allRequestParams) {
		List<CourseBean> courseList = null;
		List<TEEBean> teeListAssignedToCourse = null;
		List<String> listOfCourseId = new ArrayList<String>();
		ModelAndView mav = new ModelAndView("preTee");
		Map<String, String> courseData = new HashMap<String, String>();
		UserBean bean = (UserBean) session.getAttribute("user");
		String roleName = bean.getRoleName();

		switch (roleName) {
		case "faculty":
			courseList = courseService.getCoursesAssignedToFaculty(bean
					.getUsername());
			break;
		default:
			courseList = courseService.getAllCourses();
		}

		teeListAssignedToCourse = teeService.getAllTEERecordsAssignedToICE();
		for (TEEBean tee : teeListAssignedToCourse) {
			listOfCourseId.add(tee.getCourse_id());
		}
		for (CourseBean courseBean : courseList) {
			if (!listOfCourseId.contains(courseBean.getSap_course_id()
					.toString())) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			} else {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}

		}
		mav.addObject("courseData", courseData);
		mav.addObject("skillData", getSkill());

		return mav;
	}

	@RequestMapping(value = "/showTEEToByCourse", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String showTEEToByCourse(
			@RequestParam("courseId") String courseId) {
		Gson gson = new Gson();
		UserBean bean = (UserBean) session.getAttribute("user");
		String roleName = bean.getRoleName();
		String jsonInString = "";
		List<TEEBean> teeList = new ArrayList<>();
		switch (roleName) {
		case "faculty":
			if (courseId.equalsIgnoreCase("showAssigned")) {
				teeList = teeService.getAssignedFacultiesForTEE(bean
						.getUsername());
			} else {
				teeList = teeService.getAllIcesForFacultyAndCourse(
						bean.getUsername(), courseId);
			}

			break;

		case "authority":
		case "cordinator":
			teeList = teeService.getAllTEERecordsByCourse(courseId, false);
			break;
		case "exam":
			teeList = teeService.getAllTEERecordsByCourse(courseId, true);
			break;

		}

		jsonInString = gson.toJson(teeList);
		log.info("jsonInString **" + jsonInString);
		return jsonInString;
	}

	@RequestMapping(value = "/createTEEJson", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView createIcJson(
			@RequestParam Map<String, String> allRequestParams) {

		String courseId = allRequestParams.get("courseName");
		String teeType = allRequestParams.get("teeType");
		String tee_percent = allRequestParams.get("tee_percent");
		UserBean bean = (UserBean) session.getAttribute("user");
		String facultyName = allRequestParams.get("facultyName");
		TEEBean teeRecord = teeService.createTEERecord(bean.getUsername(),
				courseId, "false", null, facultyName, teeType, tee_percent);
		String teeId = teeRecord.getId().toString();
		allRequestParams.put("teeId", teeId);
		teeService.updateTEEWithCriteria(allRequestParams, criteriaCount);
		return teeList();

	}

	@RequestMapping(value = "/getConsilatedTotal", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getConsilatedIce(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("consoleTee");
		String courseId = allRequestParams.get("courseId");
		UserBean bean = (UserBean) session.getAttribute("user");
		CourseBean courseBean = courseService.getCourse(courseId);
		double ratio = Double.valueOf(courseBean.getIca_tee_split());
		List<ConsoleIce> preList = marksService.getConsoliatedIceForStudents(
				courseId, ratio);
		mav.addObject("ratio", ratio);
		mav.addObject("courseId", courseId);
		mav.addObject("preList", preList);

		return mav;
	}

	@RequestMapping(value = "/updateTeeJson", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody String updateTeeJson(
			@RequestParam Map<String, String> allRequestParams) {

		String teeId = allRequestParams.get("teeId");
		UserBean bean = (UserBean) session.getAttribute("user");
		teeService.updateTEEWithCriteria(allRequestParams, criteriaCount);
		String id = "Tee Id  " + teeId + " updated";
		return id;

	}

	@RequestMapping(value = "/saveConsilatedTotal", method = {
			RequestMethod.POST, RequestMethod.GET })
	public @ResponseBody String saveConsilatedTotal(
			@RequestParam Map<String, String> allRequestParams) {

		String courseId = allRequestParams.get("courseId");

		UserBean bean = (UserBean) session.getAttribute("user");
		Set<String> keys = allRequestParams.keySet();
		List<TermEndMarksBean> termEndbean = new ArrayList<TermEndMarksBean>();
		for (String key : keys) {
			if (key.startsWith("sapId")) {
				TermEndMarksBean tmb = new TermEndMarksBean();
				String sapId = key.substring(5);
				tmb.setCourse_id(courseId);
				tmb.setSap_id(sapId);
				tmb.setIce_weighted(allRequestParams.get("calc" + sapId));
				tmb.setScore_earned(allRequestParams.get("teeWeighted" + sapId));
				tmb.setTotal(allRequestParams.get("total" + sapId));
				tmb.setActive("Y");
				termEndbean.add(tmb);
			}
		}

		temDao.insertBatch(termEndbean);

		return "TEE Saved";
	}

	@RequestMapping(value = "/teeList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView teeList() {
		ModelAndView mav = new ModelAndView();
		Map<String, String> courseData = new HashMap<String, String>();
		UserBean bean = (UserBean) session.getAttribute("user");
		String roleName = bean.getRoleName();
		List<CourseBean> courseList = null;
		switch (roleName) {
		case "faculty":
			String facultyId = bean.getUsername();

			courseList = courseService.getCoursesAssignedToFaculty(facultyId);

			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}
			courseData.put("showAssigned", "showAssigned");
			mav.addObject("courseData", courseData);
			mav.setViewName("teeList");

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
			mav.setViewName("teeList");
			break;

		}
		return mav;

	}

	@RequestMapping(value = "/beforeUpdateTEE", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView updateIc(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("updateTee");
		String teeId = allRequestParams.get("teeId");

		log.info("allRequestParams" + allRequestParams);

		List<TEECriteriaBean> teeCriteriaList = teeService
				.getCriteriaListBasedOnId(teeId);
		mav.addObject("teeCriteriaList", teeCriteriaList);
		mav.addObject("teeId", teeId);
		mav.addObject("skillData", getSkill());

		return mav;
	}

	private List<String> getSkill() {
		Properties prop = new Properties();
		List<String> skill = new ArrayList<String>();
		try {
			prop.load(new FileInputStream(skillProperty));
			String property = (String) prop.get("skill");
			String args[] = StringUtils.split(property, ",");
			skill = Arrays.asList(args);
		} catch (Exception e) {
			log.error("Exception", e);
		}

		return skill;
	}

}
