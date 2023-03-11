package org.siit.logisticsystem.actuator;

import org.siit.logisticsystem.controller.CurrentData;
import org.siit.logisticsystem.service.OrderService;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;


@Component
public class CompanyInfoContributor implements InfoContributor {

    private final CurrentData currentData;
    private OrderService orderService;

    public CompanyInfoContributor(CurrentData currentData) {
        this.currentData = currentData;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder
                .withDetail("currentDate", currentData.toString())
                .withDetail("profit", orderService.calculateProfit(LocalDate.parse(currentData.toString())));
    }
}