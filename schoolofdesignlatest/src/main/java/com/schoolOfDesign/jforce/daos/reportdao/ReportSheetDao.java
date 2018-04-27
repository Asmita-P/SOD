package com.schoolOfDesign.jforce.daos.reportdao;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.schoolOfDesign.jforce.daos.BaseDao;

@Component
public class ReportSheetDao extends BaseDao implements InitializingBean {

	static Logger logger = LoggerFactory.getLogger(ReportSheetDao.class);

	@Autowired
	NamedParameterJdbcTemplate jdbcTemplate;

	public List getQueryResults(String sapId, String session) {

		String sql = " select distinct	m.sap_id as sapId,  	s.first_name as fname , 	s.last_name as lname, c.course_name as courseName,  "
				+ "	i.iceName as assignmentName, i.id as id ,ic.criteria_desc as criteria, ic.weightage as criteria_weightage,  	ic.id as assNo, "
				+ " 	m.weightage_1 as w1,  	m.weightage_2 as w2, m.weightage_3 as w3,  	m.weightage_4 as w4 ,ic.mapping_desc as mapping_desc "
				+ "   ,it.weighted_total as total from  marks m,  	ice i,  ice_criteria ic, course c,  student s , ice_total it  where  it.ice_id = i.id and it.sap_id = s.sap_id and  					    					 	"
				+ "   i.id = ic.ice_id   	and m.assignment_id = i.id   	and c.sap_course_id = i.course_id   	and s.sap_id = m.sap_id  and s.active = 'Y' and m.sap_id ='"
				+ sapId
				+ "' and c.session='"
				+ session
				+ "' order by  	s.sap_id, c.course_name,  ic.id asc ";
		System.out
				.println("check sql---------------------------------------------------"
						+ sql);
		return jdbcTemplate.queryForList(sql, new LinkedHashMap());
	}

	public List getQueryResultsTotalTee() {

		String sql = "select distinct m.sap_id as sapId,      s.first_name as fname "
				+ "                                              ,        s.last_name as lname, "
				+ "                                                    c.course_name as courseName,  "
				+ "                          i.id as assignmentName, i.id as id , "
				+ "                                                    ic.criteria_desc as criteria,  "
				+ "                                  ic.weightage as criteria_weightage,     ic.id as assNo, "
				+ "                                                    m.weightage_1 as w1,    m.weightage_2 as w2,  "
				+ "                          m.weightage_3 as w3,        m.weightage_4 as w4 "
				+ "                     ,it.weighted_total as total,ic.mapping_desc as mapping from  "
				+ "                                                   marks m,        tee i,      tee_criteria ic,        course c,  student s , tee_total it "
				+ "                                                            where  it.tee_id = i.id and it.sap_id = s.sap_id and "
				+ "                                                                     i.id = ic.tee_id        and m.assignment_id = i.id   "
				+ "                                                                           and c.sap_course_id = i.course_id   "
				+ "                                                                                 and s.sap_id = m.sap_id  and s.active='Y'  order by  "
				+ "                                                                                       s.sap_id,  c.course_name,    ic.id asc";
		;
		return jdbcTemplate.queryForList(sql, new HashMap<>());
	}

	public List getQueryResultsForTee(String sapId, String session) {

		String sql = "select distinct m.sap_id as sapId,      s.first_name as fname ,  s.last_name as lname, c.course_name as courseName,  "
				+ "         i.id as assignmentName, i.id as id , ic.criteria_desc as criteria, ic.weightage as criteria_weightage,     ic.id as assNo, "
				+ "         m.weightage_1 as w1,    m.weightage_2 as w2,  m.weightage_3 as w3,  m.weightage_4 as w4 "
				+ "         ,it.weighted_total as total,ic.mapping_desc as mapping from  marks m,  tee i,  tee_criteria ic,  course c, student s , tee_total it "
				+ "          where  it.tee_id = i.id and it.sap_id = s.sap_id and  i.id = ic.tee_id        and m.assignment_id = i.id   "
				+ "         and c.sap_course_id = i.course_id  and s.sap_id ='"
				+ sapId
				+ "' and c.session='"
				+ session
				+ "'   and s.sap_id = m.sap_id  and s.active='Y'  order by s.sap_id,  c.course_name,    ic.id asc";
		;
		return jdbcTemplate.queryForList(sql, new HashMap<>());
	}

	public List getQueryResultsCourse(String sapId, String courseId,
			String session) {

		String sql = " select distinct	m.sap_id as sapId,  	s.first_name as fname"
				+ "   				 ,  	s.last_name as lname,    "
				+ " 				 	c.course_name as courseName,  "
				+ "	i.iceName as assignmentName, i.id as id ,"
				+ "    				 	ic.criteria_desc as criteria,  	   		"
				+ "		  ic.weightage as criteria_weightage,  	ic.id as assNo, "
				+ " 	   				 m.weightage_1 as w1,  	m.weightage_2 as w2,  "
				+ "	m.weightage_3 as w3,  	m.weightage_4 as w4 ,ic.mapping_desc as mapping_desc "
				+ ",it.weighted_total as total from  "
				+ "  				 	marks m,  	ice i,  	ice_criteria ic,  	course c,  	student s , ice_total it  where  it.ice_id = i.id and it.sap_id = s.sap_id and  					    					 	"
				+ "i.id = ic.ice_id   	and m.assignment_id = i.id   	and c.sap_course_id = i.course_id   	and s.sap_id = m.sap_id  and m.sap_id ='"
				+ sapId
				+ "' and c.session='"
				+ session
				+ "' and c.sap_course_id ="
				+ courseId
				+ " order by  	s.sap_id,  	c.course_name,  	ic.id asc ";
		return jdbcTemplate.queryForList(sql, new LinkedHashMap());
	}

	public List getIcaTotalCourseWise(String sapId, String session) {

		String sql = " select distinct	m.sap_id as sapId,  	s.first_name as fname"
				+ "   				 ,  	s.last_name as lname,    "
				+ " 				 	c.course_name as courseName,  "
				+ "	i.iceName as assignmentName, i.id as id ,"
				+ "    				 	ic.criteria_desc as criteria,  	   		"
				+ "		  ic.weightage as criteria_weightage,  	ic.id as assNo, "
				+ " 	   				 m.weightage_1 as w1,  	m.weightage_2 as w2,  "
				+ "	m.weightage_3 as w3,  	m.weightage_4 as w4 "
				+ ",it.weighted_total as total from  "
				+ "  				 	marks m,  	ice i,  	ice_criteria ic,  	course c,  	student s , ice_total it  where  it.ice_id = i.id and it.sap_id = s.sap_id and  					    					 	"
				+ "i.id = ic.ice_id   	and m.assignment_id = i.id   	and c.sap_course_id = i.course_id   	and s.sap_id = m.sap_id  and m.sap_id ='"
				+ sapId
				+ "' and c.session='"
				+ session
				+ "'   order by  	s.sap_id,  	c.course_name,  	ic.id asc ";
		return jdbcTemplate.queryForList(sql, new LinkedHashMap());
	}

	public List getStudents(String currentAcadSession) {

		String sql = " select distinct sap_id as sapId ,first_name as fname,last_name as lname from student where active = 'Y' and current_acad_session='"
				+ currentAcadSession + "'  order by sap_id asc";
		System.out.println("sql -->" + sql);
		return jdbcTemplate.queryForList(sql, new LinkedHashMap());
	}

	public List getQueryResultsTotal() {

		String sql = " select distinct	m.sap_id as sapId,  	s.first_name as fname"
				+ "   				 ,  	s.last_name as lname,    "
				+ " 				 	c.course_name as courseName,  "
				+ "	i.iceName as assignmentName, i.id as id ,"
				+ "    				 	ic.criteria_desc as criteria,  	   		"
				+ "		  ic.weightage as criteria_weightage,  	ic.id as assNo, "
				+ " 	   				 m.weightage_1 as w1,  	m.weightage_2 as w2,  "
				+ "	m.weightage_3 as w3,  	m.weightage_4 as w4 "
				+ ",it.weighted_total as total,ic.mapping_desc as mapping from  "
				+ "  				 	marks m,  	ice i,  	ice_criteria ic,  	course c,  	student s , ice_total it  where  it.ice_id = i.id and it.sap_id = s.sap_id and  					    					 	i.id = ic.ice_id   	and m.assignment_id = i.id   	and c.sap_course_id = i.course_id   	and s.sap_id = m.sap_id   order by  	s.sap_id,  	c.course_name,  	ic.id asc ";
		return jdbcTemplate.queryForList(sql, new HashMap<>());
	}

	public List getMarksPerStudent(String studentId) {
		String sql = "select i.iceName as iceName , t.sap_id as sapId, s.first_name as fname,s.last_name as lname,c.course_name as cname ,t.weighted_total  as weighted_total"
				+ " from ice i, ice_total t, course c,student s where "
				+ "i.id = t.ice_id and i.course_id = c.sap_course_id and s.sap_id = t.sap_id and s.sap_id =:sapId order by t.sap_id,t.ice_id asc";
		Map paramMap = new HashMap<>();
		paramMap.put("sapId", studentId);
		return jdbcTemplate.queryForList(sql, paramMap);

	}

	public List getMinMax(String currentAcadSession) {

		String sql = "select s.student_name as name ,s.sap_id as sapId, c.course_name as cname,(sum(ict.weighted_total)/(count(ic.id)*100)*100) as markcourse "
				+ " from ice_total ict, ice ic, course c, student s "
				+ " where ict.ice_id = ic.id "
				+ " and ic.course_id = c.sap_course_id "
				+ " and ict.sap_id = s.sap_id and s.current_acad_session='"
				+ currentAcadSession
				+ "' "
				+ " group by ict.sap_id, c.course_name"
				+ " order by c.course_name asc,markcourse desc";
		return jdbcTemplate.queryForList(sql, new LinkedHashMap());

	}

	@Override
	protected String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getInsertSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUpdateSql() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String getUpsertSql() {
		// TODO Auto-generated method stub
		return null;
	}

}