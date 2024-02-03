package br.com.sindy.domain.service.impl;

import br.com.sindy.domain.enums.ErrorsEnum;
import br.com.sindy.domain.exception.ApiException;
import br.com.sindy.domain.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final JdbcTemplate jdbcTemplate;

    public byte[] generateReport(String reportTemplate, Map<String, Object> parameters) {
        try {
            JasperReport report = compileReport(reportTemplate);
            Connection connection = getConnection();
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parameters, connection);
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            log.error("Error generating report. " + e.getMessage());
            throw new ApiException(ErrorsEnum.ERROR_WHILE_GENERATING_REPORT);
        }
    }

    public JasperReport compileReport(String reportTemplate) {
        Path path = Paths.get("src/main/resources/templates", reportTemplate + ".jrxml");

        if (!path.toFile().exists()) {
            throw new ApiException(ErrorsEnum.REPORT_NOT_FOUND);
        }

        try {
            return JasperCompileManager.compileReport(path.toString());
        } catch (Exception e) {
            log.error("Error generating report. " + e.getMessage());
            throw new ApiException(ErrorsEnum.ERROR_WHILE_GENERATING_REPORT);
        }
    }

    private Connection getConnection() throws SQLException {
        return Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection();
    }
}
