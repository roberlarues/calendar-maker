package es.rcsdevs.calendarmaker.modules.calendar.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MonthPicture {
    private int month;
    private String filePath;
}
