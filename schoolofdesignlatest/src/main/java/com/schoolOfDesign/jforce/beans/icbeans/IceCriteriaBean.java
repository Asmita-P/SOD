package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceCriteriaBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String ice_id;
	private String criteria_desc;
	private String weightage;
	
	private String mapping_desc;
	private String active;
	
	
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getIce_id() {
		return ice_id;
	}
	public void setIce_id(String ice_id) {
		this.ice_id = ice_id;
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
	@Override
	public String toString() {
		return "IceCriteriaBean [ice_id=" + ice_id + ", criteria_desc="
				+ criteria_desc + ", weightage=" + weightage
				+ ", mapping_desc=" + mapping_desc + ", active=" + active + "]";
	}
	

}
