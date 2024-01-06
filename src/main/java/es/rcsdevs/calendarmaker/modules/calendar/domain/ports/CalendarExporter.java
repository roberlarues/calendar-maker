package es.rcsdevs.calendarmaker.modules.calendar.domain.ports;

import java.util.Map;

public interface CalendarExporter<T> {
    T exportCalendar(Map<String, Object> calendarDataMap);
}
