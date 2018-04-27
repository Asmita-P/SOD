package com.schoolOfDesign.jforce.helpers;

import java.util.HashMap;
import java.util.List;

import model.Ic;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang3.RandomStringUtils;

public class JasperReport {

	public static JasperDesign jasperDesign;
	public static JasperPrint jasperPrint;
	public static net.sf.jasperreports.engine.JasperReport jasperReport;

	public static String generatePendingReport(List<Ic> icList, String format,
			String op) {

		String filePath = op + "//icePending.jrxml";
		String outputPath = op + "/" + RandomStringUtils.randomAlphabetic(8)
				+ "." + format.toLowerCase();
		try {
			jasperDesign = JRXmlLoader.load(filePath);
			jasperReport = JasperCompileManager.compileReport(jasperDesign);
			// fill the ready report with data and parameter
			jasperPrint = JasperFillManager.fillReport(jasperReport,
					new HashMap(), new JRBeanCollectionDataSource(icList));
			// view the report using JasperViewer

			switch (format) {
			case "PDF":
				JasperExportManager.exportReportToPdfFile(jasperPrint,
						outputPath);
				break;
			case "XLS":
				JRXlsExporter exporter = new JRXlsExporter();

				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,
						outputPath);
				exporter.exportReport();

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return outputPath;

	}
}
