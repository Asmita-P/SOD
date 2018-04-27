package com.schoolOfDesign.jforce.daos.icdao;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.BatchDetailsBean;
import com.schoolOfDesign.jforce.daos.BaseDao;
@Component
public class BatchDetailsDao extends BaseDao<BatchDetailsBean> {
	
	@Override
	protected String getTableName() {
		return "batch_details";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into "
				+ getTableName()
				+ "( batch_name,acad_year,acad_month,ice_startDate,ice_endDate,ice_midReviewDate,active,semester"
				+ ") values( :batch_name,:acad_year,:acad_month,:ice_startDate,:ice_endDate,:ice_midReviewDate,:active,:semester "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update batch_details set  " 
				+ " batch_name=:batch_name,"
				+ " acad_year=:acad_year, "
				+ " acad_month=:acad_month, "
				+ "  ice_startDate=:ice_startDate,"
				+ "  ice_endDate =:ice_endDate,"
				+ "  ice_midReviewDate =:ice_midReviewDate,"
				+ "  active =:active,"
				+ "  semester =:semester"
				
				+ " where batch_name=:batch_name";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}
	

}
