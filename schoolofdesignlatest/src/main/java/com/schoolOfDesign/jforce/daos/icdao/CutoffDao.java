package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.CutoffBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class CutoffDao extends BaseDao<CutoffBean> {

	@Override
	protected String getTableName() {
		return "cutoff";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(cutoff, criteria,active"
				+ ") values(:cutoff, :criteria,:active " 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update cutoff set  " 
				+ " cutoff=:cutoff, "
				+ " criteria=:criteria, " 
				+ " active=:active"
				

				+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

}
