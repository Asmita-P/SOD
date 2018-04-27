package com.schoolOfDesign.jforce.utils;

import java.util.List;

public class RoleUtils {

	public static boolean isFaculty(List<String> roles) {
		return roles.contains("FACULTY");
	}

	public static boolean isCordinator(List<String> roles) {
		return roles.contains("COORDINATOR");
	}

	public static boolean isAuthority(List<String> roles) {
		return roles.contains("AUTHORITY");
	}

	public static boolean isExam(List<String> roles) {
		return roles.contains("EXAM");
	}

	public static String getViewName(List<String> roles) {
		String viewName = "";

		if (isFaculty(roles)) {
			viewName = "faculty";
		} else if (isCordinator(roles)) {
			viewName = "cordinator";
		} else if (isAuthority(roles)) {
			viewName = "authority";
		} else if (isExam(roles)) {
			viewName = "exam";
		}
		return viewName;

	}
}
