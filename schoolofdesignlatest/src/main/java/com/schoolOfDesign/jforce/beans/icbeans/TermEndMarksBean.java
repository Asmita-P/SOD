package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TermEndMarksBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sap_id;
	private String course_id;
	private String score_earned;
	private String active;
	String ice_weighted;
	String total;

	public String getIce_weighted() {
		return ice_weighted;
	}

	public void setIce_weighted(String ice_weighted) {
		this.ice_weighted = ice_weighted;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getSap_id() {
		return sap_id;
	}

	public void setSap_id(String sap_id) {
		this.sap_id = sap_id;
	}

	

	public String getScore_earned() {
		return score_earned;
	}

	public void setScore_earned(String score_earned) {
		this.score_earned = score_earned;
	}

	public String getCourse_id() {
		return course_id;
	}

	@Override
	public String toString() {
		return "TermEndMarksBean [sap_id=" + sap_id + ", course_id="
				+ course_id + ", score_earned=" + score_earned + ", active="
				+ active + "]";
	}

}
