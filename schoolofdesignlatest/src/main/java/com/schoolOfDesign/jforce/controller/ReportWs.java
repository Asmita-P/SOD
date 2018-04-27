package com.schoolOfDesign.jforce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.schoolOfDesign.jforce.beans.icbeans.CourseBean;
import com.schoolOfDesign.jforce.service.CourseService;
import com.schoolOfDesign.jforce.service.MarkSheetExcelService;

@RestController
public class ReportWs {

	@Autowired
	static Logger logger = LoggerFactory.getLogger(ReportWs.class);

	@Autowired
	MarkSheetExcelService markSheetExcelService;

	@Autowired
	CourseService courseService;

	@RequestMapping(value = "/downloadIcaReport", method = RequestMethod.GET)
	public void getFile(HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getIcaReports();
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			response.flushBuffer();
		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadIcaReportBulk", method = RequestMethod.GET)
	public void getFileBulk(HttpServletResponse response,
			@RequestParam(name = "session") String session) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getIcaReportBulks(session);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

			response.flushBuffer();
		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadStudentWiseMapping", method = RequestMethod.GET)
	public void downloadStudentWise(HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getMappingDescReports();
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadStudentWiseMappingTee", method = RequestMethod.GET)
	public void downloadStudentWiseMappingTee(HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getMappingDescReportsForTee();
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadModuleWiseMapping", method = RequestMethod.GET)
	public void downloadModuletWiseMapping(HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getCourseWiseMapper();
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadIcaSap", method = RequestMethod.GET)
	public void downloadIcaSap(@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		logger.info("session ----- " + session);
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService.getIcaSessionAndCourseWise(
					session, courseId, courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadTeeSap", method = RequestMethod.GET)
	public void downloadTeeSap(@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService.getTeeSessionAndCourseWise(
					session, courseId, courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			ex.printStackTrace();
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			// FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadInternalExternalTee", method = RequestMethod.GET)
	public void downloadInternalExternalTee(
			@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService
					.getTeeMarksheetForInternalAndExternal(session, courseId,
							courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadTeeMarkSheetBlank", method = RequestMethod.GET)
	public void downloadTeeMarkSheetBlank(
			@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService.getTeeMarksheet(session, courseId,
					courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadTeeMarkSheetBlankForExternal", method = RequestMethod.GET)
	public void downloadTeeMarkSheetBlankExternal(
			@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService.getTeeMarksheetExternal(session,
					courseId, courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadClassWise", method = RequestMethod.GET)
	public void downloadClassWise(
			@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			@RequestParam(name = "year") String year,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream
			CourseBean courseBean = courseService.getCourse(courseId);
			filePath = markSheetExcelService.getClassWise(session, courseId,
					courseBean, year);
			File f = new File(filePath);
			is = new FileInputStream(filePath);
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadMarks", method = RequestMethod.GET)
	public void downloadMarks(
			@RequestParam(name = "studentId") String studentId,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getMarks(studentId);

			response.setContentType("application/pdf");

			is = new FileInputStream(filePath);
			File f = new File(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadMinMax", method = RequestMethod.GET)
	public void downloadMinMax(@RequestParam(name = "session") String session,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = "";
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getMinMax(session);
			File f = new File(filePath);
			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);

			// response.setContentType("image/png");
			// copy it to response's OutputStream

			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}

			FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadIcaPdfGraphs", method = RequestMethod.GET)
	public void downloadIcaGraphsStudentWise(
			@RequestParam(name = "session") String session,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = null;
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getIcaReportBulksPdf(session);
			File f = new File(filePath);
			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}
			if (filePath != null)
				FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadAttributeMapGraphs", method = RequestMethod.GET)
	public void downloadAttributeMapGraphs(
			@RequestParam(name = "session") String session,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = null;
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getAttributeMap(session);
			File f = new File(filePath);

			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}
			if (filePath != null)
				FileUtils.deleteQuietly(new File(filePath));
		}

	}
	@RequestMapping(value = "/downloadAttributeMapGraphsForTee", method = RequestMethod.GET)
	public void downloadAttributeMapGraphsForTee(
			@RequestParam(name = "session") String session,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = null;
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getAttributeMapForTee(session);
			File f = new File(filePath);

			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}
			if (filePath != null)
				FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadStudentWiseReport", method = RequestMethod.GET)
	public void downloadStudentWiseReport(
			@RequestParam(name = "session") String session,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = null;
		try {
			// get your file as InputStream

			filePath = markSheetExcelService.getStudentWiseFinalReport(session);
			File f = new File(filePath);

			response.setContentType("application/zip");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}
			if (filePath != null)
				FileUtils.deleteQuietly(new File(filePath));
		}

	}

	@RequestMapping(value = "/downloadMappingDescLineChart", method = RequestMethod.GET)
	public void downloadMappingDescLineChart(
			@RequestParam(name = "session") String session,
			@RequestParam(name = "courseId") String courseId,
			HttpServletResponse response) {
		InputStream is = null;
		String filePath = null;
		try {
			// get your file as InputStream

			CourseBean courseBean = courseService.getCourse(courseId);

			filePath = markSheetExcelService.getOverall(session, courseId,
					courseBean.getCourse_name());
			File f = new File(filePath);

			response.setContentType("application/pdf");
			response.setHeader("Content-Disposition", "attachment; filename="
					+ f.getName());

			is = new FileInputStream(filePath);
			// response.setContentType("image/png");
			// copy it to response's OutputStream
			org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();

		} catch (Exception ex) {
			logger.info(
					"Error writing file to output stream. Filename was '{}'",
					ex);
			throw new RuntimeException("IOError writing file to output stream");
		} finally {
			if (is != null) {
				org.apache.commons.io.IOUtils.closeQuietly(is);
			}
			if (filePath != null)
				FileUtils.deleteQuietly(new File(filePath));
		}

	}

}