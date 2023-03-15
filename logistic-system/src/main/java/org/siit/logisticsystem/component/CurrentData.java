package org.siit.logisticsystem.component;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

@Component
public class CurrentData {
    private Calendar calendar = new Calendar
            .Builder()
            .setDate(2021, Calendar.DECEMBER, 14)
            .build();

    public Calendar getCalendar() {
        return calendar;
    }

    public LocalDate toLocalDate() {
        ZoneId zoneId = ZoneId.systemDefault();
        Date date = getCalendar().getTime();
        return date.toInstant().atZone(zoneId).toLocalDate();
    }

    public void advanceDay() {
        calendar.add(Calendar.DAY_OF_MONTH,1);
    }
}
