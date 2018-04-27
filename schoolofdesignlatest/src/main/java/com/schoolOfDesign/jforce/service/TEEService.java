package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.IceTotalBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEECriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEEMarksBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEETotalBean;
import com.schoolOfDesign.jforce.daos.icdao.FacultyDao;
import com.schoolOfDesign.jforce.daos.icdao.MarksDao;
import com.schoolOfDesign.jforce.daos.icdao.TEECriteriaDao;
import com.schoolOfDesign.jforce.daos.icdao.TEECutoffDao;
import com.schoolOfDesign.jforce.daos.icdao.TEEDao;
import com.schoolOfDesign.jforce.daos.icdao.TEEMarksDao;
import com.schoolOfDesign.jforce.daos.icdao.TEETotalDao;

import model.Marks;

@Service
public class TEEService {
	@Autowired
	TEECriteriaDao teeCriteriaDao;

	@Autowired
	TEECutoffDao teeCutOffDao;

	@Autowired
	TEEDao teeDao;

	@Autowired
	TEETotalDao teeTotalDao;

	@Autowired
	TEEMarksDao teeMarksDao;

	@Autowired
	FacultyDao facultyDao;

	@Autowired
	MarksDao mDao;

	public List<TEEBean> getAllTeeForCourse(String courseId) {
		return teeDao.getAllTeeForCourse(courseId);
	}
	
	public List<TEEBean> getAllInternalTeeForCourse(String courseId) {
		return teeDao.getAllInternalTeeForCourse(courseId);
	}
	
	public List<TEEBean> getAllExternalTeeForCourse(String courseId) {
		return teeDao.getAllExternalTeeForCourse(courseId);
	}

	public TEEBean createTEERecord(String faculty, String courseId,
			String isRe, String orgId, String assignedFaculty, String teeType,
			String tee_percent) {

		TEEBean teeBean = new TEEBean();
		teeBean.setOwner_faculty(faculty);
		teeBean.setCourse_id((courseId));
		teeBean.setIs_reexam(isRe);
		teeBean.setStatus("CREATED");
		teeBean.setActive("Y");
		teeBean.setAssigned_faculty(assignedFaculty);
		teeBean.setOrg_tee_id(orgId);
		teeBean.setTeeType(teeType);
		teeBean.setTee_percent(tee_percent);
		teeDao.insertWithIdReturn(teeBean);
		return teeBean;
	}

	public TEEBean loadTEE(String teeId) {
		return teeDao.loadTEE(teeId);
	}

	public List<TEECriteriaBean> getCriteriaListBasedOnId(String teeId) {
		return teeCriteriaDao.getCriteriaListBasedOnId(teeId);
	}

	public List<TEEBean> getAllTEERecordsByCourse(String courseId,
			boolean isEdit) {
		List<TEEBean> teeList = teeDao.listOfTEERecordsByCourse(courseId);
		Map<String, String> map = new HashMap();
		if (teeList != null) {
			for (TEEBean b : teeList) {
				if (null != b.getAssigned_faculty())
					map.put(b.getAssigned_faculty(), null);

				if (null != b.getOwner_faculty())
					map.put(b.getOwner_faculty(), null);
				b.setIsEdit(isEdit + "");
			}
		}

		if (map != null && !map.isEmpty()) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("ids", map.keySet());

			List<Map<String, Object>> inList = facultyDao
					.getNamedParameterJdbcTemplate()
					.queryForList(
							"select sap_id, faculty_name from faculty where sap_id in (:ids) ",
							parameters);
			map.clear();
			for (Map<String, Object> m : inList) {
				map.put(returnBlankIfNull(m.get("sap_id")),
						returnBlankIfNull(m.get("faculty_name")));
			}

			for (TEEBean tee : teeList) {
				if (null != tee.getAssigned_faculty()) {
					tee.setAssigned_faculty(map.get(tee.getAssigned_faculty()));
				}
				if (null != tee.getOwner_faculty())
					tee.setOwner_faculty(map.get(tee.getOwner_faculty()));

			}
		}

		return teeList;
	}

	public void updateTEEWithCriteria(Map<String, String> allRequestParams,
			int criteriaCount) {
		String teeId = allRequestParams.get("teeId");
		List<TEECriteriaBean> beanList = new ArrayList<TEECriteriaBean>();

		for (int i = 1; i <= criteriaCount; i++) {

			TEECriteriaBean teeCriteriaBean = new TEECriteriaBean();
			teeCriteriaBean.setCriteria_desc(allRequestParams
					.get("criteriaDesc" + i));
			teeCriteriaBean.setTee_id(teeId);
			teeCriteriaBean.setWeightage(allRequestParams.get("weightage" + i));
			teeCriteriaBean.setMapping_desc(allRequestParams.get("skill" + i));
			teeCriteriaBean.setActive("Y");
			beanList.add(teeCriteriaBean);
		}

		updateTEECriteria(teeId, beanList);

	}

	public boolean updateTEECriteria(String teeId,
			List<TEECriteriaBean> beanList) {
		boolean isSuccess = false;
		teeCriteriaDao.getJdbcTemplate().update(
				"delete from  tee_criteria where tee_id =?",
				new Object[] { teeId });
		teeCriteriaDao.insertBatch(beanList);
		TEEBean bean = teeDao.findOne(Long.valueOf(teeId));
		bean.setStatus("CRITERIAADDED");
		teeDao.update(bean);
		return isSuccess;
	}

	public List<TEEBean> getAllTEERecordsAssignedToICE() {
		return teeDao.getAllTEERecordsAssignedToICE();

	}

	public List<TEEBean> getAllIcesForFacultyAndCourse(String facultyId,
			String courseId) {
		List<TEEBean> beanList = new ArrayList<TEEBean>();

		beanList = teeDao.listOfTEERecordsAssignedToFacultyAndCourse(facultyId,
				courseId);

		Map<String, String> map = new HashMap();
		for (TEEBean tee : beanList) {

			if (null != tee.getAssigned_faculty())
				map.put(tee.getAssigned_faculty(), null);

			if (null != tee.getOwner_faculty())
				map.put(tee.getOwner_faculty(), null);
		}

		if (map != null && !map.isEmpty()) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("ids", map.keySet());

			List<Map<String, Object>> inList = facultyDao
					.getNamedParameterJdbcTemplate()
					.queryForList(
							"select sap_id, faculty_name from faculty where sap_id in (:ids) ",
							parameters);
			map.clear();
			for (Map<String, Object> m : inList) {
				map.put(returnBlankIfNull(m.get("sap_id")),
						returnBlankIfNull(m.get("faculty_name")));
			}

			for (TEEBean tee : beanList) {
				if (null != tee.getAssigned_faculty()) {
					tee.setAssigned_faculty(map.get(tee.getAssigned_faculty()));
				}
				if (null != tee.getOwner_faculty())
					tee.setOwner_faculty(map.get(tee.getOwner_faculty()));

			}
		}

		return beanList;

	}

	public List<Marks> populateStudentMarksIfAnyTEE(List<Marks> input,
			String icId) {
		List<Marks> mrkList = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		for (Marks m : input) {
			ids.add(m.getSapId());
		}

		String sql = "select s.student_name as name,m.sap_id  as sapId ,m.criteria_1_marks,m.criteria_2_marks,m.criteria_3_marks,m.criteria_4_marks"
				+ ",m.criteria_5_marks,m.criteria_6_marks,m.criteria_7_marks,m.criteria_8_marks,m.criteria_9_marks,m.criteria_10_marks,"
				+ "m.weightage_1,m.weightage_2,m.weightage_3,m.weightage_4,m.weightage_5,m.weightage_6,m.weightage_7,m.weightage_8,"
				+ "m.weightage_9,m.weightage_10 from marks m,student s  where m.assignment_id = :icId and m.sap_id =s.sap_id and m.sap_id in (:ids)";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		parameters.addValue("icId", icId);
		mrkList = mDao.getNamedParameterJdbcTemplate().query(sql, parameters,
				new BeanPropertyRowMapper(Marks.class));
		List<Marks> newList = new ArrayList<>();

		for (Marks m : mrkList) {
			List<TEETotalBean> teeList = teeTotalDao.getJdbcTemplate().query(
					"select * from tee_total where tee_id =? and sap_id=?",
					new Object[] { icId, m.getSapId() },
					new BeanPropertyRowMapper(IceTotalBean.class));
			if (!teeList.isEmpty()) {

				m.setCriteriaTotal(teeList.get(0).getTee_total());
				m.setWeightedTotal(teeList.get(0).getWeighted_total());
			}

			newList.add(m);
		}
		for (Marks m : input) {
			if (!newList.contains(m))
				newList.add(m);
		}

		return newList;
	}

	public List<TEEBean> getAssignedFacultiesForTEE(String facultyId) {
		List<TEEBean> beanList = new ArrayList<TEEBean>();

		beanList = teeDao.listOfTEERecordsAssignedToFaculty(facultyId);

		Map<String, String> map = new HashMap();
		for (TEEBean tee : beanList) {
			/* tee.setGrade("grade"); */
			if (null != tee.getAssigned_faculty())
				map.put(tee.getAssigned_faculty(), null);

			if (null != tee.getOwner_faculty())
				map.put(tee.getOwner_faculty(), null);
		}

		if (map != null && !map.isEmpty()) {
			MapSqlParameterSource parameters = new MapSqlParameterSource();
			parameters.addValue("ids", map.keySet());

			List<Map<String, Object>> inList = facultyDao
					.getNamedParameterJdbcTemplate()
					.queryForList(
							"select sap_id, faculty_name from faculty where sap_id in (:ids) ",
							parameters);
			map.clear();
			for (Map<String, Object> m : inList) {
				map.put(returnBlankIfNull(m.get("sap_id")),
						returnBlankIfNull(m.get("faculty_name")));
			}

			for (TEEBean tee : beanList) {
				if (null != tee.getAssigned_faculty()) {
					tee.setAssigned_faculty(map.get(tee.getAssigned_faculty()));
				}
				if (null != tee.getOwner_faculty())
					tee.setOwner_faculty(map.get(tee.getOwner_faculty()));

			}
		}

		return beanList;

	}

	public void fillMarksAndTEETotal(Map<String, String> allRequestParams,
			String status) {

		String teeId = allRequestParams.get("teeId");

		TEEBean teeBean = teeDao.loadTEE(teeId);

		Set<String> keys = allRequestParams.keySet();
		List<TEEMarksBean> teeMarksList = new ArrayList<>();
		List<TEETotalBean> teeTotalList = new ArrayList<>();
		for (String key : keys) {
			if (key.startsWith("criteria1")) {
				String sapId = StringUtils.substring(key, 9);
				String didChange = allRequestParams.get("didChange" + sapId);
				System.out.println("didChange" + sapId + didChange);
				if ("true".equals(didChange)) {
					TEEMarksBean marks = new TEEMarksBean();
					marks.setSap_id(sapId);
					marks.setCriteria_1_marks(allRequestParams.get("criteria1"
							+ sapId));
					marks.setCriteria_2_marks(allRequestParams.get("criteria2"
							+ sapId));
					marks.setCriteria_3_marks(allRequestParams.get("criteria3"
							+ sapId));
					marks.setCriteria_4_marks(allRequestParams.get("criteria4"
							+ sapId));
					marks.setCriteria_5_marks(allRequestParams.get("criteria5"
							+ sapId));
					marks.setCriteria_6_marks(allRequestParams.get("criteria6"
							+ sapId));
					marks.setCriteria_7_marks(allRequestParams.get("criteria7"
							+ sapId));
					marks.setCriteria_8_marks(allRequestParams.get("criteria8"
							+ sapId));
					marks.setCriteria_9_marks(allRequestParams.get("criteria9"
							+ sapId));
					marks.setCriteria_10_marks(allRequestParams
							.get("criteria10" + sapId));

					marks.setWeightage_1(allRequestParams
							.get("output1" + sapId));
					marks.setWeightage_2(allRequestParams
							.get("output2" + sapId));
					marks.setWeightage_3(allRequestParams
							.get("output3" + sapId));
					marks.setWeightage_4(allRequestParams
							.get("output4" + sapId));
					marks.setWeightage_5(allRequestParams
							.get("output5" + sapId));
					marks.setWeightage_6(allRequestParams
							.get("output6" + sapId));
					marks.setWeightage_7(allRequestParams
							.get("output7" + sapId));
					marks.setWeightage_8(allRequestParams
							.get("output8" + sapId));
					marks.setWeightage_9(allRequestParams
							.get("output9" + sapId));
					marks.setWeightage_10(allRequestParams.get("output10"
							+ sapId));
					marks.setAssignment_id(teeBean.getId() + "");
					marks.setActive("Y");

					TEETotalBean tb = new TEETotalBean();
					tb.setActive("Y");
					tb.setTee_total(allRequestParams.get("total" + sapId));
					tb.setWeighted_total(allRequestParams.get("weighted"
							+ sapId));
					tb.setSap_id(sapId);
					tb.setTee_id(teeId);
					teeMarksList.add(marks);
					teeTotalList.add(tb);
				}
			}
		}

		teeBean.setStatus(status);

		teeMarksDao.deleteSqlBatch(teeMarksList);

		teeMarksDao.insertBatch(teeMarksList);

		teeTotalDao.deleteSqlBatch(teeTotalList);

		teeTotalDao.insertBatch(teeTotalList);

		teeDao.updateTEEStatus(teeBean);

	}

	public static String returnBlankIfNull(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

}
