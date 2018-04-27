package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TermEndMarksBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class TermEndMarksDao extends BaseDao<TermEndMarksBean> {

	@Override
	protected String getTableName() {
		return "term_end_marks";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(sap_id,score_earned,course_id,ice_weighted,total,active"
				+ ") values(:sap_id,:score_earned,:course_id,:ice_weighted,:total,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update term_end_marks set " 
		
				+ " sap_id=:sap_id, "
				+ " score_earned=:score_earned, "
				+ " course_id=:course_id ,"
				+ "ice_weighted=:ice_weighted,"
				+ "total=:total, "
				+ " active=:active"
			

				+ " where sap_id=:sap_id and course_id=:course_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
