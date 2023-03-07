package org.siit.logisticsystem.actuator;

import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;


@Component
public class CompanyInfoContributor implements InfoContributor {

    private final OrderService orderService;

    public CompanyInfoContributor(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("currentDate", orderService.getCurrentDate())
                .withDetail("profit", orderService.calculateProfit());
    }
}