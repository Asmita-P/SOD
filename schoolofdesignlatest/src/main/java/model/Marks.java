package model;

public class Marks {

	@Override
	public String toString() {
		return "Marks [sapId=" + sapId + ", name=" + name
				+ ", criteria_1_marks=" + criteria_1_marks
				+ ", criteria_2_marks=" + criteria_2_marks
				+ ", criteria_3_marks=" + criteria_3_marks
				+ ", criteria_4_marks=" + criteria_4_marks + ", criteriaTotal="
				+ criteriaTotal + ", weightedTotal=" + weightedTotal
				+ ", rollNo=" + rollNo + ", criteria_5_marks="
				+ criteria_5_marks + ", criteria_6_marks=" + criteria_6_marks
				+ ", criteria_7_marks=" + criteria_7_marks
				+ ", criteria_8_marks=" + criteria_8_marks
				+ ", criteria_9_marks=" + criteria_9_marks
				+ ", criteria_10_marks=" + criteria_10_marks + ", weightage_1="
				+ weightage_1 + ", weightage_2=" + weightage_2
				+ ", weightage_3=" + weightage_3 + ", weightage_4="
				+ weightage_4 + ", weightage_5=" + weightage_5
				+ ", weightage_6=" + weightage_6 + ", weightage_7="
				+ weightage_7 + ", weightage_8=" + weightage_8
				+ ", weightage_9=" + weightage_9 + ", weightage_10="
				+ weightage_10 + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sapId == null) ? 0 : sapId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Marks other = (Marks) obj;
		if (sapId == null) {
			if (other.sapId != null)
				return false;
		} else if (!sapId.equals(other.sapId))
			return false;
		return true;
	}

	String sapId;
	String name;
	String criteria_1_marks;
	String criteria_2_marks;
	String criteria_3_marks;
	String criteria_4_marks;
	String criteriaTotal;
	String weightedTotal;
	String rollNo;
	String remarks;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRollNo() {
		return rollNo;
	}

	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}

	public String getCriteriaTotal() {
		return criteriaTotal;
	}

	public void setCriteriaTotal(String criteriaTotal) {
		this.criteriaTotal = criteriaTotal;
	}

	public String getWeightedTotal() {
		return weightedTotal;
	}

	public void setWeightedTotal(String weightedTotal) {
		this.weightedTotal = weightedTotal;
	}

	public String getSapId() {
		return sapId;
	}

	public void setSapId(String sapId) {
		this.sapId = sapId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCriteria_1_marks() {
		return criteria_1_marks;
	}

	public void setCriteria_1_marks(String criteria_1_marks) {
		this.criteria_1_marks = criteria_1_marks;
	}

	public String getCriteria_2_marks() {
		return criteria_2_marks;
	}

	public void setCriteria_2_marks(String criteria_2_marks) {
		this.criteria_2_marks = criteria_2_marks;
	}

	public String getCriteria_3_marks() {
		return criteria_3_marks;
	}

	public void setCriteria_3_marks(String criteria_3_marks) {
		this.criteria_3_marks = criteria_3_marks;
	}

	public String getCriteria_4_marks() {
		return criteria_4_marks;
	}

	public void setCriteria_4_marks(String criteria_4_marks) {
		this.criteria_4_marks = criteria_4_marks;
	}

	public String getCriteria_5_marks() {
		return criteria_5_marks;
	}

	public void setCriteria_5_marks(String criteria_5_marks) {
		this.criteria_5_marks = criteria_5_marks;
	}

	public String getCriteria_6_marks() {
		return criteria_6_marks;
	}

	public void setCriteria_6_marks(String criteria_6_marks) {
		this.criteria_6_marks = criteria_6_marks;
	}

	public String getCriteria_7_marks() {
		return criteria_7_marks;
	}

	public void setCriteria_7_marks(String criteria_7_marks) {
		this.criteria_7_marks = criteria_7_marks;
	}

	public String getCriteria_8_marks() {
		return criteria_8_marks;
	}

	public void setCriteria_8_marks(String criteria_8_marks) {
		this.criteria_8_marks = criteria_8_marks;
	}

	public String getCriteria_9_marks() {
		return criteria_9_marks;
	}

	public void setCriteria_9_marks(String criteria_9_marks) {
		this.criteria_9_marks = criteria_9_marks;
	}

	public String getCriteria_10_marks() {
		return criteria_10_marks;
	}

	public void setCriteria_10_marks(String criteria_10_marks) {
		this.criteria_10_marks = criteria_10_marks;
	}

	public String getWeightage_1() {
		return weightage_1;
	}

	public void setWeightage_1(String weightage_1) {
		this.weightage_1 = weightage_1;
	}

	public String getWeightage_2() {
		return weightage_2;
	}

	public void setWeightage_2(String weightage_2) {
		this.weightage_2 = weightage_2;
	}

	public String getWeightage_3() {
		return weightage_3;
	}

	public void setWeightage_3(String weightage_3) {
		this.weightage_3 = weightage_3;
	}

	public String getWeightage_4() {
		return weightage_4;
	}

	public void setWeightage_4(String weightage_4) {
		this.weightage_4 = weightage_4;
	}

	public String getWeightage_5() {
		return weightage_5;
	}

	public void setWeightage_5(String weightage_5) {
		this.weightage_5 = weightage_5;
	}

	public String getWeightage_6() {
		return weightage_6;
	}

	public void setWeightage_6(String weightage_6) {
		this.weightage_6 = weightage_6;
	}

	public String getWeightage_7() {
		return weightage_7;
	}

	public void setWeightage_7(String weightage_7) {
		this.weightage_7 = weightage_7;
	}

	public String getWeightage_8() {
		return weightage_8;
	}

	public void setWeightage_8(String weightage_8) {
		this.weightage_8 = weightage_8;
	}

	public String getWeightage_9() {
		return weightage_9;
	}

	public void setWeightage_9(String weightage_9) {
		this.weightage_9 = weightage_9;
	}

	public String getWeightage_10() {
		return weightage_10;
	}

	public void setWeightage_10(String weightage_10) {
		this.weightage_10 = weightage_10;
	}

	String criteria_5_marks;
	String criteria_6_marks;
	String criteria_7_marks;
	String criteria_8_marks;
	String criteria_9_marks;
	String criteria_10_marks;
	String weightage_1;
	String weightage_2;
	String weightage_3;
	String weightage_4;
	String weightage_5;
	String weightage_6;
	String weightage_7;
	String weightage_8;
	String weightage_9;
	String weightage_10;

}
