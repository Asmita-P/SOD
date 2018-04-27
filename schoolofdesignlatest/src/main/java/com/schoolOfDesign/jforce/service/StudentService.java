package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.daos.icdao.StudentDao;

@Service
public class StudentService {
	@Autowired
	StudentDao studentDao;

	public void insertStudentList(ArrayList<StudentBean> studentList) {
		studentDao.insertBatch(studentList);
	}

	public void updateStudentList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		studentDao.getNamedParameterJdbcTemplate().update(
				"update student set active='N'   WHERE sap_id IN (:ids)",
				parameters);

	}

	public void deleteStudentList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		studentDao.getNamedParameterJdbcTemplate().update(
				"delete from student  WHERE sap_id IN (:ids) and active='Y'",
				parameters);

	}

	public String findRollNo(String sapId) {
		return studentDao.getJdbcTemplate().queryForObject(
				"select rollNo from sap_roll where sap_id=?", String.class,
				new Object[] { sapId });
	}

	public String findSapId(String studentName) {
		return studentDao.getJdbcTemplate().queryForObject(
				"select distinct sap_id from student s where student_name = ?",
				String.class, new Object[] { studentName });
	}

}
