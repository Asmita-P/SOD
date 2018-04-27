package com.schoolOfDesign.jforce.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserBean;
import com.schoolOfDesign.jforce.beans.icbeans.UserDetailsBean;
import com.schoolOfDesign.jforce.daos.icdao.UserDao;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.IceService;
import com.schoolOfDesign.jforce.service.Notifier;
import com.schoolOfDesign.jforce.service.UserService;
import com.schoolOfDesign.jforce.utils.PasswordGenerator;
import com.schoolOfDesign.jforce.utils.RoleUtils;

@Controller
public class LoginController {

	@Autowired
	UserService userService;

	@Autowired
	private CourseService courseService;

	@Autowired
	private IceService iceService;

	@Autowired
	HttpSession session;

	@Autowired
	Notifier notifier;
	
	@Autowired
	UserDao userDAO;

	@Value("${sodUrl}")
	private String sodUrl;

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping(value = { "", "/login" }, method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	@RequestMapping(value = "/autheticate", method = { RequestMethod.POST })
	public ModelAndView autheticate(@RequestParam("username") String username,
			@RequestParam("password") String password) {

		ModelAndView mav = new ModelAndView();
		UserBean bean = userService.getUserBean(username);
		Map<String, String> courseData = new HashMap<String, String>();

		if (bean != null) {
			String encodedPassword = bean.getPassword();
			if (PasswordGenerator.matches(password, encodedPassword)) {

				List<String> roles = bean.getRoles();
				String roleName = RoleUtils.getViewName(roles);
				bean.setRoleName(roleName);
				session.setAttribute("user", bean);

				List<CourseBean> courseList = null;
				switch (roleName) {
				case "faculty":
					String facultyId = bean.getUsername();

					courseList = courseService
							.getCoursesAssignedToFaculty(facultyId);

					for (CourseBean courseBean : courseList) {
						courseData.put(
								courseBean.getSap_course_id().toString(),
								courseBean.getCourse_name());
					}
					courseData.put("showAssigned", "showAssigned");
					mav.addObject("courseData", courseData);
					mav.setViewName("iceList");
					/*
					 * List<Ic> icList =
					 * iceService.getAllIcesForFaculty(bean.getUsername());
					 * 
					 * mav.addObject("iceList", icList);
					 */
					break;

				case "authority":
					courseList = courseService.getAllCourses();

					for (CourseBean courseBean : courseList) {
						courseData.put(
								courseBean.getSap_course_id().toString(),
								courseBean.getCourse_name());
					}
					mav.addObject("courseData", courseData);
					mav.setViewName("iceList");
					break;

				case "cordinator":
					courseList = courseService.getAllCourses();

					for (CourseBean courseBean : courseList) {
						courseData.put(
								courseBean.getSap_course_id().toString(),
								courseBean.getCourse_name());
					}
					mav.addObject("courseData", courseData);
					mav.setViewName("iceList");

					break;

				case "exam":
					courseList = courseService.getAllCourses();

					for (CourseBean courseBean : courseList) {
						courseData.put(
								courseBean.getSap_course_id().toString(),
								courseBean.getCourse_name());
					}
					mav.addObject("courseData", courseData);
					mav.setViewName("teeList");

					break;

				}

			} else {
				mav.addObject("error", "Invalid details");
				mav.setViewName("login");
			}

		} else {
			mav.addObject("error", "Invalid details");
			mav.setViewName("login");
		}

		return mav;

	}

	@RequestMapping(value = "/logout", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView logout() {
		session.invalidate();
		ModelAndView mav = new ModelAndView("login");
		return mav;
	}

	@RequestMapping(value = "/changePasswordForm", method = RequestMethod.GET)
	public ModelAndView changePasswordForm() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("user", new UserBean());
		mav.setViewName("changePassword");
		return mav;
	}

	@RequestMapping(value = "/changePassword", method = { RequestMethod.POST,
			RequestMethod.GET })
	public ModelAndView changePassword(
			@RequestParam("username") String username,
			@RequestParam("password") String password) {
		ModelAndView mav = new ModelAndView("changePassword");
		UserBean bean = userService.getUserBean(username);
		logger.info(username);
		logger.info("old password" + bean.getPassword());
		String encodePass = PasswordGenerator.generatePassword(password);
		bean.setPassword(encodePass);
		logger.info("new password:" + encodePass);
		userService.getUpdatedPass(bean);
		mav.setViewName("login");
		return mav;
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(@RequestParam("username") String username,
			HttpServletRequest request, Model m) {
		logger.info("Forgot username " + username);
		try {
			UserBean user = userService.getUserBean(username);
			UserDetailsBean userDetails = user.getUserDetails();
			String newPassword = userDAO.resetPassword(user);
			// UserBean newUser = userService.getUserBean(username);
			Map<String, String> email = new HashMap<String, String>();
			Map<String, String> mobiles = new HashMap<String, String>();
			String subject = "School of design Reset Password ";
			String message = "Login Credentials for School of Design App\\r\\n\\r\\nApp can be accessed via URL: "
					+ sodUrl
					+ " \\r\\n\\r\\nUsername :  "
					+ username
					+ "\\r\\nPassword : " + newPassword;

			// email.put(username, user.getEmail());

			boolean sucessStatus = true;
			email.put(username, userDetails.getEmail());

			notifier.sendEmail(email, mobiles, subject, message);

			System.out.println("status is ---" + sucessStatus);
			if (sucessStatus) {
				setSuccess(request,
						"Password reset successfully. A mail has been sent to "
								+ userDetails.getEmail()
								+ " containing the new password details.");
				logger.info("password is --" + newPassword);
			} else {
				setError(request, "Error in reset password");
			}
		} catch (ValidationException ex) {
			setError(request, ex.getMessage());
			/*
			 * m.addAttribute("webPage", new WebPage("forgot", "Forgot", true,
			 * true, true, true, false));
			 */
			return "redirect:/login";
		}
		/*
		 * m.addAttribute("webPage", new WebPage("login", "Login", true, true,
		 * true, true, false));
		 */
		return "redirect:/login";
	}

	public void setError(HttpServletRequest request, String errorMessage) {
		request.setAttribute("error", "true");
		request.setAttribute("errorMessage", errorMessage);
	}

	public void setSuccess(HttpServletRequest request, String successMessage) {
		request.setAttribute("success", "true");
		request.setAttribute("successMessage", successMessage);
	}

	public void setSuccess(RedirectAttributes redirectAttrs,
			String successMessage) {
		redirectAttrs.addFlashAttribute("success", "true");
		redirectAttrs.addFlashAttribute("successMessage", successMessage);
	}

	public void setError(RedirectAttributes redirectAttrs, String errorMessage) {
		redirectAttrs.addFlashAttribute("error", "true");
		redirectAttrs.addFlashAttribute("errorMessage", errorMessage);
	}
}
