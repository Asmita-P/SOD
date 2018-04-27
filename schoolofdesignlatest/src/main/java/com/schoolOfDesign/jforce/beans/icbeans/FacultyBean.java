package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class FacultyBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sap_id;
	private String faculty_name;
	
	private String faculty_email_id;
	private String faculty_phone_no;
	private String faculty_type;
	private String vendor_code;
	
	private String no_of_session_scheduled;
	private String no_of_session_taken;
	private String ica_created;
	private String ica_invited;
	private String ica_submitted;
	private String ica_reexam;
	
	private String ica_duplicate;
	
	private String no_of_hrs_taken;
	private String active;
	
	


	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSap_id() {
		return sap_id;
	}

	public void setSap_id(String sap_id) {
		this.sap_id = sap_id;
	}

	public String getFaculty_name() {
		return faculty_name;
	}

	public void setFaculty_name(String faculty_name) {
		this.faculty_name = faculty_name;
	}

	

	public String getFaculty_email_id() {
		return faculty_email_id;
	}

	public void setFaculty_email_id(String faculty_email_id) {
		this.faculty_email_id = faculty_email_id;
	}

	public String getFaculty_type() {
		return faculty_type;
	}

	public void setFaculty_type(String faculty_type) {
		this.faculty_type = faculty_type;
	}

	public String getVendor_code() {
		return vendor_code;
	}

	public void setVendor_code(String vendor_code) {
		this.vendor_code = vendor_code;
	}

	

	public String getNo_of_session_scheduled() {
		return no_of_session_scheduled;
	}

	public void setNo_of_session_scheduled(String no_of_session_scheduled) {
		this.no_of_session_scheduled = no_of_session_scheduled;
	}

	public String getNo_of_session_taken() {
		return no_of_session_taken;
	}

	public void setNo_of_session_taken(String no_of_session_taken) {
		this.no_of_session_taken = no_of_session_taken;
	}

	

	
	public String getIca_created() {
		return ica_created;
	}

	public void setIca_created(String ica_created) {
		this.ica_created = ica_created;
	}

	public String getIca_invited() {
		return ica_invited;
	}

	public void setIca_invited(String ica_invited) {
		this.ica_invited = ica_invited;
	}

	public String getIca_submitted() {
		return ica_submitted;
	}

	public void setIca_submitted(String ica_submitted) {
		this.ica_submitted = ica_submitted;
	}

	public String getIca_reexam() {
		return ica_reexam;
	}

	public void setIca_reexam(String ica_reexam) {
		this.ica_reexam = ica_reexam;
	}

	public String getIca_duplicate() {
		return ica_duplicate;
	}

	public void setIca_duplicate(String ica_duplicate) {
		this.ica_duplicate = ica_duplicate;
	}

	public String getNo_of_hrs_taken() {
		return no_of_hrs_taken;
	}

	public void setNo_of_hrs_taken(String no_of_hrs_taken) {
		this.no_of_hrs_taken = no_of_hrs_taken;
	}

	@Override
	public String toString() {
		return "FacultyBean [sap_id=" + sap_id + ", faculty_name="
				+ faculty_name + ", faculty_email_id=" + faculty_email_id
				+ ", faculty_phone_no=" + faculty_phone_no + ", faculty_type="
				+ faculty_type + ", vendor_code=" + vendor_code
				+ ", no_of_session_scheduled=" + no_of_session_scheduled
				+ ", no_of_session_taken=" + no_of_session_taken
				+ ", ica_created=" + ica_created + ", ica_invited="
				+ ica_invited + ", ica_submitted=" + ica_submitted
				+ ", ica_reexam=" + ica_reexam + ", ica_duplicate="
				+ ica_duplicate + ", no_of_hrs_taken=" + no_of_hrs_taken
				+ ", active=" + active + "]";
	}

	public String getFaculty_phone_no() {
		return faculty_phone_no;
	}

	public void setFaculty_phone_no(String faculty_phone_no) {
		this.faculty_phone_no = faculty_phone_no;
	}

}
