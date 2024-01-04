package es.rcsdevs.calendarmaker.modules.pdf.logic;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import es.rcsdevs.calendarmaker.modules.pdf.domain.CalendarService;

@Service
public class CalendarServiceImpl implements  CalendarService {
    
    public Map<String, Object> generateCalendarData(int year) {
        Map<String, Object> map = new HashMap<>();

        ArrayList<Map<String, Object>> months = new ArrayList<>();

        Map<String, Object> festivos = new HashMap<>();
        festivos.put("25/12", true);
        festivos.put("05/03", true);

        String[] monthNames = new String[] {
                "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre",
                "Noviembre", "Diciembre"
        };

        Calendar cal = Calendar.getInstance();

        for (int i = 0; i < 12; i++) {
            Map<String, Object> monthData = new HashMap<>();
            monthData.put("month", monthNames[i]);
            monthData.put("image", "sheeps.JPG");

            cal.set(year, i, 1);

            ArrayList<Object> weeks = new ArrayList<>();

            int firstMonthDayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 5) % 7;
            int numWeeksInitial = cal.get(Calendar.WEEK_OF_MONTH);
            cal.set(year, i + 1, 0);
            int numWeeks = cal.get(Calendar.WEEK_OF_MONTH) - numWeeksInitial + 1;

            for (int w = 0; w < numWeeks; w++) {
                Map<String, Object> weekData = new HashMap<>();

                ArrayList<Object> days = new ArrayList<>();
                for (int j = 0; j < 7; j++) {
                    cal.set(year, i, w * 7 + - firstMonthDayOfWeek + j + 1);
                    days.add(getDayData(cal, i, festivos, numWeeks));
                }

                weekData.put("days", days);
                weeks.add(weekData);
            }

            monthData.put("weeks", weeks);
            months.add(monthData);
        }
        map.put("months", months);

        return map;
    }
    
    private Map<String, Object> getDayData(Calendar cal, int currentMonth, Map<String, Object> festivos, Integer numWeeks) {

        float cellHeight = 6f / numWeeks; // 6 = 6cm total tabla

        Map<String, Object> dayData = new HashMap<>();
        dayData.put("number", cal.get(Calendar.DAY_OF_MONTH));
        dayData.put("height", cellHeight);
        int dayOfWeek = (cal.get(Calendar.DAY_OF_WEEK) + 6) % 7;
        int month = cal.get(Calendar.MONTH);
        if (month != currentMonth) {
            dayData.put("type", "not-current-month");
        } else if (dayOfWeek > 5 || dayOfWeek == 0) {
            dayData.put("type", "finde");
        } else if (festivos.containsKey(cal.get(Calendar.DAY_OF_MONTH) + "/" + month)) {
            dayData.put("type", "festivo");
        }
        return dayData;
    }
}
