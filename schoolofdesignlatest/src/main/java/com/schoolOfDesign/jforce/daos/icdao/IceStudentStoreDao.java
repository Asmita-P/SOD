package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.IceStudentStoreBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class IceStudentStoreDao extends BaseDao<IceStudentStoreBean> {

	@Override
	protected String getTableName() {
		return "ice_student_store";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(sap_id, ice_id,score_earned,weighted_score,total,criteria_id"
				+ ") values(:sap_id,:ice_id,:score_earned,:weighted_score,:total,:criteria_id "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update ice_student_store  "
				+ " sap_id=:sap_id, "
				+ " ice_id=:ice_id, " 
				+ " score_earned=:score_earned, "
				+ " weighted_score=: weighted_score," 
				+ " total=:total,"
				+ " criteria_id=:criteria_id"

				+ " where criteria_id=:criteria_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
