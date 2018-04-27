package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.UserDetailsBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class UserDetailsDao extends BaseDao<UserDetailsBean> {

	@Override
	protected String getTableName() {
		return "user_details";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(username,first_name,last_name,email,phone_no"
				+ ") values(:username,:first_name,:last_name,:email,:phone_no "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update user_details set  " + " username=:username,"
				+ " first_name=:first_name, " + " last_name=:last_name,"
				+ " email=:email," + " phone_no=:phone_no "

				+ " where username =:username";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(username,first_name,last_name,email,phone_no"
				+ ") values(:username,:first_name,:last_name,:email,:phone_no "
				+ ") ON DUPLICATE KEY UPDATE username =:username,first_name=:first_name,last_name=:last_name,email=:email,phone_no=:phone_no";
	}

}
