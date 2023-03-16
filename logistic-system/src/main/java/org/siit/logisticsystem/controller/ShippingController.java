package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.service.ShippingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
    private final CurrentData currentData;

    private final ShippingService shippingService;

    @Autowired
    public ShippingController(CurrentData currentData, ShippingService shippingService) {
        this.currentData = currentData;
        this.shippingService = shippingService;
    }

    @PostMapping("/shipping/new-day")
    public void shippingController() {
        currentData.advanceDay();
        shippingService.shipOrdersForNewDay();
    }
}
