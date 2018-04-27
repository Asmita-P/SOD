package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class UserDetailsBean extends BaseBean implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String username;
	private String first_name;
	private String last_name;
	private String email;
	private String phone_no;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	@Override
	public String toString() {
		return "UserDetailsBean [username=" + username + ", first_name="
				+ first_name + ", last_name=" + last_name + ", email=" + email
				+ ", phone_no=" + phone_no + "]";
	}
	

}
