package es.rcsdevs.calendarmaker.modules.pdf.logic;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.rcsdevs.calendarmaker.modules.pdf.domain.PdfService;

@SpringBootTest
public class PdfServiceTest {

    @Autowired
    private PdfService pdfService;

    @Test
    void testGeneratePdfHtml() {
        pdfService.generatePdfHtml(2024);
        assertTrue(true);
    }
}
