package com.ifba.educampo.domain.service;

import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

public interface ReportService {
    byte[] generateReport(String reportTemplate, Map<String, Object> parameters);

    JasperReport compileReport(String reportTemplate);
}
