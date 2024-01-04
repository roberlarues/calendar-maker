package es.rcsdevs.calendarmaker.modules.pdf.domain;

import java.util.Map;

public interface CalendarService {
    Map<String, Object> generateCalendarData(int year);
}
