package org.siit.logisticsystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.GregorianCalendar;

// TODO CurrentData should not be a controller, should be a component
@RestController
public class CurrentData {
    private Calendar calendar = new Calendar
            .Builder()
            .setDate(2021, Calendar.DECEMBER, 14)
            .build();

    public Calendar getCalendar() {
        return calendar;
    }

    // TODO no need for the code below, we already have the actuator
    @GetMapping("/")
    public String toString() {
        var gregorianCalendar = (GregorianCalendar) getCalendar();
        LocalDate ourDate = gregorianCalendar.toZonedDateTime().toLocalDate();
        return "Current data: " + ourDate.toString();
    }
}
