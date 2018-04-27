package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class IceCutoffBean extends BaseBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ice_id;
	private int cutoff_id;

	private String active;
	
	public int getIce_id() {
		return ice_id;
	}
	public void setIce_id(int ice_id) {
		this.ice_id = ice_id;
	}
	public int getCutoff_id() {
		return cutoff_id;
	}
	public void setCutoff_id(int cutoff_id) {
		this.cutoff_id = cutoff_id;
	}
	
	public String getActive() {
		return active;
	}
	public void setActive(String active) {
		this.active = active;
	}
	@Override
	public String toString() {
		return "IceCutoffBean [ice_id=" + ice_id + ", cutoff_id=" + cutoff_id
				+ ", active=" + active + "]";
	}

}
