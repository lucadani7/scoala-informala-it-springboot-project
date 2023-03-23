package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.service.ShippingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.format.DateTimeFormatter;

@RestController
public class ShippingController {
    private final CurrentData currentData;

    private final ShippingService shippingService;

    private final Logger logger = LoggerFactory.getLogger(ShippingController.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Autowired
    public ShippingController(CurrentData currentData, ShippingService shippingService) {
        this.currentData = currentData;
        this.shippingService = shippingService;
    }

    @PostMapping("/shipping/new-day")
    public String shippingController() {
        currentData.advanceDay();
        logger.info("New day starting: {}", currentData.toLocalDate().format(dateTimeFormatter) );
        shippingService.shipOrdersForNewDay();
        return "New day starting: " + currentData.toLocalDate().format(dateTimeFormatter);
    }
}
