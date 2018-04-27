package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TEEBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String course_id;
	private String owner_faculty;
	private String assigned_faculty;
	private String is_reexam;
	private String org_tee_id;
	private String status;
	private String active;
	private String teeType;
	private String tee_percent;

	public String getTeeType() {
		return teeType;
	}

	public void setTeeType(String teeType) {
		this.teeType = teeType;
	}

	public String getTee_percent() {
		return tee_percent;
	}

	public void setTee_percent(String tee_percent) {
		this.tee_percent = tee_percent;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private String isEdit;

	public String getIsEdit() {
		return isEdit;
	}

	public void setIsEdit(String isEdit) {
		this.isEdit = isEdit;
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

	public String getOrg_tee_id() {
		return org_tee_id;
	}

	public void setOrg_tee_id(String org_tee_id) {
		this.org_tee_id = org_tee_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "TEEBean [course_id=" + course_id + ", owner_faculty="
				+ owner_faculty + ", assigned_faculty=" + assigned_faculty
				+ ", is_reexam=" + is_reexam + ", org_tee_id=" + org_tee_id
				+ ", status=" + status + ", active=" + active + ", teeType="
				+ teeType + ", tee_percent=" + tee_percent + ", isEdit="
				+ isEdit + "]";
	}



}
