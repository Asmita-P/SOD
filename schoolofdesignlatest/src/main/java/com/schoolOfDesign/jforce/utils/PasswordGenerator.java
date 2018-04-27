package com.schoolOfDesign.jforce.utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



public class PasswordGenerator {
	 // Keep same as configured in applicationContext-security.xml file
	private Logger logger = LoggerFactory.getLogger(PasswordGenerator.class);
	private final static int STRENGTH = 11;
	private final static BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(STRENGTH);
	
	
	public static String generatePassword(String pass) {
		return passwordEncoder.encode(pass);
	}
	
	public static boolean matches(String rawPassword, String encodedPassword) {
		return passwordEncoder.matches(rawPassword, encodedPassword);
	}
	
	public static void main(String[] args) {
		
		System.out.println(generatePassword("fsafssw2"));
	}

}
