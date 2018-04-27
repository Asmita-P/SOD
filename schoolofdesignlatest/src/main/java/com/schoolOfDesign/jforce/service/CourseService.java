package com.schoolOfDesign.jforce.service;

import java.util.List;
import java.util.Map;

import org.hibernate.validator.internal.xml.GetterType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.daos.icdao.CourseDao;

@Service
public class CourseService {

	@Autowired
	CourseDao courseDao;

	public List<CourseBean> getCoursesAssignedToFaculty(String facultyId) {
		return courseDao
				.findAllSQL(
						"SELECT  distinct c.sap_course_id , c.course_name FROM schoolofdesign.student_course_faculty scf, faculty f ,course c "
								+ "where  f.sap_id = scf.faculty_sap_id  and c.sap_course_id = scf.course_id and f.sap_id=?"

						, new Object[] { facultyId });
	}

	public List<CourseBean> getCoursesAssignedToFacultyAndSemester(
			String facultyId, String semester) {
		return courseDao
				.findAllSQL(
						"SELECT  distinct c.sap_course_id , c.course_name FROM schoolofdesign.student_course_faculty scf, faculty f ,course c "
								+ "where  f.sap_id = scf.faculty_sap_id  and c.sap_course_id = scf.course_id and f.sap_id=? and c.session=?"

						, new Object[] { facultyId, semester });
	}

	public List<CourseBean> getAllCoursesBySemester(String semester) {
		return courseDao.findAllSQL("select * from course where session=?",
				new Object[] { semester });
	}

	public List<CourseBean> getAllCourses() {
		return courseDao.findAll();
	}

	public CourseBean getCourse(String courseId) {
		return courseDao.findOne(courseId);
	}

	public void insertCourseList(List<CourseBean> courseList) {
		courseDao.insertBatch(courseList);
	}

	public void updateCourse(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		courseDao.getNamedParameterJdbcTemplate().update(
				"update course set active='N'   WHERE sap_course_id IN (:ids)",
				parameters);

	}

	public void deleteCourseList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		courseDao
				.getNamedParameterJdbcTemplate()
				.update("delete from course  WHERE sap_course_id IN (:ids) and active='Y'",
						parameters);

	}
}
