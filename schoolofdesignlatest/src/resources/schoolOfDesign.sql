-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               5.7.10-log - MySQL Community Server (GPL)
-- Server OS:                    Win64
-- HeidiSQL Version:             9.4.0.5125
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for schoolofdesign
CREATE DATABASE IF NOT EXISTS `schoolofdesign` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `schoolofdesign`;

-- Dumping structure for table schoolofdesign.batch_details
CREATE TABLE IF NOT EXISTS `batch_details` (
  `batch_name` varchar(50) DEFAULT NULL,
  `acad_year` varchar(45) DEFAULT NULL,
  `acad_month` varchar(45) DEFAULT NULL,
  `ice_startDate` varchar(45) DEFAULT NULL,
  `ice_endDate` varchar(45) DEFAULT NULL,
  `ice_midReviewDate` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `semester` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.course
CREATE TABLE IF NOT EXISTS `course` (
  `course_name` varchar(45) DEFAULT NULL,
  `sap_course_id` varchar(45) DEFAULT NULL,
  `module_abbr` varchar(45) DEFAULT NULL,
  `module_name` varchar(45) DEFAULT NULL,
  `prog_name` varchar(45) DEFAULT NULL,
  `subject_code` varchar(45) DEFAULT NULL,
  `subject_abbr` varchar(45) DEFAULT NULL,
  `session` varchar(45) DEFAULT NULL,
  `registered` varchar(45) DEFAULT NULL,
  `prog_code` varchar(45) DEFAULT NULL,
  `year` varchar(45) DEFAULT NULL,
  `students_booked_to_event` varchar(45) DEFAULT NULL,
  `ica_tee_split` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.cutoff
CREATE TABLE IF NOT EXISTS `cutoff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cutoff` int(11) DEFAULT NULL,
  `criteria` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.faculty
CREATE TABLE IF NOT EXISTS `faculty` (
  `sap_id` varchar(45) NOT NULL,
  `faculty_name` varchar(45) DEFAULT NULL,
  `faculty_email_id` varchar(45) DEFAULT NULL,
  `faculty_phone_no` varchar(45) DEFAULT NULL,
  `faculty_type` varchar(45) DEFAULT NULL,
  `vendor_code` varchar(45) DEFAULT NULL,
  `no_of_session_scheduled` varchar(50) DEFAULT NULL,
  `no_of_session_taken` varchar(45) DEFAULT NULL,
  `ica_created` varchar(45) DEFAULT NULL,
  `ica_invited` varchar(45) DEFAULT NULL,
  `ica_submitted` varchar(45) DEFAULT NULL,
  `ica_reexam` varchar(45) DEFAULT NULL,
  `ica_duplicate` varchar(45) DEFAULT NULL,
  `no_of_hrs_taken` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.ice
CREATE TABLE IF NOT EXISTS `ice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` varchar(45) DEFAULT NULL,
  `owner_faculty` varchar(45) DEFAULT NULL,
  `assigned_faculty` varchar(45) DEFAULT NULL,
  `is_reexam` varchar(45) DEFAULT NULL,
  `org_ice_id` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  UNIQUE KEY `ice_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.ice_criteria
CREATE TABLE IF NOT EXISTS `ice_criteria` (
  `ice_id` varchar(45) DEFAULT NULL,
  `criteria_desc` varchar(45) DEFAULT NULL,
  `weightage` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mapping_desc` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  UNIQUE KEY `criteria_id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.ice_cutoff
CREATE TABLE IF NOT EXISTS `ice_cutoff` (
  `ice_id` int(11) DEFAULT NULL,
  `cutoff_id` int(11) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.ice_total
CREATE TABLE IF NOT EXISTS `ice_total` (
  `sap_id` varchar(45) DEFAULT NULL,
  `ice_id` varchar(45) DEFAULT NULL,
  `ice_total` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `weighted_total` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.marks
CREATE TABLE IF NOT EXISTS `marks` (
  `sap_id` varchar(25) DEFAULT NULL,
  `id` varchar(45) DEFAULT NULL,
  `criteria_1_marks` varchar(25) NOT NULL,
  `criteria_2_marks` varchar(25) DEFAULT NULL,
  `criteria_3_marks` varchar(25) DEFAULT NULL,
  `criteria_4_marks` varchar(25) DEFAULT NULL,
  `criteria_5_marks` varchar(45) DEFAULT NULL,
  `criteria_6_marks` varchar(45) DEFAULT NULL,
  `criteria_7_marks` varchar(45) DEFAULT NULL,
  `criteria_8_marks` varchar(45) DEFAULT NULL,
  `criteria_9_marks` varchar(45) DEFAULT NULL,
  `criteria_10_marks` varchar(45) DEFAULT NULL,
  `weightage_1` varchar(45) DEFAULT NULL,
  `weightage_2` varchar(45) DEFAULT NULL,
  `weightage_3` varchar(45) DEFAULT NULL,
  `weightage_4` varchar(45) DEFAULT NULL,
  `weightage_5` varchar(45) DEFAULT NULL,
  `weightage_6` varchar(45) DEFAULT NULL,
  `weightage_7` varchar(45) DEFAULT NULL,
  `weightage_8` varchar(45) DEFAULT NULL,
  `weightage_9` varchar(45) DEFAULT NULL,
  `weightage_10` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `assignment_id` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.role
CREATE TABLE IF NOT EXISTS `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  UNIQUE KEY `role_id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.student
CREATE TABLE IF NOT EXISTS `student` (
  `sap_id` varchar(45) NOT NULL,
  `dateOfBirth` varchar(45) DEFAULT NULL,
  `student_name` varchar(200) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `mother_name` varchar(45) DEFAULT NULL,
  `father_name` varchar(45) DEFAULT NULL,
  `middle_name` varchar(45) DEFAULT NULL,
  `phone_no` varchar(45) DEFAULT NULL,
  `father_mobileNo` varchar(45) DEFAULT NULL,
  `address` varchar(150) DEFAULT NULL,
  `street_1` varchar(45) DEFAULT NULL,
  `street_2` varchar(45) DEFAULT NULL,
  `city` varchar(45) DEFAULT NULL,
  `state` varchar(45) DEFAULT NULL,
  `zipcode` varchar(45) DEFAULT NULL,
  `email_id` varchar(45) DEFAULT NULL,
  `roll_no` varchar(50) DEFAULT NULL,
  `gender` varchar(45) DEFAULT NULL,
  `reg_prog_abbr` varchar(45) DEFAULT NULL,
  `enroll_year` int(20) DEFAULT NULL,
  `session` varchar(45) DEFAULT NULL,
  `current_prog_abbr` varchar(45) DEFAULT NULL,
  `current_prog_desc` varchar(145) DEFAULT NULL,
  `parent_email` varchar(50) DEFAULT NULL,
  `parent_phone` varchar(45) DEFAULT NULL,
  `current_acad_year` varchar(45) DEFAULT NULL,
  `current_acad_session` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.student_course_faculty
CREATE TABLE IF NOT EXISTS `student_course_faculty` (
  `student_sap_id` varchar(45) DEFAULT NULL,
  `course_id` varchar(45) DEFAULT NULL,
  `faculty_sap_id` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `acad_year` varchar(45) DEFAULT NULL,
  `acad_month` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee
CREATE TABLE IF NOT EXISTS `tee` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` varchar(45) DEFAULT NULL,
  `owner_faculty` varchar(45) DEFAULT NULL,
  `assigned_faculty` varchar(45) DEFAULT NULL,
  `is_reexam` varchar(45) DEFAULT NULL,
  `org_tee_id` varchar(45) DEFAULT NULL,
  `status` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee_criteria
CREATE TABLE IF NOT EXISTS `tee_criteria` (
  `tee_id` varchar(45) DEFAULT NULL,
  `criteria_desc` varchar(45) DEFAULT NULL,
  `weightage` varchar(45) DEFAULT NULL,
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mapping_desc` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee_cutoff
CREATE TABLE IF NOT EXISTS `tee_cutoff` (
  `tee_id` int(11) DEFAULT NULL,
  `cutoff_id` int(11) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee_marks
CREATE TABLE IF NOT EXISTS `tee_marks` (
  `sap_id` varchar(25) DEFAULT NULL,
  `id` varchar(45) DEFAULT NULL,
  `criteria_1_marks` varchar(45) DEFAULT NULL,
  `criteria_2_marks` varchar(45) DEFAULT NULL,
  `criteria_3_marks` varchar(45) DEFAULT NULL,
  `criteria_4_marks` varchar(45) DEFAULT NULL,
  `criteria_5_marks` varchar(45) DEFAULT NULL,
  `criteria_6_marks` varchar(45) DEFAULT NULL,
  `criteria_7_marks` varchar(45) DEFAULT NULL,
  `criteria_8_marks` varchar(45) DEFAULT NULL,
  `criteria_9_marks` varchar(45) DEFAULT NULL,
  `criteria_10_marks` varchar(45) DEFAULT NULL,
  `weightage_1` varchar(45) DEFAULT NULL,
  `weightage_2` varchar(45) DEFAULT NULL,
  `weightage_3` varchar(45) DEFAULT NULL,
  `weightage_4` varchar(45) DEFAULT NULL,
  `weightage_5` varchar(45) DEFAULT NULL,
  `weightage_6` varchar(45) DEFAULT NULL,
  `weightage_7` varchar(45) DEFAULT NULL,
  `weightage_8` varchar(45) DEFAULT NULL,
  `weightage_9` varchar(45) DEFAULT NULL,
  `weightage_10` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `assignment_id` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee_metadata
CREATE TABLE IF NOT EXISTS `tee_metadata` (
  `course_id` varchar(45) DEFAULT NULL,
  `ice_percent` varchar(45) DEFAULT NULL,
  `written_percent` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.tee_total
CREATE TABLE IF NOT EXISTS `tee_total` (
  `sap_id` varchar(45) DEFAULT NULL,
  `tee_id` varchar(45) DEFAULT NULL,
  `tee_total` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `weighted_total` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.term_end_marks
CREATE TABLE IF NOT EXISTS `term_end_marks` (
  `sap_id` varchar(45) DEFAULT NULL,
  `course_id` varchar(45) DEFAULT NULL,
  `score_earned` varchar(45) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL,
  `ice_weighted` varchar(50) DEFAULT NULL,
  `total` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.user
CREATE TABLE IF NOT EXISTS `user` (
  `username` varchar(45) DEFAULT NULL,
  `password` varchar(450) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.user_details
CREATE TABLE IF NOT EXISTS `user_details` (
  `username` varchar(25) DEFAULT NULL,
  `first_name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `phone_no` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
-- Dumping structure for table schoolofdesign.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `username` varchar(45) DEFAULT NULL,
  `role_id` int(11) DEFAULT NULL,
  `active` varchar(45) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Data exporting was unselected.
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
