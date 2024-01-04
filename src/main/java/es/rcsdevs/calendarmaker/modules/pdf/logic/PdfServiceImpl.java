package es.rcsdevs.calendarmaker.modules.pdf.logic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.io.FileTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import es.rcsdevs.calendarmaker.modules.pdf.domain.CalendarService;
import es.rcsdevs.calendarmaker.modules.pdf.domain.PdfService;
import es.rcsdevs.calendarmaker.shared.exception.DataErrorException;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PdfServiceImpl implements PdfService {

    @Autowired
    private CalendarService calendarService;

    /**
     * Genera el documento PDF en 2 pasos:
     * - Genera el contenido del documento dada la plantilla y los datos de usuario a
     * través de Handlebars.java
     * - Exporta el HTML resultante a PDF a través de Openpdftohtml
     */
    @Override
    public void generatePdfHtml(int year) {

        long initial = System.currentTimeMillis();
        String documentHtml = buildDocumentHtml("calendar", year);
        long finalT = System.currentTimeMillis();
        log.info("Tiempo ejecucion handlebars ms: {}", finalT - initial);

        long initial2 = System.currentTimeMillis();
        try (OutputStream os = new FileOutputStream(getBaseUrl() + "/output.pdf")) {
            exportToPdf(documentHtml, os);
        } catch (Exception e) {
            throw new DataErrorException("Error al procesar la plantilla 2", e);
        }
        long finalT2 = System.currentTimeMillis();
        log.info("Tiempo ejecucion openhtmltopdf ms: {}", finalT2 - initial2);
    }



    private String buildDocumentHtml(String templateId, int year) {
        String content = "";
        try {
            Template template = loadTemplate(templateId);
            Map<String, Object> mapping = calendarService.generateCalendarData(year);
            content = template.apply(mapping);
        } catch (IOException e) {
            throw new RuntimeException("error", e);
        }

        return content;
    }

    private Template loadTemplate(String name) throws IOException {
        TemplateLoader loader = new FileTemplateLoader("");
        loader.setPrefix(getBaseUrl());
        loader.setSuffix(".html");

        Handlebars handlebars = new Handlebars(loader);
        Template template = handlebars.compile(name);

        return template;
    }

    private void exportToPdf(String documentHtml, OutputStream os) throws IOException {
        final String fontPath = getBaseUrl() + "/fonts/Lato-Regular.ttf";
        PdfRendererBuilder builder = new PdfRendererBuilder();
        builder.useFastMode();
        builder.usePdfUaAccessbility(false);
        builder.useFont(new File(fontPath), "TestFont");
        builder.withHtmlContent(documentHtml, "/");
        builder.toStream(os);
        builder.run();
    }

    private String getBaseUrl() {
        String base = "";
        base = Paths.get("").toAbsolutePath().toString().replace("\\", "/")
                + "/src/main/resources/templates/pdf/calendar";
        return base;
    }

}
