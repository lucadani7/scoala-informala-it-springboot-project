package org.siit.logisticsystem.actuator;

import org.siit.logisticsystem.controller.CurrentData;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;


@Component
public class CompanyInfoContributor implements InfoContributor {

    private final CurrentData currentData;

    public CompanyInfoContributor(CurrentData currentData) {
        this.currentData = currentData;
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("currentDate", currentData.toString());
          //      .withDetail("profit", orderService.calculateProfit())
         // a se decomenta dupa ce Luca va reusi sa faca clasa orderService cu calculatorul de profit si va merge ulterior
    }
}