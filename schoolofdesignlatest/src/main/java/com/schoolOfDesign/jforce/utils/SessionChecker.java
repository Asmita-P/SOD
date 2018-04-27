package com.schoolOfDesign.jforce.utils;

import javax.servlet.http.HttpSession;

public class SessionChecker {

	public static boolean isUserAbsent(HttpSession session) {
		return (session.getAttribute("user") == null);

	}
}
