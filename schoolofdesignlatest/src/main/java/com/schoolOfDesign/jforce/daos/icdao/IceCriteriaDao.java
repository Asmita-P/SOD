package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class IceCriteriaDao extends BaseDao<IceCriteriaBean> {

	@Override
	protected String getTableName() {
		return "ice_criteria";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(ice_id, criteria_desc,weightage,mapping_desc,active"
				+ ") values(:ice_id, :criteria_desc,:weightage,:mapping_desc,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update ice_criteria set  " 
				+ " ice_id=:ice_id, "
				+ " criteria_desc=:criteria_desc, " 
				+ " weightage=:weightage, "
				+ " active=:active, " 
				+ " mapping_desc= :mapping_desc"
				
				+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
