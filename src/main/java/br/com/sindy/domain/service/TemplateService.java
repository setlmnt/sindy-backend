package br.com.sindy.domain.service;

import br.com.sindy.domain.entity.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

public interface TemplateService {
    Template getTemplate(String templateName);

    StringWriter processTemplate(Map<String, Object> templateVariables, Template emailTemplate) throws IOException, TemplateException;

    String getProcessedTemplate(String templateId, Map<String, Object> templateVariables);
}
