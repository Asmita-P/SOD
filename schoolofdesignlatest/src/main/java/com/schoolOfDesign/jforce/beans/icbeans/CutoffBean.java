package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class CutoffBean extends BaseBean implements Serializable{

	
	private static final long serialVersionUID = 1L;
	
	private int cutoff;
	private String criteria;
	private String active;
	
	
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
	
	@Override
	public String toString() {
		return "CutoffBean [cutoff=" + cutoff + ", criteria=" + criteria
				+ ", active=" + active + "]";
	}
	
	
}
