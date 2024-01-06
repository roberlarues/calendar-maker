package es.rcsdevs.calendarmaker.modules.calendar.domain.dto;

import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.SpecialDayTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SpecialDay {
    private int dayOfMonth;
    private int month;
    private SpecialDayTypeEnum type;
    private String name;
    private String description;
}
