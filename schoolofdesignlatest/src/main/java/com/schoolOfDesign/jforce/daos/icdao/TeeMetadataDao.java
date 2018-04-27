package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TeeMetadataBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class TeeMetadataDao extends BaseDao<TeeMetadataBean> {

	@Override
	protected String getTableName() {
		return "tee_metadata";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(course_id,ice_percent,written_percent,active"
				+ ") values(:course_id,:ice_percent,:written_percent,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee_metadata set  "
				+ " course_id=:course_id, "
				+ " ice_percent=:ice_percent, "
				+ " written_percent=:written_percent, "
				
				+ " active=:active "
				
				+ " where course_id=:course_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
