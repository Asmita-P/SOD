package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TEETotalBean extends BaseBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String sap_id;
	private String tee_id;
	private String tee_total;
	private String active;
	private String weighted_total;
	
	public String getSap_id() {
		return sap_id;
	}
	public void setSap_id(String sap_id) {
		this.sap_id = sap_id;
	}
	public String getTee_id() {
		return tee_id;
	}
	public void setTee_id(String tee_id) {
		this.tee_id = tee_id;
	}
	public String getTee_total() {
		return tee_total;
	}
	public void setTee_total(String tee_total) {
		this.tee_total = tee_total;
	}
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	public String getWeighted_total() {
		return weighted_total;
	}
	public void setWeighted_total(String weighted_total) {
		this.weighted_total = weighted_total;
	}
	@Override
	public String toString() {
		return "TEETotalBean [sap_id=" + sap_id + ", tee_id=" + tee_id
				+ ", tee_total=" + tee_total + ", active=" + active
				+ ", weighted_total=" + weighted_total + "]";
	}

	
}
