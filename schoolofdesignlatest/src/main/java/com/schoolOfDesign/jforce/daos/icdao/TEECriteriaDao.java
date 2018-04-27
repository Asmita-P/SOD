package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class TEECriteriaDao extends BaseDao<TEECriteriaBean> {
	
	@Override
	protected String getTableName() {
		return "tee_criteria";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "(tee_id,criteria_desc,weightage,mapping_desc,active"
				+ ") values(:tee_id,:criteria_desc,:weightage,:mapping_desc,:active"
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee_criteria set  " 
				+ " tee_id=:tee_id, "
				+ " criteria_desc=:criteria_desc, " 
				+ " weightage=:weightage, "
				+ " active=:active, " 
				+ " mapping_desc=:mapping_desc"
				
				+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}
	
	public List<TEECriteriaBean> getCriteriaListBasedOnId(String teeId){
		return findAllSQL("select * from "+getTableName()+" where tee_id = ? ",new Object[]{teeId});
	}

}
