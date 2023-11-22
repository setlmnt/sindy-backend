package com.ifba.educampo.service;

import com.lowagie.text.DocumentException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
@Slf4j
public class PdfService {
    private final TemplateEngine templateEngine;

    public PdfService() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    private String parseThymeleafTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }

    public byte[] generatePdfByTemplate(String template, Context context) {
        log.info("Generating PDF by template {}", template);
        String html = parseThymeleafTemplate(template, context);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            log.error("Error generating PDF by template {}", template);
            log.error(e.getMessage());
        }

        return new byte[0];
    }
}
