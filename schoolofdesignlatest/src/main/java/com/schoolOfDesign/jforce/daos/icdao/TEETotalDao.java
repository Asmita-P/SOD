package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TEEMarksBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEETotalBean;
import com.schoolOfDesign.jforce.daos.BaseDao;


@Component
public class TEETotalDao extends BaseDao<TEETotalBean> {
	
	@Override
	protected String getTableName() {
		return "tee_total";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(sap_id,tee_total,tee_id,active,weighted_total"
				+ ") values(:sap_id,:tee_total,:tee_id,:active,:weighted_total" 
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee_total set "
				
				+ " sap_id=:sap_id, "
				+ " tee_total=:tee_total," 
				+ " tee_id=:tee_id,"
				+ " active=:active,"
				+ " weighted_total=:weighted_total"
				
				
				+ " where tee_id=:tee_id and sap_id=:sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}
	
	public void deleteSqlBatch(List<TEETotalBean> marksList) {
		int result[] = deleteSQLBatch(marksList,
				"delete from tee_total where sap_id = :sap_id and tee_id = :tee_id");
	}

	public void insertTEEMarkListBatch(List<TEETotalBean> marksList) {
		int result[] = insertBatch(marksList);
	}

}
