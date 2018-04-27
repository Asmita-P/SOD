package com.schoolOfDesign.jforce.beans.icbeans;

import java.io.Serializable;

import com.schoolOfDesign.jforce.beans.BaseBean;

public class TeeMetadataBean extends BaseBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String course_id;
	private String ice_percent;
	private String written_percent;
	private String active;

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

	public String getIce_percent() {
		return ice_percent;
	}

	public void setIce_percent(String ice_percent) {
		this.ice_percent = ice_percent;
	}

	public String getWritten_percent() {
		return written_percent;
	}

	public void setWritten_percent(String written_percent) {
		this.written_percent = written_percent;
	}

	@Override
	public String toString() {
		return "TeeMetadataBean [course_id=" + course_id + ", ice_percent="
				+ ice_percent + ", written_percent=" + written_percent
				+ ", active=" + active + "]";
	}

}
