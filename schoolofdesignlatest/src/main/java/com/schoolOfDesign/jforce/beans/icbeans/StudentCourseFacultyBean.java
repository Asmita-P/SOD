package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class StudentCourseFacultyBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String student_sap_id;
	private String course_id;
	private String faculty_sap_id;
	private String acad_year;
	private String acad_month;
	private String active;
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	

	public String getAcad_year() {
		return acad_year;
	}

	public void setAcad_year(String acad_year) {
		this.acad_year = acad_year;
	}

	public String getAcad_month() {
		return acad_month;
	}

	public void setAcad_month(String acad_month) {
		this.acad_month = acad_month;
	}

	public String getStudent_sap_id() {
		return student_sap_id;
	}

	public void setStudent_sap_id(String student_sap_id) {
		this.student_sap_id = student_sap_id;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getFaculty_sap_id() {
		return faculty_sap_id;
	}

	public void setFaculty_sap_id(String faculty_sap_id) {
		this.faculty_sap_id = faculty_sap_id;
	}

	@Override
	public String toString() {
		return "StudentCourseFacultyBean [student_sap_id=" + student_sap_id
				+ ", course_id=" + course_id + ", faculty_sap_id="
				+ faculty_sap_id + ", acad_year=" + acad_year + ", acad_month="
				+ acad_month + ", active=" + active + "]";
	}

}
