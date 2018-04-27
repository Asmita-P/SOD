package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.beans.icbeans.StudentCourseFacultyBean;
import com.schoolOfDesign.jforce.daos.icdao.StudentCourseFacultyDao;

@Service
public class StudentCourseFacultyService {
	@Autowired
	StudentCourseFacultyDao studentCourseFacultydao;

	@Autowired
	StudentService studentService;

	public void insertStudentCourseFacultyList(
			ArrayList<StudentCourseFacultyBean> studentCourseFacultyList) {
		studentCourseFacultydao.insertBatch(studentCourseFacultyList);
	}

	public void updateStudentList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		studentCourseFacultydao
				.getNamedParameterJdbcTemplate()
				.update("update student_course_faculty set active='N'   WHERE student_sap_id IN (:ids)",
						parameters);

	}

	public Map<String, String> mapOfSapIdAndCourseId() {
		List<StudentCourseFacultyBean> getAllStudentCourseRecordsList = studentCourseFacultydao
				.getAllStudentCourseRecords();
		Map<String, String> mapper = new HashMap<String, String>();
		for (StudentCourseFacultyBean sfb : getAllStudentCourseRecordsList) {
			mapper.put(sfb.getStudent_sap_id(), sfb.getCourse_id());
		}
		return mapper;
	}

	public Map<String, String> mapOfSapIdAndCourseId(String courseId) {
		List<StudentCourseFacultyBean> getAllStudentCourseRecordsList = studentCourseFacultydao
				.getAllStudentCourseRecords(courseId);
		Map<String, String> mapper = new HashMap<String, String>();
		for (StudentCourseFacultyBean sfb : getAllStudentCourseRecordsList) {
			mapper.put(sfb.getStudent_sap_id(), sfb.getCourse_id());
		}
		return mapper;
	}

	public Map<String, String> getStudentsForCouse(String courseId) {
		List<StudentBean> studentList = studentCourseFacultydao
				.getStudentsForCourse(courseId);

		Map<String, String> mapper = new LinkedHashMap<String, String>();
		for (StudentBean sfb : studentList) {
			mapper.put(sfb.getSap_id(), sfb.getStudent_name());
		}

		return mapper;
	}

	public void deleteStudentList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		studentCourseFacultydao
				.getNamedParameterJdbcTemplate()
				.update("delete from student_course_faculty  WHERE student_sap_id IN (:ids)",
						parameters);

	}

	public List<StudentCourseFacultyBean> findByCourseActive(String courseId) {
		return studentCourseFacultydao.findByCourseActive(courseId);
	}
}
