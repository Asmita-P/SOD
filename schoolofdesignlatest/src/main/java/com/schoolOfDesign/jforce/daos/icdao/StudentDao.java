package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.StudentBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class StudentDao extends BaseDao<StudentBean> {

	@Override
	protected String getTableName() {
		return "student";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(sap_id,dateOfBirth,student_name,last_name,first_name,mother_name,father_name,middle_name,phone_no,father_mobileNo,address,street_1,street_2,city,state,zipcode,email_id,"
				+ "  roll_no,gender,reg_prog_abbr,enroll_year,session,current_prog_abbr,current_prog_desc,parent_email,parent_phone,current_acad_year,current_acad_session,active"
				+ ") values(:sap_id,:dateOfBirth,:student_name,:last_name,:first_name,:mother_name,:father_name,:middle_name,:phone_no,:father_mobileNo,:address,:street_1,:street_2,:city,:state,:zipcode,:email_id,"
				+ "  :roll_no,:gender,:reg_prog_abbr,:enroll_year,:session,:current_prog_abbr,:current_prog_desc,:parent_email,:parent_phone,:current_acad_year,:current_acad_session,:active "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update student set " + " sap_id=:sap_id,"
				+ " dateOfBirth=:dateOfBirth,"
				+ " student_name=:student_name, " + " last_name=:last_name, "
				+ "   first_name=:first_name," + "   mother_name=:mother_name,"
				+ "   father_name=:father_name,"
				+ "   middle_name=:middle_name," + "   phone_no=:phone_no,"
				+ "   father_mobileNo=:father_mobileNo,"
				+ "   address=:address," + "   street_1=:street_1,"
				+ "   street_2=:street_2," + "   city=:city,"
				+ "   state=:state," + "   zipcode=:zipcode,"
				+ "   email_id=:email_id," + "   roll_no=:roll_no,"
				+ "   gender=:gender," + "   reg_prog_abbr=:reg_prog_abbr,"
				+ "   enroll_year=:enroll_year," + "   session=:session,"
				+ "   current_prog_abbr=:current_prog_abbr,"
				+ "   current_prog_desc=:current_prog_desc,"
				+ "   parent_email=:parent_email,"
				+ "   parent_phone=:parent_phone,"
				+ "   current_acad_year=:current_acad_year,"
				+ "   current_acad_session=:current_acad_session,"
				+ "   active=:active"

				+ " where sap_id=:sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

/*	public String findRollNo(String sapId) {
		String sql = "select rollNo from sap_roll where sap_id=?";
		return getJdbcTemplate().queryForObject(sql, String.class,
				new Object[] { sapId });
	}*/

}
