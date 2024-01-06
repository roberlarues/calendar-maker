package es.rcsdevs.calendarmaker.modules.calendar;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.Constants;
import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.SpecialDayTypeEnum;
import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.CalendarData;
import es.rcsdevs.calendarmaker.modules.calendar.domain.ports.CalendarExporter;
import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.MonthPicture;
import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.SpecialDay;
import es.rcsdevs.calendarmaker.modules.calendar.domain.service.CalendarService;

@SpringBootTest
public class CalendarServiceTest {

    @Autowired
    private CalendarService calendarService;

    @Autowired
    @Qualifier(Constants.EXPORTER_HTML)
    private CalendarExporter<String> calendarHtmlExporter;

    @Autowired
    @Qualifier(Constants.EXPORTER_PDF)
    private CalendarExporter<String> calendarPdfExporter;

    private CalendarData calendarData;

    @BeforeEach
    void setup() {
        this.calendarData = new CalendarData();
        calendarData.setYear(2024);

        List<SpecialDay> specialDays = new ArrayList<>();
        specialDays.add(new SpecialDay(1, 1, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Año nuevo", null));
        specialDays.add(new SpecialDay(6, 1, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Reyes magos", "Epifanía del señor"));
        specialDays.add(new SpecialDay(17, 1, SpecialDayTypeEnum.BIRTHDAY, "Cumple Rober", null));
        specialDays.add(new SpecialDay(5, 3, SpecialDayTypeEnum.LOCAL_HOLIDAY, "Cincomarzada", null));
        specialDays.add(new SpecialDay(29, 3, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Viernes santo", null));
        specialDays.add(new SpecialDay(4, 4, SpecialDayTypeEnum.BIRTHDAY, "Cumple Isa", null));
        specialDays.add(new SpecialDay(1, 5, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Día del trabajo", null));
        specialDays.add(new SpecialDay(15, 8, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Asunción de la Virgen", null));
        specialDays.add(new SpecialDay(12, 10, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Fiesta nacional de España", "El pilar"));
        specialDays.add(new SpecialDay(1, 11, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Todos los santos",  null));
        specialDays.add(new SpecialDay(6, 12, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Día de la Constitución",  null));
        specialDays.add(new SpecialDay(25, 12, SpecialDayTypeEnum.NATIONAL_HOLIDAY, "Navidad", null));
        calendarData.setSpecialDays(specialDays);

        String calendarDir = Paths.get("").toAbsolutePath().toString().replace("\\", "/")
                + "/src/main/resources/templates/pdf/calendar";
        calendarData.setCalendarDir(calendarDir);

        String imageEn = "file:" + calendarDir +"/En.jpg";
        String imageFeb = "file:" + calendarDir +"/Feb.JPG";
        String imageMar = "file:" + calendarDir +"/Mar.JPG";
        String image = "file:" + calendarDir +"/sheeps.JPG";
        List<MonthPicture> monthPictures = new ArrayList<>();
        monthPictures.add(new MonthPicture(1, imageEn));
        monthPictures.add(new MonthPicture(2, imageFeb));
        monthPictures.add(new MonthPicture(3, imageMar));
        monthPictures.add(new MonthPicture(4, image));
        monthPictures.add(new MonthPicture(5, image));
        monthPictures.add(new MonthPicture(6, image));
        monthPictures.add(new MonthPicture(7, image));
        monthPictures.add(new MonthPicture(8, image));
        monthPictures.add(new MonthPicture(9, image));
        monthPictures.add(new MonthPicture(10, image));
        monthPictures.add(new MonthPicture(11, image));
        monthPictures.add(new MonthPicture(12, image));
        calendarData.setMonthPictures(monthPictures);
    }
    
    @Test
    void testGenerateCalendarHtml() {
        String  calendarHtml = calendarService.createCalendar(calendarData, calendarHtmlExporter);
        assertNotNull(calendarHtml);
        assertTrue(calendarHtml.contains("Enero"));
    }
    
    @Test
    void testGenerateCalendarPdf() {
        String  calendarHtml = calendarService.createCalendar(calendarData, calendarPdfExporter);
        assertNotNull(calendarHtml);
    }
}
