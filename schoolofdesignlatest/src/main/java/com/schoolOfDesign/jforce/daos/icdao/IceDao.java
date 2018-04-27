package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.IceBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class IceDao extends BaseDao<IceBean> {

	@Override
	protected String getTableName() {
		return "ice";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(iceName,course_id,owner_faculty,assigned_faculty,is_reexam,org_ice_id,status,active"
				+ ") values( :iceName,:course_id,:owner_faculty,:assigned_faculty,:is_reexam,:org_ice_id,:status,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update ice set " 
				
				+ " course_id=:course_id,"
				+ " owner_faculty=:owner_faculty, "
				+ " assigned_faculty=:assigned_faculty, "
				+ "  is_reexam = :is_reexam," 
				+ "  org_ice_id =:org_ice_id,"
				+ "  status =:status," 
				+ "  active =:active,iceName=:iceName" 
				
				+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
