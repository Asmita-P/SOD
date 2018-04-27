package com.schoolOfDesign.jforce.controller;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpSession;

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
import com.schoolOfDesign.jforce.beans.icbeans.FacultyBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.FacultyService;
import com.schoolOfDesign.jforce.service.IceService;

@Controller
public class IceController {

	@Autowired
	private IceService iceService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private FacultyService facultyService;

	@Value("#{'${skillProperty:G:}'}")
	String skillProperty;

	Logger log = LoggerFactory.getLogger(IceController.class);
	int criteriaCount = 4;

	@Autowired
	HttpSession session;

	@RequestMapping(value = "/preSelect", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView prSelectIc() {
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
			courseList = courseService.getAllCourses();

			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}
			mav.addObject("courseData", courseData);
			break;

		case "cordinator":
			courseList = courseService.getAllCourses();

			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}
			mav.addObject("courseData", courseData);
			break;

		}
		Map<String, String> criteria = new LinkedHashMap<String, String>();
		for (int i = 1; i <= criteriaCount; i++) {
			criteria.put("criteriaDesc" + i, "weightage" + i);
		}

		Map<String, String> facultyData = new HashMap<String, String>();
		List<FacultyBean> facultyList = facultyService.getFacultyList();

		for (FacultyBean fBean : facultyList) {
			facultyData.put(fBean.getSap_id().toString(),
					fBean.getFaculty_name());
		}

		mav.addObject("criteria", criteria);
		mav.addObject("skillData", getSkill());
		mav.addObject("facultyData", facultyData);
		mav.setViewName("preIc");
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

	@RequestMapping(value = "/beforeUpdateIc", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView updateIc(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("updateIc");
		String icId = allRequestParams.get("icId");

		log.info("allRequestParams" + allRequestParams);
		IceBean bean = iceService.loadIc(icId);
		List<IceCriteriaBean> iceCriteriaList = iceService.loadIcÇriteria(icId);
		mav.addObject("iceCriteriaList", iceCriteriaList);
		mav.addObject("icid", icId);
		mav.addObject("skillData", getSkill());
		mav.addObject("iceName", bean.getIceName());

		return mav;
	}

	@RequestMapping(value = "/preDeleteIca", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView preDeleteIca() {
		ModelAndView mav = new ModelAndView("preDelIc");
		List<Ic> iceList = iceService.getAllActiveIces();
		Map<String, String> iceData = new HashMap<String, String>();
		if (iceList != null)
			for (Ic ic : iceList) {
				iceData.put(ic.getIcId(), ic.getIceName());
			}

		mav.addObject("icaData", iceData);
		return mav;

	}

	@RequestMapping(value = "/deleteIca", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView deleteIca(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("preDelIc");
		String iceId = allRequestParams.get("iceId");
		IceBean bean = iceService.loadIc(iceId);
		if (iceId != null) {
			iceService.permDeleteIc(iceId.trim());
			mav.addObject("message", " ICA with id <i>" + bean.getId()
					+ "</i> and name <i>" + bean.getIceName() + " </i> deleted ");
		} else {
			mav.addObject("message", " No Ica " + "   found ");
		}
		
		List<Ic> iceList = iceService.getAllActiveIces();
		Map<String, String> iceData = new HashMap<String, String>();
		if (iceList != null)
			for (Ic ic : iceList) {
				iceData.put(ic.getIcId(), ic.getIceName());
			}

		mav.addObject("icaData", iceData);
		return mav;

	}

	@RequestMapping(value = "/createIcJson", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView createIcJson(
			@RequestParam Map<String, String> allRequestParams) {

		String courseId = allRequestParams.get("courseId");
		UserBean bean = (UserBean) session.getAttribute("user");
		String facultyName = allRequestParams.get("facultyName");

		IceBean ic = iceService.createIc(bean.getUsername(), courseId, "false",
				null, facultyName);

		String icId = ic.getId().toString();
		allRequestParams.put("icId", icId);
		iceService.updateIcWithCriteria(allRequestParams, criteriaCount);
		return iceList();

	}

	@RequestMapping(value = "/updateIcJson", method = { RequestMethod.POST,
			RequestMethod.GET })
	public @ResponseBody String updateIcJson(
			@RequestParam Map<String, String> allRequestParams) {

		String icId = allRequestParams.get("icId");
		UserBean bean = (UserBean) session.getAttribute("user");
		iceService.updateIcWithCriteria(allRequestParams, criteriaCount);
		String id = "ICE Id  " + icId + " updated";
		return id;

	}

	@RequestMapping(value = "/loadIc", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView loadIc(@RequestParam("icId") String icId) {
		IceBean bean = iceService.loadIc(icId);

		Long id = bean.getId();
		ModelAndView mav = new ModelAndView();
		if ("GRADING".equals(bean.getStatus()))
			mav.setViewName("showIcAndCriteriaDisabled");
		else
			mav.setViewName("showIc");

		List<IceCriteriaBean> iceCriteriaList = iceService.loadIcÇriteria(id
				.toString());
		mav.addObject("icid", id);
		mav.addObject("iceCriteriaList", iceCriteriaList);
		return mav;

	}

	@RequestMapping(value = "/showIcToFaculty", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView showIcToFaculty() {
		UserBean bean = (UserBean) session.getAttribute("user");
		ModelAndView mav = new ModelAndView();
		List<Ic> icList = iceService.getAllIcesForFaculty(bean.getUsername());

		mav.addObject("iceList", icList);
		mav.setViewName("listIc");
		return mav;

	}

	@RequestMapping(value = "/iceList", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView iceList() {
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
			mav.setViewName("iceList");

			break;

		case "authority":
		case "cordinator":
			courseList = courseService.getAllCourses();

			for (CourseBean courseBean : courseList) {
				courseData.put(courseBean.getSap_course_id().toString(),
						courseBean.getCourse_name());
			}
			mav.addObject("courseData", courseData);
			mav.setViewName("iceList");
			break;

		}
		return mav;

	}

	@RequestMapping(value = "/showIcToByCourse", method = { RequestMethod.GET,
			RequestMethod.POST })
	public @ResponseBody String getCourseFacultyJson(
			@RequestParam("courseId") String courseId) {
		Gson gson = new Gson();
		UserBean bean = (UserBean) session.getAttribute("user");
		String roleName = bean.getRoleName();
		String jsonInString = "";
		List<Ic> icList = new ArrayList<>();
		switch (roleName) {
		case "faculty":
			if (courseId.equalsIgnoreCase("showAssigned")) {
				icList = iceService.getAssignedFaculties(bean.getUsername());
			} else
				icList = iceService.getAllIcesForFacultyAndCourse(
						bean.getUsername(), courseId);
			break;

		case "authority":
		case "cordinator":
			icList = iceService.getAllIces(courseId);
			break;
		}

		jsonInString = gson.toJson(icList);
		log.info("jsonInString **" + jsonInString);
		return jsonInString;
	}

	@RequestMapping(value = "/saveiccriteria", method = RequestMethod.POST)
	public ModelAndView saveiccriteria(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView();
		String icId = allRequestParams.get("icid");
		log.info("allRequestParams" + allRequestParams);

		List<IceCriteriaBean> beanList = new ArrayList<IceCriteriaBean>();

		for (int i = 1; i <= criteriaCount; i++) {

			IceCriteriaBean bean = new IceCriteriaBean();
			bean.setCriteria_desc(allRequestParams.get("criteriaDesc" + i));
			bean.setIce_id(icId);
			bean.setWeightage(allRequestParams.get("weightage" + i));
			beanList.add(bean);
		}
		String iceName = allRequestParams.get("iceName");
		iceService.updateIcWithCriteria(icId, beanList, iceName);
		mav.addObject("iceCriteriaList", beanList);
		mav.addObject("icid", icId);
		mav.setViewName("showIc");

		return mav;
	}
}
