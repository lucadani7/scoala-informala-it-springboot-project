package org.siit.logisticsystem.component;

import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class CurrentData {
    private Calendar calendar = new Calendar
            .Builder()
            .setDate(2021, Calendar.DECEMBER, 14)
            .build();

    public Calendar getCalendar() {
        return calendar;
    }
}
