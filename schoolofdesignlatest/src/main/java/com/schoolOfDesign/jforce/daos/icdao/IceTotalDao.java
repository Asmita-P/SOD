package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.IceTotalBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class IceTotalDao extends BaseDao<IceTotalBean> {

	@Override
	protected String getTableName() {
		return "ice_total";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(sap_id, ice_total,ice_id,active,weighted_total"
				+ ") values(:sap_id,:ice_total,:ice_id,:active,:weighted_total" + ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update ice_total set "
				
				+ " sap_id=:sap_id, "
				+ " ice_total=:ice_total," 
				+ " ice_id=:ice_id,"
				+ " active=:active,"
				+ "  weighted_total=:weighted_total"
				
				
				+ " where ice_id=:ice_id and sap_id=:sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
