package com.schoolOfDesign.jforce.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Service;

import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceCriteriaBean;
import com.schoolOfDesign.jforce.beans.icbeans.IceTotalBean;
import com.schoolOfDesign.jforce.beans.icbeans.MarksBean;
import com.schoolOfDesign.jforce.beans.icbeans.TEETotalBean;
import com.schoolOfDesign.jforce.controller.MarkController;
import com.schoolOfDesign.jforce.daos.icdao.IceTotalDao;
import com.schoolOfDesign.jforce.daos.icdao.MarksDao;
import com.schoolOfDesign.jforce.daos.icdao.StudentCourseFacultyDao;
import com.schoolOfDesign.jforce.daos.icdao.StudentDao;
import com.schoolOfDesign.jforce.daos.icdao.TEETotalDao;

import model.ConsoleIcScore;
import model.ConsoleIce;
import model.ICETEEConsolidated;
import model.Marks;

@Service
public class MarksService {

	@Autowired
	MarksDao mDao;

	@Autowired
	StudentCourseFacultyDao scfDao;

	@Autowired
	StudentDao studentDao;

	@Autowired
	IceTotalDao iceTotalDao;

	@Autowired
	TEETotalDao teeTotalDao;

	@Autowired
	private IceService iceService;

	@Autowired
	private CourseService courseService;

	Logger log = LoggerFactory.getLogger(MarksService.class);

	public List<IceCriteriaBean> listOfCriteriaFromCourseId(String courseId) {
		return null;
	}

	public List<Marks> getStudentsToGrade(String courseId, String facultyId) {

		List<Marks> mrkList = mDao
				.getJdbcTemplate()
				.query("select distinct scf.student_sap_id as sapId,sr.rollNo,s.student_name as name from student_course_faculty scf, student s, sap_roll sr "
						+ "where scf.student_sap_id = s.sap_id "
						+ " and scf.faculty_sap_id = ? and s.sap_id = sr.sap_id and  scf.course_id=? and s.active='Y'  order by scf.student_sap_id asc",
						new Object[] { facultyId, courseId },
						new BeanPropertyRowMapper(Marks.class));

		return mrkList;

	}

	public List<Marks> getAllStudentsToCourse(String courseId) {

		List<Marks> mrkList = mDao
				.getJdbcTemplate()
				.query("select distinct scf.student_sap_id as sapId,sr.rollNo as rollNo,s.student_name as name from student_course_faculty scf, student s, sap_roll sr "
						+ "where scf.student_sap_id = s.sap_id and s.sap_id = sr.sap_id "
						+ "  and scf.course_id=? and s.active='Y' order by scf.student_sap_id asc",
						new Object[] { courseId },
						new BeanPropertyRowMapper(Marks.class));

		return mrkList;

	}

	public List<Marks> getStudentsToGradeTee(String courseId, String facultyId) {

		List<Marks> mrkList = mDao
				.getJdbcTemplate()
				.query("select distinct scf.student_sap_id as sapId,s.student_name as name from student_course_faculty scf, student s "
						+ "where scf.student_sap_id = s.sap_id "
						+ " and scf.faculty_sap_id = ? and scf.course_id=? and s.active='Y' order by scf.student_sap_id asc",
						new Object[] { facultyId, courseId },
						new BeanPropertyRowMapper(Marks.class));

		return mrkList;

	}

	public List<ICETEEConsolidated> getAllRecordsForDashboard() {

		List<ICETEEConsolidated> mrkList = mDao
				.getJdbcTemplate()
				.query("select ss.first_name as firstName,stt.sap_id as sapid,stt.tee_total as teeTotal,sit.ice_total as iceTotal from "
						+ "schoolofdesign.tee_total stt,schoolofdesign.ice_total sit,schoolofdesign.student ss "
						+ " where stt.sap_id = sit.sap_id and ss.sap_id = stt.sap_id and  ss.active='Y' order by stt.sap_id asc",
						new Object[] {},
						new BeanPropertyRowMapper(ICETEEConsolidated.class));

		return mrkList;

	}

	public List<MarksBean> getTotalICA(String courseId) {
		return mDao
				.findAllSQL(
						"select ss.sap_id ,sr.rollNo,ss.student_name,sum(sit.ice_total)/count(*) as iceTotal, ((sum(sit.weighted_total)/count(*))*c.ica_tee_split)/100 as iceWeightedTotal ,count(*)"
								+ "	from ice_total sit,student ss, course c, ice i ,sap_roll sr"
								+ "	 where ss.sap_id = sit.sap_id and ss.sap_id = sr.sap_id "
								+ "	 and sit.ice_id = i.id and i.course_id = c.sap_course_id "
								+ "	 and c.sap_course_id = ? and  ss.active='Y' "
								+ "	 group by ss.sap_id "
								+ "	  order by ss.sap_id asc ",
						new Object[] { courseId });
	}

	@SuppressWarnings("unchecked")
	public List<ICETEEConsolidated> getAllRecordsForDashboardBasedOnCourseId(
			Map<String, String> mapOfSapIdAndCourseId, String courseId) {
		List<ICETEEConsolidated> courseWiseICETEERecords = new ArrayList<ICETEEConsolidated>();
		List<ICETEEConsolidated> totalIceRecords = mDao
				.getJdbcTemplate()
				.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.ice_total)/count(*) as iceTotal, sum(sit.weighted_total)/count(*) as iceWeightedTotal ,count(*)"
						+ "	from ice_total sit,student ss, course c, ice i "
						+ "	 where ss.sap_id = sit.sap_id "
						+ "	 and sit.ice_id = i.id and i.course_id = c.sap_course_id "
						+ "	 and c.sap_course_id = ? and  ss.active='Y' "
						+ "	 group by ss.sap_id "
						+ "	  order by ss.sap_id asc ",
						new Object[] { courseId },
						new BeanPropertyRowMapper(ICETEEConsolidated.class));

		List externalLst = mDao
				.getJdbcTemplate()
				.queryForList(
						"select * from tee t where t.course_id =?  and t.teeType ='External' ",
						new Object[] { courseId });

		if (externalLst.isEmpty()) {
			List<ICETEEConsolidated> totalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal, sum(sit.weighted_total)/count(*) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));

			Map<String, ICETEEConsolidated> sMap = new HashMap<String, ICETEEConsolidated>();

			for (ICETEEConsolidated cc : totalTeeRecords) {
				sMap.put(cc.getSapId(), cc);
			}

			log.info("S Map" + sMap);

			for (ICETEEConsolidated consolidated : totalIceRecords) {

				consolidated.setCourseId(courseId);
				ICETEEConsolidated tt = sMap.get(consolidated.getSapId());
				log.info("TT" + tt + " for sap Id" + consolidated.getSapId());
				if (tt != null) {
					consolidated.setTeeTotal(tt.getTeeTotal());
					consolidated.setTeeWeightedTotal(tt.getTeeWeightedTotal());

				}

				courseWiseICETEERecords.add(consolidated);

			}
		} else {
			log.info("Inside else block implies external is present");

			Map<String, ICETEEConsolidated> sMap = new HashMap<String, ICETEEConsolidated>();

			List<ICETEEConsolidated> internalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal,(sum(sit.weighted_total)/count(*)) * (i.tee_percent/100) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' and i.teeType ='Internal' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));

			List<ICETEEConsolidated> externalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal,(sum(sit.weighted_total)/count(*)) * (i.tee_percent/100) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' and i.teeType ='External' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));

			for (ICETEEConsolidated cc : internalTeeRecords) {
				sMap.put(cc.getSapId(), cc);
			}

			for (ICETEEConsolidated cc : externalTeeRecords) {

				if (!sMap.containsKey(cc.getSapId())) {
					sMap.put(cc.getSapId(), cc);
				} else {
					ICETEEConsolidated tt = sMap.get(cc.getSapId());
					tt.setCourseId(courseId);
					tt.setTeeTotal(""
							+ (Double.valueOf(tt.getTeeTotal()) + Double
									.valueOf(cc.getTeeTotal())));
					tt.setTeeWeightedTotal(""
							+ (Double.valueOf(tt.getTeeWeightedTotal()) + Double
									.valueOf(cc.getTeeWeightedTotal())));
					sMap.put(cc.getSapId(), tt);
				}

			}

			for (ICETEEConsolidated consolidated : totalIceRecords) {

				consolidated.setCourseId(courseId);
				ICETEEConsolidated tt = sMap.get(consolidated.getSapId());
				log.info("TT" + tt + " for sap Id" + consolidated.getSapId());
				if (tt != null) {
					consolidated.setTeeTotal(tt.getTeeTotal());
					consolidated.setTeeWeightedTotal(tt.getTeeWeightedTotal());

				}

				courseWiseICETEERecords.add(consolidated);

			}

		}
		return courseWiseICETEERecords;

	}

	public List<Map<String, String>> getInternalExternalTee(
			Map<String, String> mapOfSapIdAndCourseId, String courseId) {

		List<Map<String, String>> lstMap = new ArrayList();

		List externalLst = mDao
				.getJdbcTemplate()
				.queryForList(
						"select * from tee t where t.course_id =?  and t.teeType ='External' ",
						new Object[] { courseId });

		if (externalLst.isEmpty()) {
			List<ICETEEConsolidated> totalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal, sum(sit.weighted_total)/count(*) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));

			for (ICETEEConsolidated tt : totalTeeRecords) {
				Map<String, String> mp = new HashMap();
				mp.put("sapId", tt.getSapId());
				mp.put("studentName", tt.getFirstName());
				mp.put("teeInternalTotal", tt.getTeeTotal() + "");
				mp.put("teeInternalWtTotal", tt.getTeeWeightedTotal() + "");
				lstMap.add(mp);
			}

		} else {
			log.info("Inside else block implies external is present");

			List<ICETEEConsolidated> internalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal,(sum(sit.weighted_total)/count(*)) * (i.tee_percent/100) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' and i.teeType ='Internal' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));

			List<ICETEEConsolidated> externalTeeRecords = mDao
					.getJdbcTemplate()
					.query("select ss.sap_id as sapId,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal,(sum(sit.weighted_total)/count(*)) * (i.tee_percent/100) as teeWeightedTotal ,count(*)"
							+ "	from tee_total sit,student ss, course c, tee i "
							+ "	 where ss.sap_id = sit.sap_id "
							+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id "
							+ "	 and c.sap_course_id = ? and  ss.active='Y' and i.teeType ='External' "
							+ "	 group by ss.sap_id "
							+ "	  order by ss.sap_id asc ",
							new Object[] { courseId },
							new BeanPropertyRowMapper(ICETEEConsolidated.class));
			for (ICETEEConsolidated tt : internalTeeRecords) {
				Map<String, String> mp = new HashMap();
				mp.put("sapId", tt.getSapId());
				mp.put("studentName", tt.getFirstName());
				mp.put("teeInternalTotal", tt.getTeeTotal() + "");
				mp.put("teeInternalWtTotal", tt.getTeeWeightedTotal() + "");

				for (ICETEEConsolidated pp : externalTeeRecords) {
					if (pp.getSapId().equals(tt.getSapId())) {
						mp.put("teeExternalTotal", pp.getTeeTotal() + "");
						mp.put("teeExternalWtTotal", pp.getTeeWeightedTotal()
								+ "");
					}
				}

				lstMap.add(mp);
			}

		}
		return lstMap;

	}

	public Map<String, CourseBean> findCourse() {
		Map<String, CourseBean> map = new HashMap<String, CourseBean>();
		List<CourseBean> bean = courseService.getAllCourses();

		for (CourseBean bn : bean) {
			map.put(bn.getSap_course_id(), bn);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public Map<String, List<ICETEEConsolidated>> getMapOfAllScores(
			String session) {

		List<ICETEEConsolidated> totalIceRecords = mDao
				.getJdbcTemplate()
				.query("select ss.sap_id as sapId ,c.sap_course_id as courseId,c.course_name,ss.student_name as firstName,sum(sit.ice_total)/count(*) as iceTotal, sum(sit.weighted_total)/count(*) as iceWeightedTotal ,count(*)"
						+ "	from ice_total sit,student ss, course c, ice i "
						+ "	 where ss.sap_id = sit.sap_id "
						+ "	 and sit.ice_id = i.id and i.course_id = c.sap_course_id and   ss.active='Y' and ss.session = ?  and c.session=?"

						+ "	 	 group by c.sap_course_id , ss.sap_id  "
						+ "	  order by ss.sap_id,c.sap_course_id asc  ",
						new Object[] { session, session },
						new BeanPropertyRowMapper(ICETEEConsolidated.class));

		List<ICETEEConsolidated> externalTeeRecords = mDao
				.getJdbcTemplate()
				.query("select ss.sap_id as sapId ,c.sap_course_id as courseId,c.course_name,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal, sum(sit.weighted_total)/count(*) as teeWeightedTotal ,count(*)"
						+ "	from tee_total sit,student ss, course c, tee i "
						+ "	 where ss.sap_id = sit.sap_id "
						+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id  and  ss.active='Y' and ss.session = ? and c.session=? and i.teeType ='External' "
						+ "	  group by c.sap_course_id , ss.sap_id "
						+ "	  order by ss.sap_id,c.sap_course_id asc ",
						new Object[] { session, session },
						new BeanPropertyRowMapper(ICETEEConsolidated.class));

		List<ICETEEConsolidated> internalTeeRecords = mDao
				.getJdbcTemplate()
				.query("select ss.sap_id as sapId ,c.sap_course_id as courseId,c.course_name,ss.student_name as firstName,sum(sit.tee_total)/count(*) as teeTotal, sum(sit.weighted_total)/count(*) as teeWeightedTotal ,count(*)"
						+ "	from tee_total sit,student ss, course c, tee i "
						+ "	 where ss.sap_id = sit.sap_id "
						+ "	 and sit.tee_id = i.id and i.course_id = c.sap_course_id  and  ss.active='Y' and ss.session = ? and c.session=? and i.teeType ='Internal' "
						+ "	  group by c.sap_course_id , ss.sap_id "
						+ "	  order by ss.sap_id,c.sap_course_id asc ",
						new Object[] { session, session },
						new BeanPropertyRowMapper(ICETEEConsolidated.class));

		Map<String, List<ICETEEConsolidated>> sMap = new HashMap<String, List<ICETEEConsolidated>>();
		if (internalTeeRecords != null)
			for (ICETEEConsolidated cc : internalTeeRecords) {
				String sapId = cc.getSapId();

				if (!sMap.containsKey(cc.getSapId())) {
					List<ICETEEConsolidated> ccList = new ArrayList<ICETEEConsolidated>();

					ccList.add(cc);
					sMap.put(sapId, ccList);
				} else {
					List<ICETEEConsolidated> ccList = sMap.get(cc.getSapId());
					if (!ccList.isEmpty()) {
						if (cc == null
								|| (cc.getTeeTotal() == null || cc
										.getTeeTotal().isEmpty()))
							continue;

						ICETEEConsolidated tt = ccList.get(0);
						log.info("tt.getTeeWeightedTotal()"
								+ tt.getTeeWeightedTotal());

						log.info("cc.getTeeWeightedTotal()"
								+ cc.getTeeWeightedTotal());
						tt.setTeeTotal((Double.valueOf(tt.getTeeTotal()) + Double
								.valueOf(cc.getTeeTotal())) + "");
						if (tt.getTeeWeightedTotal() != null
								&& !tt.getTeeWeightedTotal().isEmpty())
							tt.setTeeWeightedTotal(""
									+ (Double.valueOf(tt.getTeeWeightedTotal()) + Double
											.valueOf(cc.getTeeWeightedTotal())));
						ccList.clear();
						ccList.add(tt);
					} else {
						List<ICETEEConsolidated> newccList = new ArrayList<ICETEEConsolidated>();

						newccList.add(cc);
						sMap.put(sapId, newccList);
					}

				}

			}
		if (externalTeeRecords != null)
			for (ICETEEConsolidated cc : externalTeeRecords) {
				String sapId = cc.getSapId();

				if (!sMap.containsKey(cc.getSapId())) {
					List<ICETEEConsolidated> ccList = new ArrayList<ICETEEConsolidated>();

					ccList.add(cc);
					sMap.put(sapId, ccList);
				} else {
					List<ICETEEConsolidated> ccList = sMap.get(cc.getSapId());
					if (!ccList.isEmpty()) {
						ICETEEConsolidated tt = ccList.get(0);
						if (cc == null
								|| (cc.getTeeTotal() == null || cc
										.getTeeTotal().isEmpty()))
							continue;
						tt.setTeeTotal(""
								+ (Double.valueOf(tt.getTeeTotal()) + Double
										.valueOf(cc.getTeeTotal())));
						tt.setTeeWeightedTotal(""
								+ (Double.valueOf(tt.getTeeWeightedTotal()) + Double
										.valueOf(cc.getTeeWeightedTotal())));
						ccList.clear();
						ccList.add(tt);
					} else {
						List<ICETEEConsolidated> newccList = new ArrayList<ICETEEConsolidated>();

						newccList.add(cc);
						sMap.put(sapId, newccList);
					}

				}

			}

		Map<String, List<ICETEEConsolidated>> finalList = new HashMap<String, List<ICETEEConsolidated>>();

		for (ICETEEConsolidated consolidated : totalIceRecords) {

			List<ICETEEConsolidated> tt = sMap.get(consolidated.getSapId());
			if (tt != null) {

				for (ICETEEConsolidated t : tt) {
					if (t.getCourseId().equals(consolidated.getCourseId())) {
						consolidated.setTeeTotal(t.getTeeTotal());
						consolidated.setTeeWeightedTotal(t
								.getTeeWeightedTotal());
					}
				}

			}
			if (finalList.containsKey(consolidated.getSapId())) {
				finalList.get(consolidated.getSapId()).add(consolidated);
			} else {
				List<ICETEEConsolidated> marksList = new ArrayList<ICETEEConsolidated>();
				marksList.add(consolidated);
				finalList.put(consolidated.getSapId(), marksList);
			}

		}
		return finalList;

	}

	public List<Marks> getStudentsToCourse(String courseId) {

		List<Marks> mrkList = mDao
				.getJdbcTemplate()
				.query("select distinct scf.student_sap_id as sapId,s.student_name as name from student_course_faculty scf, student s "
						+ "where scf.student_sap_id = s.sap_id "
						+ "  and scf.course_id=? and  s.active='Y'  order by scf.student_sap_id asc",
						new Object[] { courseId },
						new BeanPropertyRowMapper(Marks.class));

		return mrkList;
	}

	public List<ConsoleIce> getConsoliatedIceForStudents(String courseId,
			double ratio) {

		String sql = "select t.ice_id as icId , t.sap_id as sapId, sr.rollNo as rollNo,s.first_name as fname,s.last_name as lname,c.course_name as cname ,t.weighted_total  as weighted_total"
				+ " from ice i, ice_total t, course c,student s,sap_roll sr where "
				+ "i.id = t.ice_id and i.course_id = c.sap_course_id and s.sap_id = t.sap_id and s.sap_id=sr.sap_id and c.sap_course_id =? and  s.active='Y' order by t.sap_id,t.ice_id asc ";
		List<ConsoleIcScore> icScores = mDao.getJdbcTemplate().query(sql,
				new Object[] { courseId },
				new BeanPropertyRowMapper(ConsoleIcScore.class));
		List<ConsoleIce> iceList = new ArrayList<>();

		Map<String, Map<String, String>> map = new LinkedHashMap<String, Map<String, String>>();
		Map<String, String> idToName = new HashMap<>();
		for (ConsoleIcScore cs : icScores) {

			if (!map.containsKey(cs.getSapId())) {
				Map<String, String> markMap = new LinkedHashMap();
				markMap.put(cs.getIcId(), cs.getWeighted_total());
				map.put(cs.getSapId(), markMap);
				idToName.put(cs.getSapId(), cs.getFname() + "," + cs.getLname());
			} else {
				map.get(cs.getSapId())
						.put(cs.getIcId(), cs.getWeighted_total());
			}
		}

		for (Map.Entry<String, Map<String, String>> m : map.entrySet()) {
			ConsoleIce ic = new ConsoleIce();
			ic.setSapId(m.getKey());
			ic.setName(idToName.get(m.getKey()));
			ic.setIceMarks(m.getValue());
			int total = m.getValue().size() * 100;
			ic.setTotal("" + total);
			double t = 0;
			for (Map.Entry<String, String> e : m.getValue().entrySet()) {
				t = t + Double.valueOf(e.getValue());
			}
			ic.setScored(t + "");
			ic.setCalc((Math.ceil((t / total) * ratio)) + "");
			iceList.add(ic);
		}

		return iceList;

	}

	public List<Marks> populateStudentMarksIfAny(List<Marks> input, String icId) {
		List<Marks> mrkList = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		for (Marks m : input) {
			ids.add(m.getSapId());
		}

		String sql = "select distinct s.student_name as name,m.sap_id  as sapId ,sr.rollNo as rollNo,m.criteria_1_marks,m.criteria_2_marks,m.criteria_3_marks,m.criteria_4_marks"
				+ ",m.criteria_5_marks,m.criteria_6_marks,m.criteria_7_marks,m.criteria_8_marks,m.criteria_9_marks,m.criteria_10_marks,m.remarks,"
				+ "m.weightage_1,m.weightage_2,m.weightage_3,m.weightage_4,m.weightage_5,m.weightage_6,m.weightage_7,m.weightage_8,"
				+ "m.weightage_9,m.weightage_10 from marks m,student s,sap_roll sr  where m.assignment_id = :icId and m.sap_id =s.sap_id and s.sap_id=sr.sap_id and m.sap_id in (:ids) and s.active='Y' order by m.sap_id asc";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		parameters.addValue("icId", icId);
		mrkList = mDao.getNamedParameterJdbcTemplate().query(sql, parameters,
				new BeanPropertyRowMapper(Marks.class));
		List<Marks> newList = new ArrayList<>();

		for (Marks m : mrkList) {
			List<IceTotalBean> iceBeanList = iceTotalDao
					.getJdbcTemplate()
					.query("select * from ice_total where ice_id =? and sap_id=?",
							new Object[] { icId, m.getSapId() },
							new BeanPropertyRowMapper(IceTotalBean.class));
			if (!iceBeanList.isEmpty()) {

				m.setCriteriaTotal(iceBeanList.get(0).getIce_total());
				m.setWeightedTotal(iceBeanList.get(0).getWeighted_total());
			}

			newList.add(m);
		}
		for (Marks m : input) {
			if (!newList.contains(m))
				newList.add(m);
		}

		return newList;
	}

	public List<Marks> populateStudentMarksForTeeIfAny(List<Marks> input,
			String teeId) {
		List<Marks> mrkList = new ArrayList<>();
		List<String> ids = new ArrayList<>();

		for (Marks m : input) {
			ids.add(m.getSapId());
		}
		System.out.println("ids- --- " + ids);
		System.out.println("teeId- --- " + teeId);

		String sql = "select distinct s.student_name as name,m.sap_id  as sapId ,sr.rollNo,m.criteria_1_marks,m.criteria_2_marks,m.criteria_3_marks,m.criteria_4_marks"
				+ ",m.criteria_5_marks,m.criteria_6_marks,m.criteria_7_marks,m.criteria_8_marks,m.criteria_9_marks,m.criteria_10_marks,"
				+ "m.weightage_1,m.weightage_2,m.weightage_3,m.weightage_4,m.weightage_5,m.weightage_6,m.weightage_7,m.weightage_8,"
				+ "m.weightage_9,m.weightage_10 from tee_marks m,student s,sap_roll sr where m.assignment_id = :teeId and m.sap_id =s.sap_id and s.sap_id=sr.sap_id and m.sap_id in (:ids) and s.active='Y' order by m.sap_id asc";

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		parameters.addValue("ids", ids);
		parameters.addValue("teeId", teeId);
		mrkList = mDao.getNamedParameterJdbcTemplate().query(sql, parameters,
				new BeanPropertyRowMapper(Marks.class));
		List<Marks> newList = new ArrayList<>();
		System.out.println("mrkList ------ " + mrkList);
		for (Marks m : mrkList) {
			System.out.println("Marks bean ------ " + m);
			List<TEETotalBean> iceBeanList = teeTotalDao
					.getJdbcTemplate()
					.query("select * from tee_total where tee_id =? and sap_id=?",
							new Object[] { teeId, m.getSapId() },
							new BeanPropertyRowMapper(TEETotalBean.class));
			if (!iceBeanList.isEmpty()) {

				m.setCriteriaTotal(iceBeanList.get(0).getTee_total());
				m.setWeightedTotal(iceBeanList.get(0).getWeighted_total());
			}

			newList.add(m);
		}
		for (Marks m : input) {
			System.out.println("Marks m bean ------------ " + m);
			if (!newList.contains(m))
				newList.add(m);
		}

		return newList;
	}

	public void fillMarksAndIceTotal(Map<String, String> allRequestParams,
			String status) {

		String icId = allRequestParams.get("icId");

		IceBean bean = iceService.loadIc(icId);

		Set<String> keys = allRequestParams.keySet();
		List<MarksBean> marksList = new ArrayList<>();
		List<IceTotalBean> iceTotalList = new ArrayList<>();
		for (String key : keys) {
			log.info("Key - " + key);
			if (key.startsWith("criteria1")) {
				String sapId = StringUtils.substring(key, 9);
				String didChange = allRequestParams.get("didChange" + sapId);
				System.out.println("didChange" + sapId + didChange);
				if ("true".equals(didChange)) {
					MarksBean marks = new MarksBean();
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
					marks.setAssignment_id(icId);
					//marks.setRemarks(allRequestParams.get("remarks" + sapId));
					marks.setActive("Y");

					IceTotalBean tb = new IceTotalBean();
					tb.setActive("Y");
					tb.setIce_total(allRequestParams.get("total" + sapId));
					tb.setWeighted_total(allRequestParams.get("weighted"
							+ sapId));
					tb.setSap_id(sapId);
					tb.setIce_id(icId);
					marksList.add(marks);
					iceTotalList.add(tb);
				}
			}
		}

		mDao.deleteSQLBatch(marksList,
				"delete from marks where sap_id = :sap_id and assignment_id = :assignment_id");
		iceTotalDao
				.deleteSQLBatch(iceTotalList,
						"delete from ice_total where sap_id = :sap_id and ice_id = :ice_id");
		log.info("marksList-------------" + marksList);
		mDao.insertBatch(marksList);
		iceTotalDao.insertBatch(iceTotalList);

		bean.setStatus(status);
		iceService.updateIcStatus(bean, status);

	}
}
