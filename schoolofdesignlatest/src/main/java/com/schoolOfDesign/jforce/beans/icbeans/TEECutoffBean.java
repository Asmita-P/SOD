package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TEECutoffBean extends BaseBean implements Serializable {
	
	
		private static final long serialVersionUID = 1L;
		
		private int tee_id;
		private int cutoff_id;

		private String active;

		public int getTee_id() {
			return tee_id;
		}

		public void setTee_id(int tee_id) {
			this.tee_id = tee_id;
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
			return "TEECutoffBean [tee_id=" + tee_id + ", cutoff_id="
					+ cutoff_id + ", active=" + active + "]";
		}
		
		

}
