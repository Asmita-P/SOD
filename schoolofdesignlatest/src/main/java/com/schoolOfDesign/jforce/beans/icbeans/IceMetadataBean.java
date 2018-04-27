package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceMetadataBean extends BaseBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private int cutoff;
	private String criteria;
	private String active;
	private String course_id;
	public int getCutoff() {
		return cutoff;
	}
	public void setCutoff(int cutoff) {
		this.cutoff = cutoff;
	}
	public String getCriteria() {
		return criteria;
	}
	public void setCriteria(String criteria) {
		this.criteria = criteria;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getCourse_id() {
		return course_id;
	}
	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}
	@Override
	public String toString() {
		return "IceMetadataBean [cutoff=" + cutoff + ", criteria=" + criteria
				+ ", active=" + active + ", course_id=" + course_id + "]";
	}
	
	
}
