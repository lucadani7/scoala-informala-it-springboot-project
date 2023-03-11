package org.siit.logisticsystem.actuator;

import org.siit.logisticsystem.controller.CurrentData;

import org.siit.logisticsystem.service.CompanyService;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;


@Component
public class CompanyInfoContributor implements InfoContributor {
    private final CurrentData currentData;

    private final CompanyService companyService;

    public CompanyInfoContributor(CurrentData currentData, CompanyService companyService) {
        this.currentData = currentData;
        this.companyService = companyService;
    }
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("currentDate", currentData.toString())
                .withDetail("profit", companyService.getProfit());
    }
}