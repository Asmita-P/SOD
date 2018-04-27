package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.FacultyBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class FacultyDao extends BaseDao<FacultyBean> {

	@Override
	protected String getTableName() {
		return "faculty";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(sap_id, faculty_name,faculty_email_id,faculty_phone_no,faculty_type,vendor_code,no_of_session_scheduled,"
				+ " no_of_session_taken,ica_created,ica_invited,ica_submitted,ica_reexam,ica_duplicate,no_of_hrs_taken,active"
				+ ") values(:sap_id, :faculty_name,:faculty_email_id,:faculty_phone_no,:faculty_type,:vendor_code,:no_of_session_scheduled,"
				+ " :no_of_session_taken,:ica_created,:ica_invited,:ica_submitted,:ica_reexam,:ica_duplicate,:no_of_hrs_taken,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update faculty set" 
				
				+ " faculty_name=:faculty_name, "
				+ " sap_id=:sap_id, "
				+ " faculty_email_id=:faculty_email_id, "
				+ "  faculty_phone_no = :faculty_phone_no,"
				+ "  faculty_type = :faculty_type,"
				+ "  vendor_code = :vendor_code,"

				+ "  no_of_session_scheduled = :no_of_session_scheduled,"
				+ "  no_of_session_taken = :no_of_session_taken,"
				+ "  ica_created = :ica_created,"
				+ "  ica_invited = :ica_invited,"
				+ "  ica_submitted = :ica_submitted,"
				+ "  ica_reexam = :ica_reexam,"
				+ "  ica_duplicate = :ica_duplicate," 
			
				+ "  no_of_hrs_taken = :no_of_hrs_taken,"
				+ "  active = :active"

				+ " where sap_id=:sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
