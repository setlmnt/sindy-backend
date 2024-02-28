package br.com.sindy.domain.service;

import br.com.sindy.domain.entity.Template;
import net.sf.jasperreports.engine.JasperReport;

import java.util.Map;

public interface ReportService {
    byte[] generatePdfReport(Template template, Map<String, Object> parameters);

    JasperReport compileReport(Template template);
}
