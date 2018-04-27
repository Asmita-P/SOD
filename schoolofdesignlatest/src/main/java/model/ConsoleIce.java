package model;

import java.util.Map;

public class ConsoleIce {

	String sapId;
	String name;
	
	Map<String, String> iceMarks;
	String total;
	String scored;
	String calc;
	String cname;
	String rollNo;
	String fname;
	String lname;
	String weighted_total;
	
	public String getWeighted_total() {
		return weighted_total;
	}
	public void setWeighted_total(String weighted_total) {
		this.weighted_total = weighted_total;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getRollNo() {
		return rollNo;
	}
	public void setRollNo(String rollNo) {
		this.rollNo = rollNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getSapId() {
		return sapId;
	}
	public void setSapId(String sapId) {
		this.sapId = sapId;
	}
	
	public Map<String, String> getIceMarks() {
		return iceMarks;
	}
	public void setIceMarks(Map<String, String> iceMarks) {
		this.iceMarks = iceMarks;
	}
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getScored() {
		return scored;
	}
	public void setScored(String scored) {
		this.scored = scored;
	}
	public String getCalc() {
		return calc;
	}
	public void setCalc(String calc) {
		this.calc = calc;
	}

}
