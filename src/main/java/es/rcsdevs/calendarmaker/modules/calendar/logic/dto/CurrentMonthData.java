package es.rcsdevs.calendarmaker.modules.calendar.logic.dto;

import java.util.Map;

import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.SpecialDay;
import lombok.Data;

@Data
public class CurrentMonthData {
    private int month;
    private int firstMonthDayOfWeek;
    private int numWeeks;
    private float designCellHeight;

    private Map<Integer, SpecialDay> specialDayMap;
}
