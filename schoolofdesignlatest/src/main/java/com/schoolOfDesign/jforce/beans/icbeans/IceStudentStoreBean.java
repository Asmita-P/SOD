package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceStudentStoreBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String sap_id;
	private String ice_id;
	private int criteria_id;
	private String score_earned;
	private String weighted_score;
	private String total;
	public String getSap_id() {
		return sap_id;
	}
	public void setSap_id(String sap_id) {
		this.sap_id = sap_id;
	}
	public String getIce_id() {
		return ice_id;
	}
	public void setIce_id(String ice_id) {
		this.ice_id = ice_id;
	}
	public int getCriteria_id() {
		return criteria_id;
	}
	public void setCriteria_id(int criteria_id) {
		this.criteria_id = criteria_id;
	}
	public String getScore_earned() {
		return score_earned;
	}
	public void setScore_earned(String score_earned) {
		this.score_earned = score_earned;
	}
	public String getWeighted_score() {
		return weighted_score;
	}
	public void setWeighted_score(String weighted_score) {
		this.weighted_score = weighted_score;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	@Override
	public String toString() {
		return "IceStudentStoreBean [sap_id=" + sap_id + ", ice_id=" + ice_id
				+ ", criteria_id=" + criteria_id + ", score_earned="
				+ score_earned + ", weighted_score=" + weighted_score
				+ ", total=" + total + "]";
	}
	
	
	
	

}
