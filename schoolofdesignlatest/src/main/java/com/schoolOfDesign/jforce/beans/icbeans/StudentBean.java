package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class StudentBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sap_id;
	private String dateOfBirth;
	
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	private String student_name;
	private String last_name;
	private String first_name;
	private String mother_name;
	private String father_name;
	
	public String getFather_name() {
		return father_name;
	}

	public void setFather_name(String father_name) {
		this.father_name = father_name;
	}

	private String middle_name;
	private String phone_no;
	
	private String father_mobileNo;
	private String address;
	private String street_1;
	private String street_2;
	private String city;
	private String state;
	private String zipcode;
	private String email_id;
	
	private String roll_no;
	private String gender;
	private String reg_prog_abbr;
	private int enroll_year;
	
	private String session;
	private String current_prog_abbr;
	private String current_prog_desc;
	private String parent_email;
	private String parent_phone;
	private String current_acad_year;
	private String current_acad_session;
	private String active;
	
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRoll_no() {
		return roll_no;
	}

	public void setRoll_no(String roll_no) {
		this.roll_no = roll_no;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getReg_prog_abbr() {
		return reg_prog_abbr;
	}

	public void setReg_prog_abbr(String reg_prog_abbr) {
		this.reg_prog_abbr = reg_prog_abbr;
	}

	public int getEnroll_year() {
		return enroll_year;
	}

	public void setEnroll_year(int enroll_year) {
		this.enroll_year = enroll_year;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getCurrent_prog_abbr() {
		return current_prog_abbr;
	}

	public void setCurrent_prog_abbr(String current_prog_abbr) {
		this.current_prog_abbr = current_prog_abbr;
	}

	public String getCurrent_prog_desc() {
		return current_prog_desc;
	}

	public void setCurrent_prog_desc(String current_prog_desc) {
		this.current_prog_desc = current_prog_desc;
	}

	public String getParent_email() {
		return parent_email;
	}

	public void setParent_email(String parent_email) {
		this.parent_email = parent_email;
	}

	public String getParent_phone() {
		return parent_phone;
	}

	public void setParent_phone(String parent_phone) {
		this.parent_phone = parent_phone;
	}

	public String getCurrent_acad_year() {
		return current_acad_year;
	}

	public void setCurrent_acad_year(String current_acad_year) {
		this.current_acad_year = current_acad_year;
	}

	public String getCurrent_acad_session() {
		return current_acad_session;
	}

	public void setCurrent_acad_session(String current_acad_session) {
		this.current_acad_session = current_acad_session;
	}

	
	
	
	
	
	

	public String getSap_id() {
		return sap_id;
	}

	public void setSap_id(String sap_id) {
		this.sap_id = sap_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getMother_name() {
		return mother_name;
	}

	public void setMother_name(String mother_name) {
		this.mother_name = mother_name;
	}

	

	public String getFather_mobileNo() {
		return father_mobileNo;
	}

	public void setFather_mobileNo(String father_mobileNo) {
		this.father_mobileNo = father_mobileNo;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getStreet_1() {
		return street_1;
	}

	public void setStreet_1(String street_1) {
		this.street_1 = street_1;
	}

	public String getStreet_2() {
		return street_2;
	}

	public void setStreet_2(String street_2) {
		this.street_2 = street_2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail_id() {
		return email_id;
	}

	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}

	@Override
	public String toString() {
		return "StudentBean [sap_id=" + sap_id + ", dateOfBirth=" + dateOfBirth
				+ ", student_name=" + student_name + ", last_name=" + last_name
				+ ", first_name=" + first_name + ", mother_name=" + mother_name
				+ ", father_name=" + father_name + ", middle_name="
				+ middle_name + ", phone_no=" + phone_no + ", father_mobileNo="
				+ father_mobileNo + ", address=" + address + ", street_1="
				+ street_1 + ", street_2=" + street_2 + ", city=" + city
				+ ", state=" + state + ", zipcode=" + zipcode + ", email_id="
				+ email_id + ", roll_no=" + roll_no + ", gender=" + gender
				+ ", reg_prog_abbr=" + reg_prog_abbr + ", enroll_year="
				+ enroll_year + ", session=" + session + ", current_prog_abbr="
				+ current_prog_abbr + ", current_prog_desc="
				+ current_prog_desc + ", parent_email=" + parent_email
				+ ", parent_phone=" + parent_phone + ", current_acad_year="
				+ current_acad_year + ", current_acad_session="
				+ current_acad_session + ", active=" + active + "]";
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getPhone_no() {
		return phone_no;
	}

	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}

}
