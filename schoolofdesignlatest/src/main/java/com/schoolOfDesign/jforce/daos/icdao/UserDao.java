package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.daos.BaseDao;
import com.schoolOfDesign.jforce.utils.PasswordGenerator;

@Component
public class UserDao extends BaseDao<UserBean> {

	@Override
	protected String getTableName() {
		return "user";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName() + "(username,password,active"
				+ ") values(:username,:password ,:active" + ")";
	}

	public int updatePass(UserBean user) {
		String sql = "Update " + getTableName() + " set password = :password "
				+ " where username = :username ";
		return updateSQL(user, sql);
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update user set" + "username=:username,"
				+ "password=:password," + "active=:active"

				+ "   where username=:username";

		return sql;
	}

	public List<Map<String, Object>> retrieveMultipleRowsColumns() {
		String sql = " select  distinct m.sap_id, s.first_name,s.last_name ,c.course_name,i.iceName, ic.criteria_desc ,ic.weightage, ic.id, "
				+ " m.weightage_1,m.weightage_2,m.weightage_3,m.weightage_4 "

				+ " from     schoolofdesign.marks m, schoolofdesign.ice i , schoolofdesign.ice_criteria ic , schoolofdesign.course c, schoolofdesign.student s "
				+ " where "
				+ " i.id = ic.ice_id and m.assignment_id = i.id "
				+ " and c.sap_course_id = i.course_id "
				+ " and s.sap_id = m.sap_id "
				+ " order by s.sap_id,c.course_name,ic.id  asc ";
		return getJdbcTemplate().queryForList(sql);
	}

	@Override
	protected String getUpsertSql() {
		return "Insert into " + getTableName() + "(username,password,active"
				+ ") values(:username,:password ,:active"
				+ ") ON DUPLICATE KEY UPDATE username =:username";
	}

	public String resetPassword(final UserBean user) {
		String newPassword = generateNewPassword(user);
		String sql = "Update " + getTableName()
				+ " set password = :password where username = :username";
		updateSQL(user, sql);

		System.out.println("New Password = " + newPassword);
		return newPassword;
	}

	public String generateNewPassword(final UserBean user) {
		String newPassword = RandomStringUtils.randomAlphanumeric(8);
		user.setPassword(PasswordGenerator.generatePassword(newPassword));
		return newPassword;
	}

}
