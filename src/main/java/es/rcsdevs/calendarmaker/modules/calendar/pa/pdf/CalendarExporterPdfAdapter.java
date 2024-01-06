package es.rcsdevs.calendarmaker.modules.calendar.pa.pdf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.Constants;
import es.rcsdevs.calendarmaker.modules.calendar.domain.ports.CalendarExporter;
import es.rcsdevs.calendarmaker.shared.exception.DataErrorException;

@Service(Constants.EXPORTER_PDF)
public class CalendarExporterPdfAdapter implements CalendarExporter<String> {

    @Autowired
    @Qualifier(Constants.EXPORTER_HTML)
    private CalendarExporter<String> htmlExporter;

    @Override
    public String exportCalendar(Map<String, Object> calendarDataMap) {
        String documentHtml = htmlExporter.exportCalendar(calendarDataMap);
        String outputFile = calendarDataMap.get("calendarDir") + "/output.pdf";
        try (OutputStream os = new FileOutputStream(outputFile)) {
        final String fontPath = calendarDataMap.get("calendarDir") + "/font.ttf";
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.usePdfUaAccessbility(false);
            builder.useFont(new File(fontPath), "TestFont");
            builder.withHtmlContent(documentHtml, "/");
            builder.toStream(os);
            builder.run();
        } catch (Exception e) {
            throw new DataErrorException("Error al procesar la plantilla 2", e);
        }
        return outputFile;
    }
}
