package com.schoolOfDesign.jforce.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserDetailsBean;
import com.schoolOfDesign.jforce.daos.icdao.UserDao;
import com.schoolOfDesign.jforce.daos.icdao.UserDetailsDao;
import com.schoolOfDesign.jforce.daos.icdao.UserRoleDao;

@Service
public class UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao userRoleDao;

	@Autowired
	UserDetailsDao userDetailsDao;

	public UserBean getUserBean(String logonId) {
		UserBean bean = null;
		List<UserBean> userBeanList = userDao.findAllSQL(
				"select * from user where username =? and active =?",
				new Object[] { logonId, "Y" });
		if (!userBeanList.isEmpty()) {
			bean = userBeanList.get(0);
			List<String> roles = userRoleDao
					.getJdbcTemplate()
					.query("select distinct role_name from user_role ur, role r "
							+ " where ur.role_id = r.id and ur.username =? and ur.active =?",
							new Object[] { logonId, "Y" },
							new SingleColumnRowMapper(String.class));

			bean.setRoles(roles);
			List<UserDetailsBean> userDetails = userDetailsDao.findAllSQL(
					"select * from user_details where username=?",
					new Object[] { logonId });
			if (!userDetails.isEmpty())
				bean.setUserDetails(userDetails.get(0));
		}

		return bean;
	}

	public int getUpdatedPass(UserBean uBean) {
		return userDao.updatePass(uBean);
	}

	public void insertOrUpdateUsers(List<UserBean> usrList) {
		userDao.upsertBatch(usrList);
	}

}
