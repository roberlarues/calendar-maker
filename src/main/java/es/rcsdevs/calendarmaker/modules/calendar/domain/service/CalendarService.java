package es.rcsdevs.calendarmaker.modules.calendar.domain.service;

import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.CalendarData;
import es.rcsdevs.calendarmaker.modules.calendar.domain.ports.CalendarExporter;

public interface CalendarService {

    <T> T createCalendar(CalendarData calendarData, CalendarExporter<T> calendarExporter);
}
