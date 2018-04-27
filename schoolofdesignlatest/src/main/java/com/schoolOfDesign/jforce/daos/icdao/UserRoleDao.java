package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.UserRoleBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class UserRoleDao extends BaseDao<UserRoleBean> {

	@Override
	protected String getTableName() {
		return "user_role";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName() + "(username,role_id,active"
				+ ") values(:username,:role_id,:active" + ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update user_role set  " + " username=:username, "
				+ " role_id=:role_id, " + " active=:active "
				+ " where role_id=:role_id and username=:username";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
