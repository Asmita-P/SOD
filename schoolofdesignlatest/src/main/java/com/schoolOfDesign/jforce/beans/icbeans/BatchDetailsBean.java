package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class BatchDetailsBean extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String batch_name;
	private String acad_year;
	private String acad_month;
	private String ice_startDate;
	private String ice_endDate;
	private String ice_midReviewDate;
	private String active;
	private String semester;
	public String getBatch_name() {
		return batch_name;
	}
	public void setBatch_name(String batch_name) {
		this.batch_name = batch_name;
	}
	public String getAcad_year() {
		return acad_year;
	}
	public void setAcad_year(String acad_year) {
		this.acad_year = acad_year;
	}
	public String getAcad_month() {
		return acad_month;
	}
	public void setAcad_month(String acad_month) {
		this.acad_month = acad_month;
	}
	public String getIce_startDate() {
		return ice_startDate;
	}
	public void setIce_startDate(String ice_startDate) {
		this.ice_startDate = ice_startDate;
	}
	public String getIce_endDate() {
		return ice_endDate;
	}
	public void setIce_endDate(String ice_endDate) {
		this.ice_endDate = ice_endDate;
	}
	public String getIce_midReviewDate() {
		return ice_midReviewDate;
	}
	public void setIce_midReviewDate(String ice_midReviewDate) {
		this.ice_midReviewDate = ice_midReviewDate;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}
	@Override
	public String toString() {
		return "BatchDetailsBean [batch_name=" + batch_name + ", acad_year="
				+ acad_year + ", acad_month=" + acad_month + ", ice_startDate="
				+ ice_startDate + ", ice_endDate=" + ice_endDate
				+ ", ice_midReviewDate=" + ice_midReviewDate + ", active="
				+ active + ", semester=" + semester + "]";
	}
	
	

}
