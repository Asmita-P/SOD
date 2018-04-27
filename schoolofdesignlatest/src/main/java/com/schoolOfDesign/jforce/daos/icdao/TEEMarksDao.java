package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.MarksBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEMarksBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class TEEMarksDao extends BaseDao<TEEMarksBean> {

	protected String getTableName() {
		return "tee_marks";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "(criteria_1_marks,criteria_2_marks,criteria_3_marks,criteria_4_marks,criteria_5_marks,criteria_6_marks,criteria_7_marks,criteria_8_marks,criteria_9_marks,criteria_10_marks,sap_id,assignment_id,weightage_1,weightage_2,weightage_3,weightage_4,weightage_5,weightage_6,weightage_7,weightage_8,weightage_9,weightage_10,active"
				+ ") values"
				+ "(:criteria_1_marks,:criteria_2_marks,:criteria_3_marks,:criteria_4_marks,:criteria_5_marks,:criteria_6_marks,:criteria_7_marks,:criteria_8_marks,:criteria_9_marks,:criteria_10_marks,:sap_id,:assignment_id,:weightage_1,:weightage_2,:weightage_3,:weightage_4,:weightage_5,:weightage_6,:weightage_7,:weightage_8,:weightage_9,:weightage_10,:active "
				+ ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee_marks set" + " criteria_1_marks=:criteria_1_marks,"
				+ " criteria_2_marks=:criteria_2_marks," + " criteria_3_marks=:criteria_3_marks,"
				+ " criteria_4_marks=:criteria_4_marks," + " criteria_5_marks=:criteria_5_marks,"
				+ " criteria_6_marks=:criteria_6_marks," + " criteria_7_marks=:criteria_7_marks,"
				+ " criteria_8_marks=:criteria_8_marks," + " criteria_9_marks=:criteria_9_marks,"
				+ " criteria_10_marks=:criteria_10_marks," + " sap_id=:sap_id," + " active=:active,"
				+ " assignment_id=:assignment_id," + "weightage_1 =:weightage_1," + "weightage_2 =:weightage_2,"
				+ "weightage_3 =:weightage_3," + "weightage_4 =:weightage_4," + "weightage_5 =:weightage_5,"
				+ "weightage_6 =:weightage_6," + "weightage_7 =:weightage_7," + "weightage_8 =:weightage_8,"
				+ "weightage_9 =:weightage_9," + "weightage_10 =:weightage_10"

				+ " where assignment_id=:assignment_id and sap_id=:sap_id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}

	public List<TEEMarksBean> findAllActive() {
		final String sql = "SELECT * FROM " + getTableName() + " where active = 'Y' ";
		return findAllSQL(sql, new Object[] {});
	}

	public void deleteSqlBatch(List<TEEMarksBean> marksList) {
		int result[] = deleteSQLBatch(marksList,
				"delete from tee_marks where sap_id = :sap_id and assignment_id = :assignment_id");
	}

	public void insertTEEMarkListBatch(List<TEEMarksBean> marksList) {
		int result[] = insertBatch(marksList);
	}

}
