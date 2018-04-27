package com.schoolOfDesign.jforce.daos.icdao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class TEEDao extends BaseDao<TEEBean> {
	@Override
	protected String getTableName() {
		return "tee";
	}

	@Override
	protected String getInsertSql() {
		return "Insert into " + getTableName()
				+ "( course_id,owner_faculty,assigned_faculty,is_reexam,org_tee_id,status,active,teeType,tee_percent"
				+ ") values( :course_id,:owner_faculty,:assigned_faculty,:is_reexam,:org_tee_id,:status,:active,:teeType,:tee_percent" + ")";
	}

	@Override
	protected String getUpdateSql() {

		String sql = "update tee set "

				+ " course_id=:course_id," + " owner_faculty=:owner_faculty, " + " assigned_faculty=:assigned_faculty, "
				+ " is_reexam =:is_reexam," + " org_tee_id =:org_tee_id," + " status =:status," + " active =:active, "
				+ " teeType=:teeType," + "tee_percent=:tee_percent "
				+ " where id=:id";
		return sql;
	}

	@Override
	protected String getUpsertSql() {
		return null;
	}
	
	public List<TEEBean> listOfTEERecordsAssignedToFaculty(String faculty){
		return findAllSQL("select t.*,f.faculty_name as owner_faculty from course c , tee t, faculty f where t.course_id = c.sap_course_id  and t.owner_faculty = f.sap_id and ( assigned_faculty = ?)"
				,new Object[]{faculty});
	}
	
	public List<TEEBean> listOfTEERecordsAssignedToFacultyAndCourse(String facultyId,String courseId){
		return findAllSQL("(select t.* "
				+ " from course c , tee t, faculty f "
				+ " where t.course_id = c.sap_course_id  and t.owner_faculty = f.sap_id and ( owner_faculty = ?) and c.sap_course_id = ?) "
				+ " union "
				+ " (select t.* "
				+ " from course c , tee t, faculty f "
				+ " where t.course_id = c.sap_course_id  and t.assigned_faculty = f.sap_id and ( assigned_faculty = ?) and c.sap_course_id = ?)"
				,new Object[]{facultyId, courseId,
						facultyId, courseId });
	}

	public List<TEEBean> getAllTEERecordsAssignedToICE() {
		return findAllSQL("select * from " + getTableName(), null);
	}
	
	public List<TEEBean> getAllTeeForCourse(String courseId) {
		return findAllSQL("select * from " + getTableName() + " where course_id = ? and active='Y'", new Object[]{courseId});
	}
	
	public List<TEEBean> getAllInternalTeeForCourse(String courseId) {
		return findAllSQL("select * from " + getTableName() + " where course_id = ? and teeType='Internal' and active='Y' ", new Object[]{courseId});
	}
	
	public List<TEEBean> getAllExternalTeeForCourse(String courseId) {
		return findAllSQL("select * from " + getTableName() + " where course_id = ? and teeType='External' and active='Y' ", new Object[]{courseId});
	}

	public TEEBean loadTEE(String teeId) {
		return findOne(Long.valueOf(teeId));
	}

	public void updateTEEStatus(TEEBean tee) {
		executeSQL(tee, getUpdateSql());
	}
	
	public List<TEEBean> listOfTEERecordsByCourse(String courseId){
		return findAllSQL("select * from " + getTableName()+" where course_id = ? and  active='Y' ", new Object[]{courseId});
	}
 
}
