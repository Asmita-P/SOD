package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Ic;
import model.IcCriteria;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.IceBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.controller.ReportController;
import com.schoolOfDesign.jforce.daos.icdao.FacultyDao;
import com.schoolOfDesign.jforce.daos.icdao.IceCriteriaDao;
import com.schoolOfDesign.jforce.daos.icdao.IceDao;

@Service
public class IceService {

	@Autowired
	IceDao iceDao;

	@Autowired
	IceCriteriaDao iceCriteriaDao;

	@Autowired
	FacultyDao facultyDao;
	Logger log = LoggerFactory.getLogger(IceService.class);

	public IceBean createIc(String faculty, String courseId, String isRe,
			String orgId, String assignedFaculty) {

		IceBean daoBean = new IceBean();
		daoBean.setOwner_faculty(faculty);
		daoBean.setCourse_id((courseId));
		daoBean.setIs_reexam(isRe);
		daoBean.setOrg_ice_id(orgId);
		daoBean.setStatus("CREATED");
		daoBean.setActive("Y");
		daoBean.setAssigned_faculty(assignedFaculty);

		iceDao.insertWithIdReturn(daoBean);
		return daoBean;
	}

	public void updateIcWithCriteria(Map<String, String> allRequestParams,
			int criteriaCount) {
		String icId = allRequestParams.get("icId");
		List<IceCriteriaBean> beanList = new ArrayList<IceCriteriaBean>();

		for (int i = 1; i <= criteriaCount; i++) {

			IceCriteriaBean criteriaBean = new IceCriteriaBean();
			criteriaBean.setCriteria_desc(allRequestParams.get("criteriaDesc"
					+ i));
			criteriaBean.setIce_id(icId);
			criteriaBean.setWeightage(allRequestParams.get("weightage" + i));
			criteriaBean.setMapping_desc(allRequestParams.get("skill" + i));
			criteriaBean.setActive("Y");
			beanList.add(criteriaBean);
		}
		String iceName = allRequestParams.get("iceName");
		this.updateIcWithCriteria(icId, beanList, iceName);

	}

	public boolean updateIcWithCriteria(String icId,
			List<IceCriteriaBean> beanList, String iceName) {
		boolean isSuccess = false;

		iceCriteriaDao.getJdbcTemplate().update(
				"delete from  ice_criteria where ice_id =?",
				new Object[] { icId });
		iceCriteriaDao.insertBatch(beanList);
		IceBean bean = iceDao.findOne(Long.valueOf(icId));
		bean.setStatus("CRITERIA-ADDED");
		bean.setIceName(iceName);
		iceDao.update(bean);
		return isSuccess;
	}

	public boolean updateIcStatus(IceBean bean, String status) {
		boolean isSuccess = false;

		bean.setStatus(status);

		iceDao.update(bean);
		return isSuccess;
	}

	public List<Ic> getAllIcesForFaculty(String facultyId) {
		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao
				.getJdbcTemplate()
				.query("select distinct i.id as icid,c.course_name,f.faculty_name as ownerfaculyName, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i, faculty f "
						+ "where i.course_id = c.sap_course_id  and i.owner_faculty = f.sap_id and ( owner_faculty = ? or assigned_faculty = ?) ",
						new Object[] { facultyId, facultyId },
						new BeanPropertyRowMapper(Ic.class));

		Map<String, String> map = new HashMap();
		for (Ic ic : beanList) {
			if (null != ic.getAssignedFacultyId())
				map.put(ic.getAssignedFacultyId(), null);
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

			for (Ic ic : beanList) {
				if (null != ic.getAssignedFacultyId()) {
					ic.setAssignedfacultyName(map.get(ic.getAssignedFacultyId()));
				}
			}
		}

		return beanList;

	}

	public List<Ic> getPendingIces() {

		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao
				.getJdbcTemplate()
				.query("select distinct i.id as icid,c.course_name,f.faculty_name as ownerfaculyName, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i, faculty f "
						+ "where i.course_id = c.sap_course_id  and i.owner_faculty = f.sap_id and i.status not in ('SUBMIT') ",
						new Object[] {}, new BeanPropertyRowMapper(Ic.class));

		Map<String, String> map = new HashMap();
		for (Ic ic : beanList) {

			if (null != ic.getAssignedFacultyId())
				map.put(ic.getAssignedFacultyId(), null);
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

			for (Ic ic : beanList) {
				if (null != ic.getAssignedFacultyId()) {
					ic.setAssignedfacultyName(map.get(ic.getAssignedFacultyId()));
				}

			}
		}

		return beanList;

	}

	public List<Ic> getAllIcesForFacultyAndCourse(String facultyId,
			String courseId) {
		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao
				.getJdbcTemplate()
				.query("(select distinct i.id as icid,iceName as iceName,c.course_name,f.faculty_name as ownerfaculyName, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i, faculty f "
						+ "where i.course_id = c.sap_course_id  and i.owner_faculty = f.sap_id and ( owner_faculty = ? ) and c.sap_course_id = ?)"
						+ " union "
						+ "(select i.id as icid,iceName as iceName,c.course_name,f.faculty_name as ownerfaculyName, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i, faculty f "
						+ " where i.course_id = c.sap_course_id  and i.assigned_faculty = f.sap_id and (  assigned_faculty = ?) and c.sap_course_id = ?)",
						new Object[] { facultyId, courseId, facultyId, courseId },
						new BeanPropertyRowMapper(Ic.class));

		Map<String, String> map = new HashMap();
		for (Ic ic : beanList) {
			ic.setGrade("grade");
			if (null != ic.getAssignedFacultyId())
				map.put(ic.getAssignedFacultyId(), null);
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

			for (Ic ic : beanList) {
				if (null != ic.getAssignedFacultyId()) {
					ic.setAssignedfacultyName(map.get(ic.getAssignedFacultyId()));
				}

			}
		}

		return beanList;

	}

	public List<Ic> getAssignedFaculties(String facultyId) {
		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao
				.getJdbcTemplate()
				.query("select distinct i.id as icid,iceName as iceName,c.course_name,f.faculty_name as ownerfaculyName, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i, faculty f "
						+ "where i.course_id = c.sap_course_id  and i.owner_faculty = f.sap_id and ( assigned_faculty = ?) ",
						new Object[] { facultyId },
						new BeanPropertyRowMapper(Ic.class));

		Map<String, String> map = new HashMap();
		for (Ic ic : beanList) {
			ic.setGrade("grade");
			if (null != ic.getAssignedFacultyId())
				map.put(ic.getAssignedFacultyId(), null);
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

			for (Ic ic : beanList) {
				if (null != ic.getAssignedFacultyId()) {
					ic.setAssignedfacultyName(map.get(ic.getAssignedFacultyId()));
				}

			}
		}

		return beanList;

	}

	public List<Ic> getAllIces(String courseId) {
		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao
				.getJdbcTemplate()
				.query("select distinct i.id as icid,iceName as iceName,c.course_name, i.status as status,"
						+ " is_reexam as isRepeat, i.org_ice_id as orginalCid,"
						+ " i.assigned_faculty as assignedFacultyId "
						+ "from course c , ice i  "
						+ "where i.course_id = c.sap_course_id  and  c.sap_course_id = ? ",
						new Object[] { courseId },
						new BeanPropertyRowMapper(Ic.class));

		Map<String, String> map = new HashMap();
		for (Ic ic : beanList) {
			ic.setGrade("grade");
			ic.setStatus(ic.getStatus() + " Can EDIT");
			if (null != ic.getAssignedFacultyId())
				map.put(ic.getAssignedFacultyId(), null);
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

			for (Ic ic : beanList) {
				if (null != ic.getAssignedFacultyId()) {
					ic.setAssignedfacultyName(map.get(ic.getAssignedFacultyId()));
				}

			}
		}

		return beanList;

	}

	public List<Ic> getAllActiveIces() {
		List<Ic> beanList = new ArrayList<Ic>();

		beanList = iceDao.getJdbcTemplate().query(
				"select distinct i.id as icid,iceName as iceName" + "  "
						+ "from  ice i  where i.active='Y'",

				new Object[] {}, new BeanPropertyRowMapper(Ic.class));

	

		return beanList;

	}

	public IceBean loadIc(String icId) {
		IceBean bean = iceDao.findOne(Long.valueOf(icId));
		return bean;
	}

	public IceBean cloneIc(String icId, String facultyId, String courseId,
			String isRe, String orgId) {
		IceBean bean = iceDao.findOne(Long.valueOf(icId));
		IceBean daoBean = new IceBean();
		daoBean.setOwner_faculty(facultyId);
		daoBean.setCourse_id((courseId));
		daoBean.setIs_reexam(isRe);
		daoBean.setOrg_ice_id(orgId);
		daoBean.setStatus("CREATED");
		iceDao.insertWithIdReturn(daoBean);
		return daoBean;
	}

	public void deleteIc(String icId) {

		IceBean bean = iceDao.findOne(Long.valueOf(icId));
		bean.setStatus("DELETED");
		iceDao.update(bean);

	}

	public void permDeleteIc(String icId) {

		IceBean bean = iceDao.findOne(Long.valueOf(icId));
		if (bean != null) {
			iceCriteriaDao.getJdbcTemplate().update(
					"delete from marks where assignment_id=" + icId,
					new Object[] {});
			iceCriteriaDao.getJdbcTemplate().update(
					"delete from ice_total where ice_id=" + icId,
					new Object[] {});
			iceCriteriaDao.getJdbcTemplate().update(
					"delete from ice_criteria where ice_id=" + icId,
					new Object[] {});
			iceDao.getJdbcTemplate().update(
					"delete from ice where id =" + icId, new Object[] {});
			log.info("Ice Id" + icId + " deleted");
		} else {
			log.info("Ice Id" + icId + " not found");
		}

	}

	public List<IceCriteriaBean> loadIcÇriteria(String icId) {
		List<IceCriteriaBean> icCriteria = iceCriteriaDao.findAllSQL(
				"select * from  ice_criteria where ice_id =? order by id asc ",
				new Object[] { icId });
		return icCriteria;

	}

	public static String returnBlankIfNull(Object obj) {
		if (obj == null)
			return "";
		else
			return obj.toString();
	}

}
