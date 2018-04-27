package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class CourseBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String course_name;
	private String sap_course_id;
	private String module_abbr;
	private String module_name;
	private String prog_name;
	private String prog_code;
	private String subject_code;
	private String subject_abbr;
	private String session;
	private String registered;
	private String year;
	private String students_booked_to_event;
	private String  ica_tee_split;
	private String active;
	
	
	public String getActive() {
		return active;
	}



	public void setActive(String active) {
		this.active = active;
	}



	public String getModule_abbr() {
		return module_abbr;
	}



	public void setModule_abbr(String module_abbr) {
		this.module_abbr = module_abbr;
	}



	public String getModule_name() {
		return module_name;
	}



	public void setModule_name(String module_name) {
		this.module_name = module_name;
	}



	public String getProg_name() {
		return prog_name;
	}



	public void setProg_name(String prog_name) {
		this.prog_name = prog_name;
	}



	public String getProg_code() {
		return prog_code;
	}



	public void setProg_code(String prog_code) {
		this.prog_code = prog_code;
	}



	public String getSubject_code() {
		return subject_code;
	}



	public void setSubject_code(String subject_code) {
		this.subject_code = subject_code;
	}



	public String getSubject_abbr() {
		return subject_abbr;
	}



	public void setSubject_abbr(String subject_abbr) {
		this.subject_abbr = subject_abbr;
	}



	public String getSession() {
		return session;
	}



	public void setSession(String session) {
		this.session = session;
	}



	public String getRegistered() {
		return registered;
	}



	public void setRegistered(String registered) {
		this.registered = registered;
	}



	public String getYear() {
		return year;
	}



	public void setYear(String year) {
		this.year = year;
	}



	public String getStudents_booked_to_event() {
		return students_booked_to_event;
	}



	public void setStudents_booked_to_event(String students_booked_to_event) {
		this.students_booked_to_event = students_booked_to_event;
	}



	



	public String getIca_tee_split() {
		return ica_tee_split;
	}



	public void setIca_tee_split(String ica_tee_split) {
		this.ica_tee_split = ica_tee_split;
	}



	


	@Override
	public String toString() {
		return "CourseBean [course_name=" + course_name + ", sap_course_id="
				+ sap_course_id + ", module_abbr=" + module_abbr
				+ ", module_name=" + module_name + ", prog_name=" + prog_name
				+ ", prog_code=" + prog_code + ", subject_code=" + subject_code
				+ ", subject_abbr=" + subject_abbr + ", session=" + session
				+ ", registered=" + registered + ", year=" + year
				+ ", students_booked_to_event=" + students_booked_to_event
				+ ", ica_tee_split=" + ica_tee_split + ", active=" + active
				+ "]";
	}



	public String getCourse_name() {
		return course_name;
	}

	public void setCourse_name(String course_name) {
		this.course_name = course_name;
	}

	public String getSap_course_id() {
		return sap_course_id;
	}

	public void setSap_course_id(String sap_course_id) {
		this.sap_course_id = sap_course_id;
	}

}
