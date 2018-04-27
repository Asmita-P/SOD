package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.IceMetadataBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class IceMetadataDao extends BaseDao<IceMetadataBean> {

	@Override
	protected String getTableName() {
		return "ice_metadata";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(cutoff, criteria,active,course_id"
				+ ") values(:cutoff, :criteria,:active,:course_id " 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update course  " 
				+ " cutoff=:cutoff, "
				+ " criteria=:criteria, " 
				+ " active=:active,"
				+ " course_id=:course_id "

				+ " where course_id=:course_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
