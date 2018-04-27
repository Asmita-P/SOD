package com.schoolOfDesign.jforce.daos.icdao;

import com.schoolOfDesign.jforce.beans.icbeans.IceCutoffBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

public class IceCutoffDao extends BaseDao<IceCutoffBean> {
	
	@Override
	protected String getTableName() {
		return "ice_cutoff";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName() + "(ice_id,cutoff_id,active"
				+ ") values(:ice_id,:cutoff_id,:active" 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update ice_cutoff set " 
				+ " ice_id=:ice_id, "
				+ " cutoff_id=:cutoff_id,"
				+ " active=:active"
				
				+ " where ice_id=:ice_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
