package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	
	private String course_id;
	private String owner_faculty;
	private String assigned_faculty;
	private String is_reexam;
	private String org_ice_id;
	private String status;
	private String active;
	private String iceName;
	
	public String getIceName() {
		return iceName;
	}
	public void setIceName(String iceName) {
		this.iceName = iceName;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	public String getOwner_faculty() {
		return owner_faculty;
	}
	public void setOwner_faculty(String owner_faculty) {
		this.owner_faculty = owner_faculty;
	}
	public String getAssigned_faculty() {
		return assigned_faculty;
	}
	public void setAssigned_faculty(String assigned_faculty) {
		this.assigned_faculty = assigned_faculty;
	}
	public String getIs_reexam() {
		return is_reexam;
	}
	public void setIs_reexam(String is_reexam) {
		this.is_reexam = is_reexam;
	}
	public String getOrg_ice_id() {
		return org_ice_id;
	}
	public void setOrg_ice_id(String org_ice_id) {
		this.org_ice_id = org_ice_id;
	}
	@Override
	public String toString() {
		return "IceBean [course_id=" + course_id + ", owner_faculty="
				+ owner_faculty + ", assigned_faculty=" + assigned_faculty
				+ ", is_reexam=" + is_reexam + ", org_ice_id=" + org_ice_id
				+ ", status=" + status + ", active=" + active + "]";
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
	

}
