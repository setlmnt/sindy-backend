package br.com.sindy.domain.service.impl;

import br.com.sindy.domain.entity.Template;
import br.com.sindy.domain.enums.ErrorEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final JdbcTemplate jdbcTemplate;

    public byte[] generatePdfReport(Template template, Map<String, Object> parameters) {
        try {
            JasperReport report = compileReport(template);
            Connection connection = getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            log.error("Error generating report. " + e.getMessage());
            throw new ApiException(ErrorEnum.ERROR_WHILE_GENERATING_REPORT);
        }
    }

    public JasperReport compileReport(Template template) {
        try {
            InputStream templateStream = new ByteArrayInputStream(template.getBody().getBytes());
            return JasperCompileManager.compileReport(templateStream);
        } catch (Exception e) {
            log.error("Error generating report. " + e.getMessage());
            throw new ApiException(ErrorEnum.ERROR_WHILE_GENERATING_REPORT);
        }
    }

    private Connection getConnection() throws SQLException {
        return Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    }
}
