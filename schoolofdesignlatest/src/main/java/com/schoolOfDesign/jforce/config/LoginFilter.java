package com.schoolOfDesign.jforce.config;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.schoolOfDesign.jforce.utils.PasswordGenerator;

@WebFilter("/*")
public class LoginFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	private static final Set<String> ALLOWED_PATHS = Collections
			.unmodifiableSet(new HashSet<>(Arrays.asList("", "/login",
					"/changePasswordForm", "changePassword", "/logout", "/",
					"/autheticate", "/resources",
					"/resources/static/resources/images/logoSOD.png")));

	@Override
	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		HttpSession session = request.getSession(false);
		String path = request.getRequestURI()
				.substring(request.getContextPath().length())
				.replaceAll("[/]+$", "");

		boolean loggedIn = (session != null && session.getAttribute("user") != null);
		boolean allowedPath = false;
		for (String all : ALLOWED_PATHS) {
			allowedPath = StringUtils.containsIgnoreCase(path, all);
			if (allowedPath)
				break;
		}

		// ALLOWED_PATHS.contains(path);
		// logger.info("Path"+path + allowedPath);

		if (loggedIn || allowedPath) {
			chain.doFilter(req, res);
		} else {
			response.sendRedirect(request.getContextPath() + "/login");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

	// ...
}