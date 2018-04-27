package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.RoleBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class RoleDao extends BaseDao<RoleBean> {

	@Override
	protected String getTableName() {
		return "role";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName() + "(role_name,active"
				+ ") values(:role_name,:active " 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update role set  " 
		+ " role_name=:role_name,"
		+ " active=:active"

		+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
