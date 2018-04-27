package com.schoolOfDesign.jforce.controller;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.schoolOfDesign.jforce.beans.icbeans.FacultyBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserDetailsBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserRoleBean;
import com.schoolOfDesign.jforce.daos.icdao.FacultyDao;
import com.schoolOfDesign.jforce.daos.icdao.UserDao;
import com.schoolOfDesign.jforce.daos.icdao.UserDetailsDao;
import com.schoolOfDesign.jforce.daos.icdao.UserRoleDao;
import com.schoolOfDesign.jforce.service.ExcelReader;
import com.schoolOfDesign.jforce.service.FacultyService;
import com.schoolOfDesign.jforce.utils.PasswordGenerator;

@Controller
public class FacultyMasterController {
	@Value("#{'${tempPath:G:}'}")
	String op;

	@Autowired
	ExcelReader reader;

	Logger log = LoggerFactory.getLogger(IceController.class);

	@Autowired
	ExcelReader excelReader;

	@Autowired
	FacultyService facultyService;

	@Autowired
	FacultyDao dao;

	@Autowired
	UserDao userDao;

	@Autowired
	UserRoleDao roleDao;

	@Autowired
	UserDetailsDao detailsDao;

	static List<String> headerList = new ArrayList<String>();

	static {
		String arr[] = { "Type Of Faculty (H/P)", "ID of the Faculty",
				"Name of the Faculty", "PAN No.", "Mobile No.", "Email Id",
				"Vendor Code" };
		headerList = Arrays.asList(arr);
	}

	@RequestMapping(value = "/addExternalFacultyForm", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addExternalFacultyForm(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("addExternalFaculty");
		mav.addObject("allRequestParams", allRequestParams);
		return mav;
	}

	@RequestMapping(value = "/addExternalFaculty", method = {
			RequestMethod.POST, RequestMethod.GET })
	public ModelAndView addExternalFaculty(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mav = new ModelAndView("addFacultyCourse");

		String sapId = allRequestParams.get("sap_id");

		UserBean userBean = new UserBean();
		userBean.setUsername(sapId);
		userBean.setPassword(PasswordGenerator.generatePassword("fsafssw2"));
		userBean.setActive("Y");
		List<UserBean> uBeanList = new ArrayList<UserBean>();
		uBeanList.add(userBean);
		userDao.insertBatch(uBeanList);

		UserRoleBean ur = new UserRoleBean();
		ur.setActive("Y");
		ur.setRole_id(1);
		ur.setUsername(sapId);
		List<UserRoleBean> urBeanList = new ArrayList<UserRoleBean>();
		urBeanList.add(ur);
		roleDao.insertBatch(urBeanList);

		UserDetailsBean udb = new UserDetailsBean();
		udb.setEmail(allRequestParams.get("faculty_email_id"));
		udb.setPhone_no(allRequestParams.get("faculty_phone_no"));
		udb.setUsername(sapId);
		udb.setFirst_name(allRequestParams.get("faculty_name"));
		udb.setLast_name(allRequestParams.get("faculty_name"));
		List<UserDetailsBean> udBeanList = new ArrayList<UserDetailsBean>();
		udBeanList.add(udb);
		detailsDao.insertBatch(udBeanList);
		FacultyBean externalFaculty = facultyService.createExternalFaculty(
				allRequestParams.get("sap_id"),
				allRequestParams.get("faculty_name"),
				allRequestParams.get("faculty_email_id"),
				allRequestParams.get("faculty_phone_no"),
				allRequestParams.get("vendor_code"));

		return mav;
	}

	@RequestMapping(value = "/uploadFaculty", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView submit(
			@RequestParam Map<String, String> allRequestParams) {
		ModelAndView mv = new ModelAndView("uploadFaculty");
		List<String> operations = new ArrayList();
		operations.add("CREATE");
		operations.add("UPDATE");
		operations.add("ADDNEW");
		// operations.add("DELETE");
		mv.addObject("operations", operations);
		return mv;

	}

	@RequestMapping(value = "/uploadFacultyMaster", method = RequestMethod.POST)
	public @ResponseBody String uploadMasterFile(
			@RequestParam(name = "file", required = true) MultipartFile input,
			@RequestParam("operation") String operation) {
		String statusOfUpload = uploadFileAndReturnStatus(input, operation);
		return statusOfUpload;

	}

	private String uploadFileAndReturnStatus(MultipartFile file,
			String operation) {

		InputStream inputStream = null;
		ArrayList<FacultyBean> FacultyList = new ArrayList<FacultyBean>();
		List<UserBean> userBean = new ArrayList<UserBean>();
		List<UserRoleBean> userRole = new ArrayList<UserRoleBean>();
		List<UserDetailsBean> userDetails = new ArrayList<UserDetailsBean>();

		String fileName = file.getOriginalFilename();
		log.info("fileName Size = " + file.getSize());
		log.info("fileName = " + fileName);

		fileName = RandomStringUtils.randomAlphanumeric(10)
				+ fileName.substring(fileName.lastIndexOf("."),
						fileName.length());
		try {

			inputStream = file.getInputStream();
			String filePath = op + File.separator + fileName;

			File folderPath = new File(op);
			if (!folderPath.exists()) {
				log.info("Making Folder");
				boolean created = folderPath.mkdirs();
				log.info("created = " + created);
			}

			File dest = new File(filePath);

			FileUtils.copyInputStreamToFile(inputStream, dest);
			List<Map<String, String>> listCells = excelReader.readXLSXFile(dest
					.getAbsolutePath());
			List<String> ids = new ArrayList<String>();

			for (Map<String, String> m : listCells) {
				FacultyBean bean = new FacultyBean();
				UserBean ub = new UserBean();
				UserRoleBean ur = new UserRoleBean();
				UserDetailsBean udb = new UserDetailsBean();
				log.info("Map--->" + m);
				if (m.get("Employee Id / Visiting Faculty id") != null) {
					log.info("Inside first");
					bean.setSap_id(m.get("Employee Id / Visiting Faculty id"));
					bean.setFaculty_name(m.get(" Name of the Faculty"));
					bean.setFaculty_type(m
							.get("Type of Faculty (P = CF , H = VF)"));
					bean.setFaculty_email_id(m.get("email id"));
					// bean.setVendor_code(m.get("Vendor Code"));
					bean.setFaculty_phone_no(m.get("Mobile No."));
					FacultyList.add(bean);
				} else {
					bean.setSap_id(m.get("Employee No"));
					bean.setFaculty_name(m.get("Employee Name"));
					bean.setFaculty_type(m.get("Type Of Faculty (H/P)"));
					bean.setFaculty_email_id(m.get("email id"));
					bean.setVendor_code(m.get("Vendor Code"));
					bean.setFaculty_phone_no(m.get("Mobile No."));
					FacultyList.add(bean);
				}
				ub.setUsername(bean.getSap_id());
				ub.setPassword(PasswordGenerator.generatePassword("fsafssw2"));
				ub.setActive("Y");
				udb.setEmail(bean.getFaculty_email_id());
				udb.setPhone_no(bean.getFaculty_phone_no());
				udb.setUsername(bean.getSap_id());
				udb.setFirst_name(bean.getFaculty_name());
				udb.setLast_name(bean.getFaculty_name());
				bean.setActive("Y");
				ids.add(bean.getSap_id());
				ur.setActive("Y");
				ur.setRole_id(1);
				ur.setUsername(bean.getSap_id());
				userBean.add(ub);
				userRole.add(ur);
				userDetails.add(udb);

			}

			if ("CREATE".equals(operation)) {
				facultyService.updateFacultyList(ids);
				facultyService.insertFacultyList(FacultyList);
				userDao.insertBatch(userBean);
				roleDao.insertBatch(userRole);
				detailsDao.insertBatch(userDetails);

			}

			if ("ADDNEW".equals(operation)) {
				// facultyService.updateFacultyList(ids);
				facultyService.insertFacultyList(FacultyList);
				userDao.insertBatch(userBean);
				roleDao.insertBatch(userRole);
				detailsDao.insertBatch(userDetails);

			}
			if ("DELETE".equals(operation)) {
				facultyService.deleteFacultyList(ids);
			}

			if ("UPDATE".equals(operation)) {
				dao.updateBatch(FacultyList);
				userDao.updateBatch(userBean);
				roleDao.updateBatch(userRole);
				detailsDao.updateBatch(userDetails);
			}

			return "SUCCESS";

		} catch (Exception e) {

			log.error("ERROR", e);
			return "ERROR";

		}

	}
}
