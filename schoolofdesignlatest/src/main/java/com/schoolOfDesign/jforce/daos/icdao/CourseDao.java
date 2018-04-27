package com.schoolOfDesign.jforce.daos.icdao;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class CourseDao extends BaseDao<CourseBean> {

	@Override
	protected String getTableName() {
		return "course";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(course_name, sap_course_id,module_abbr,module_name,prog_name,prog_code,subject_code,subject_abbr,session,registered,year,students_booked_to_event,ica_tee_split,active"
				+ ") values(:course_name,:sap_course_id,:module_abbr,:module_name,:prog_name,:prog_code,:subject_code,:subject_abbr,:session,:registered,:year,:students_booked_to_event,:ica_tee_split,:active "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update course set" 
				+ " course_name=:course_name, "
				+ " sap_course_id=:sap_course_id, "
				+ " module_abbr=:module_abbr, " 
				+ " module_name=:module_name, "
				+ " prog_name=:prog_name, " 
				+ " prog_code=:prog_code, "
				+ " subject_code=:subject_code, "
				+ " subject_abbr=:subject_abbr, " 
				+ " session=:session, "
				+ " registered=:registered, " 
				+ " year=:year, "
				+ " active=:active, "
				+ " students_booked_to_event=:students_booked_to_event, "
				+ " ica_tee_split=:ica_tee_split"

				+ " where sap_course_id=:sap_course_id";
		return sql;
	}

	public CourseBean findOne(final String id) {
		final String sql = "SELECT * FROM " + getTableName()
				+ " WHERE sap_course_id = ? and active='Y'";
		return findOneSQL(sql, new Object[] { id });
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
