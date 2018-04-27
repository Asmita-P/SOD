package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class RoleBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;


	private String role_name;
	private String active;

	

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	@Override
	public String toString() {
		return "RoleBean [role_name=" + role_name + ", active=" + active + "]";
	}

}
