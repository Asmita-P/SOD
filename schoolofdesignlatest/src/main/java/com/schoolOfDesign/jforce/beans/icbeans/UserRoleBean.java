package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class UserRoleBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private int role_id;
	private String active;

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public int getRole_id() {
		return role_id;
	}

	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}

	@Override
	public String toString() {
		return "UserRoleBean [username=" + username + ", role_id=" + role_id
				+ ", active=" + active + "]";
	}

}
