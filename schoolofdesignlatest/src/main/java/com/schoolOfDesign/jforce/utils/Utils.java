package com.schoolOfDesign.jforce.utils;

import org.apache.log4j.Logger;

public class Utils {

	private static final Logger logger = Logger.getLogger(Utils.class);

	public static String getBlankIfNull(Object object) {
		return (object == null) ? "" : object.toString();
	}

	
}
