package com.ifba.educampo.utils;

import com.lowagie.text.DocumentException;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PdfUtil {
    private final TemplateEngine templateEngine;

    public PdfUtil() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setPrefix("templates/");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
    }

    public String parseThymeleafTemplate(String template, Context context) {
        return templateEngine.process(template, context);
    }

    public byte[] generatePdf(String template, Context context) {
        String html = parseThymeleafTemplate(template, context);
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            ITextRenderer renderer = new ITextRenderer();

            renderer.setDocumentFromString(html);
            renderer.layout();
            renderer.createPDF(outputStream);

            return outputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
        }

        return new byte[0];
    }
}
