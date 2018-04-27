package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.beans.icbeans.StudentCourseFacultyBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class StudentCourseFacultyDao extends BaseDao<StudentCourseFacultyBean> {

	@Override
	protected String getTableName() {
		return "student_course_faculty";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(student_sap_id, faculty_sap_id,course_id,acad_year,acad_month,active"
				+ ") values(:student_sap_id, :faculty_sap_id,:course_id ,:acad_year,:acad_month,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update student_course_faculty set "
				+ " student_sap_id=:student_sap_id ,"
				+ " faculty_sap_id=:faculty_sap_id ,"
				+ " course_id=:course_id ,"
				+ " acad_year=:acad_year ,"
				+ " acad_month=:acad_month ,"
				+ " active=:active"

				+ " where course_id=:course_id and student_sap_id=:student_sap_id and faculty_sap_id=:faculty_sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

	public List<StudentCourseFacultyBean> getAllStudentCourseRecords() {
		return findAll();
	}

	public List<StudentCourseFacultyBean> getAllStudentCourseRecords(
			String courseId) {
		return findAllSQL(
				"select * from student_course_faculty where course_id = ?",
				new Object[] { courseId });
	}

	@Autowired
	StudentDao studentDao;

	public List<StudentBean> getStudentsForCourse(String courseId) {
		return studentDao
				.findAllSQL(
						"select * from student_course_faculty scf, student s where  s.sap_id = student_sap_id and "
								+ " scf.course_id = ? and s.active = 'Y' order by s.sap_id asc",
						new Object[] { courseId });
	}

	public List<StudentCourseFacultyBean> findByCourseActive(String courseId) {
		String sql = "select * from student_course_faculty scf where scf.course_id = ? and scf.active = 'Y'";
		return findAllSQL(sql, new Object[] { courseId });
	}
}
