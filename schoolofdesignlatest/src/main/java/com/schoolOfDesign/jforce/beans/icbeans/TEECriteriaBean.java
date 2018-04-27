package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TEECriteriaBean extends BaseBean implements Serializable {
	
private static final long serialVersionUID = 1L;
	
	private String tee_id;
	private String criteria_desc;
	private String weightage;
	
	private String mapping_desc;
	private String active;
	
	public String getTee_id() {
		return tee_id;
	}
	public void setTee_id(String tee_id) {
		this.tee_id = tee_id;
	}
	public String getCriteria_desc() {
		return criteria_desc;
	}
	public void setCriteria_desc(String criteria_desc) {
		this.criteria_desc = criteria_desc;
	}
	public String getWeightage() {
		return weightage;
	}
	public void setWeightage(String weightage) {
		this.weightage = weightage;
	}
	public String getMapping_desc() {
		return mapping_desc;
	}
	public void setMapping_desc(String mapping_desc) {
		this.mapping_desc = mapping_desc;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "TEECriteriaBean [tee_id=" + tee_id + ", criteria_desc="
				+ criteria_desc + ", weightage=" + weightage
				+ ", mapping_desc=" + mapping_desc + ", active=" + active + "]";
	}

	
}
