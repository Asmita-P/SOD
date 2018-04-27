package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceTotalBean extends BaseBean implements Serializable {
	
	
	

	private static final long serialVersionUID = 1L;

	private String sap_id;
	private String ice_id;
	private String ice_total;
	private String active;
	private String weighted_total;
	

	public String getIce_id() {
		return ice_id;
	}

	public void setIce_id(String ice_id) {
		this.ice_id = ice_id;
	}

	public String getWeighted_total() {
		return weighted_total;
	}

	public void setWeighted_total(String weighted_total) {
		this.weighted_total = weighted_total;
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

	public String getIce_total() {
		return ice_total;
	}

	public void setIce_total(String ice_total) {
		this.ice_total = ice_total;
	}
	
	@Override
	public String toString() {
		return "IceTotalBean [sap_id=" + sap_id + ", ice_id=" + ice_id
				+ ", ice_total=" + ice_total + ", active=" + active
				+ ", weighted_total=" + weighted_total + "]";
	}

}
