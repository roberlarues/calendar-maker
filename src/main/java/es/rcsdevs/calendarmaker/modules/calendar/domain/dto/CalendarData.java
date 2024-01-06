package es.rcsdevs.calendarmaker.modules.calendar.domain.dto;

import java.util.List;

import lombok.Data;

@Data
public class CalendarData {
    private int year;
    private String calendarDir;
    private List<SpecialDay> specialDays;
    private List<MonthPicture> monthPictures;
}
