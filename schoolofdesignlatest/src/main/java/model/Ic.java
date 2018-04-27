package model;

import java.util.List;

public class Ic {

	String icId;
	String displayName;
	String courseName;
	String ownerfaculyName;
	String assignedfacultyName;
	boolean isRepeat;
	String orginalCid;
	String status;
	String assignedFacultyId;
	String grade;
	String iceName;
	public String getIceName() {
		return iceName;
	}

	public void setIceName(String iceName) {
		this.iceName = iceName;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getAssignedFacultyId() {
		return assignedFacultyId;
	}

	public void setAssignedFacultyId(String assignedFacultyId) {
		this.assignedFacultyId = assignedFacultyId;
	}

	List<IcCriteria> criteriaList;

	public List<IcCriteria> getCriteriaList() {
		return criteriaList;
	}

	public void setCriteriaList(List<IcCriteria> criteriaList) {
		this.criteriaList = criteriaList;
	}

	public String getIcId() {
		return icId;
	}

	public void setIcId(String icId) {
		this.icId = icId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getOwnerfaculyName() {
		return ownerfaculyName;
	}

	public void setOwnerfaculyName(String ownerfaculyName) {
		this.ownerfaculyName = ownerfaculyName;
	}

	public String getAssignedfacultyName() {
		return assignedfacultyName;
	}

	public void setAssignedfacultyName(String assignedfacultyName) {
		this.assignedfacultyName = assignedfacultyName;
	}

	

	public boolean isRepeat() {
		return isRepeat;
	}

	public void setRepeat(boolean isRepeat) {
		this.isRepeat = isRepeat;
	}

	public String getOrginalCid() {
		return orginalCid;
	}

	public void setOrginalCid(String orginalCid) {
		this.orginalCid = orginalCid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
