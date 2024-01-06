package es.rcsdevs.calendarmaker.modules.calendar.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.rcsdevs.calendarmaker.modules.calendar.domain.constants.Constants;
import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.CalendarData;
import es.rcsdevs.calendarmaker.modules.calendar.domain.dto.SpecialDay;
import es.rcsdevs.calendarmaker.modules.calendar.domain.ports.CalendarExporter;
import es.rcsdevs.calendarmaker.modules.calendar.domain.service.CalendarService;
import es.rcsdevs.calendarmaker.modules.calendar.logic.dto.CurrentMonthData;

@Service
public class CalendarServiceImpl implements CalendarService {

    @Override
    public <T> T createCalendar(CalendarData calendarData, CalendarExporter<T> calendarExporter) {
        Map<String, Object> calendarDataMap = this.generateCalendarData(calendarData);
        return calendarExporter.exportCalendar(calendarDataMap);
    }
    
    private Map<String, Object> generateCalendarData(CalendarData calendarData) {
        Map<String, Object> calendarDataMap = new HashMap<>();

        ArrayList<Map<String, Object>> months = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            Map<String, Object> monthData = getMonthData(calendarData, i);
            months.add(monthData);
        }
        calendarDataMap.put("months", months);
        calendarDataMap.put("calendarDir", calendarData.getCalendarDir());

        return calendarDataMap;
    }

    private Map<String, Object> getMonthData(CalendarData calendarData, int month) {
        Map<String, Object> monthDataMap = new HashMap<>();
        monthDataMap.put("month", Constants.MONTH_NAMES[month]);
        monthDataMap.put("image", calendarData.getMonthPictures().get(month).getFilePath());

        CurrentMonthData monthData = buildCurrentMonthData(calendarData, month);

        ArrayList<Object> weeks = new ArrayList<>();
        for (int w = 0; w < monthData.getNumWeeks(); w++) {
            Map<String, Object> weekData = new HashMap<>();

            ArrayList<Object> days = new ArrayList<>();
            for (int d = 0; d < 7; d++) {
                days.add(getDayData(calendarData, monthData, w, d));
            }

            weekData.put("days", days);
            weeks.add(weekData);
        }
        monthDataMap.put("weeks", weeks);

        return monthDataMap;
    }
    
    private Map<String, Object> getDayData(CalendarData calendarData, CurrentMonthData monthData, int w, int d) {
        Calendar cal = Calendar.getInstance();
        cal.set(calendarData.getYear(), monthData.getMonth(), w * 7 + - monthData.getFirstMonthDayOfWeek() + d + 1);

        Map<String, Object> dayData = new HashMap<>();
        dayData.put("number", cal.get(Calendar.DAY_OF_MONTH));
        dayData.put("height", monthData.getDesignCellHeight());
        int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
        int month = cal.get(Calendar.MONTH);

        SpecialDay specialDay = monthData.getSpecialDayMap().get(cal.get(Calendar.DAY_OF_MONTH));
        if (specialDay != null && month == monthData.getMonth()) {
            dayData.put("type", specialDay.getType().toString().toLowerCase());
            dayData.put("name", specialDay.getName());
            dayData.put("description", specialDay.getDescription());
        }

        if (month != monthData.getMonth()) {
            dayData.put("type", "not-current-month");
        } else if (dayOfWeek > 5 || dayOfWeek == 0) {
            dayData.put("type", "weekend");
        }
        return dayData;
    }

    private CurrentMonthData buildCurrentMonthData(CalendarData calendarData, int monthIndex) {
        CurrentMonthData data = new CurrentMonthData();
        data.setMonth(monthIndex);

        Calendar cal = Calendar.getInstance();
        cal.set(calendarData.getYear(), monthIndex, 1);

        data.setFirstMonthDayOfWeek((cal.get(Calendar.DAY_OF_WEEK) + 5) % 7);
        
        int numWeeksInitial = cal.get(Calendar.WEEK_OF_MONTH);
        cal.set(calendarData.getYear(), monthIndex + 1, 0); // Last day of month
        int numWeeks = cal.get(Calendar.WEEK_OF_MONTH) - numWeeksInitial + 1;
        data.setNumWeeks(numWeeks);
        data.setDesignCellHeight(6f / numWeeks); // 6 = 6cm total table

        Map<Integer, SpecialDay> specialDaysMap = new HashMap<>();
        calendarData.getSpecialDays().forEach(specialDay -> {
            if (specialDay.getMonth() == monthIndex + 1) {
                specialDaysMap.put(specialDay.getDayOfMonth(), specialDay);
            }
        });
        data.setSpecialDayMap(specialDaysMap);

        return data;
    }
}
