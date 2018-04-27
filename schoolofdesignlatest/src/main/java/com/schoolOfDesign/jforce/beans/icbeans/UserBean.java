package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;
import java.util.List;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class UserBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String password;
	private String active;
	
	UserDetailsBean userDetails;
	List<String> roles;
	String roleName;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public UserDetailsBean getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetailsBean userDetails) {
		this.userDetails = userDetails;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserBean [username=" + username + ", password=" + password
				+ ", active=" + active + ", userDetails=" + userDetails
				+ ", roles=" + roles + ", roleName=" + roleName + "]";
	}

}
