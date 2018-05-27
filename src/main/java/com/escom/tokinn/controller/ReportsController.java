package com.escom.tokinn.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

@Controller
@RequestMapping("/tokinn")
public class ReportsController {

	@GetMapping("/reportePDF")
	public @ResponseBody void productPDF(HttpServletResponse response) {
		try {
			InputStream  jasperStream  = this.getClass().getResourceAsStream("/jasperreports/reports/report.jrxml");
			JasperDesign design = JRXmlLoader.load(jasperStream);
			JasperReport report = JasperCompileManager.compileReport(design);
			
			Map<String, Object> parameterMap = new HashMap<>();
			
			//TODO: Cambiar List<String> por una lista de algún Mapeo
			List<String> products = new ArrayList<>();
			JRDataSource jRDataSource = new JRBeanCollectionDataSource(products);
			
			parameterMap.put("datasource",jRDataSource);
			JasperPrint jasperPrint = JasperFillManager.fillReport(report,parameterMap, jRDataSource);
			response.setContentType("application/x-pdf");
			response.setHeader("Content-Disposition","inline; filename=\"report.pdf\"");
			
			final OutputStream outputStream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			
		} catch (JRException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
