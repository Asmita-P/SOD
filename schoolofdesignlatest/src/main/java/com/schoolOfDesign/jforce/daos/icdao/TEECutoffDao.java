package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TEECutoffBean;
import com.schoolOfDesign.jforce.daos.BaseDao;


@Component
public class TEECutoffDao extends BaseDao<TEECutoffBean> {
	
	@Override
	protected String getTableName() {
		return "tee_cutoff";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName() + "(tee_id,cutoff_id,active"
				+ ") values(:tee_id,:cutoff_id,:active" 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee_cutoff set " 
				+ " tee_id=:tee_id, "
				+ " cutoff_id=:cutoff_id,"
				+ " active=:active"
				
				+ " where tee_id=:tee_id and cutoff_id=:cutoff_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
