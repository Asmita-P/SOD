package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.FacultyBean;
import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.daos.icdao.FacultyDao;
import com.schoolOfDesign.jforce.daos.icdao.StudentDao;

@Service
public class FacultyService {

	@Autowired
	FacultyDao facultyDao;

	public List<FacultyBean> getFaculty(String facultyId) {
		return facultyDao
				.findAllSQL(
						"SELECT  distinct c.sap_course_id , c.course_name FROM schoolofdesign.student_course_faculty scf, faculty f ,course c "
								+ "where  f.sap_id = scf.faculty_sap_id  and c.sap_course_id = scf.course_id and f.sap_id=?"

						, new Object[] { facultyId });
	}

	public List<FacultyBean> getFacultyList() {
		return facultyDao.findAllActive();
	}

	public void insertFacultyList(ArrayList<FacultyBean> FacultyList) {
		facultyDao.insertBatch(FacultyList);
	}

	public void updateFacultyList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		facultyDao.getNamedParameterJdbcTemplate().update(
				"update faculty set active='N'   WHERE sap_id IN (:ids)",
				parameters);

	}

	public void deleteFacultyList(List<String> ids) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);

		facultyDao.getNamedParameterJdbcTemplate().update(
				"delete from faculty  WHERE sap_id IN (:ids) and active='Y'",
				parameters);

	}

	public FacultyBean createExternalFaculty(String sap_id,
			String faculty_name, String faculty_email_id,
			String faculty_phone_no, String vendor_code) {

		FacultyBean faculty = new FacultyBean();
		faculty.setSap_id(sap_id);
		faculty.setId(Long.valueOf(sap_id));
		faculty.setFaculty_name(faculty_name);
		faculty.setFaculty_email_id(faculty_email_id);
		faculty.setFaculty_phone_no(faculty_phone_no);
		faculty.setFaculty_type("E");
		faculty.setVendor_code(vendor_code);

		faculty.setActive("Y");
		System.out.println("faculty bean ->" + faculty);
		List<FacultyBean> fBeanList = new ArrayList<FacultyBean>();
		fBeanList.add(faculty);
		facultyDao.insertBatch(fBeanList);

		return faculty;
	}
}
