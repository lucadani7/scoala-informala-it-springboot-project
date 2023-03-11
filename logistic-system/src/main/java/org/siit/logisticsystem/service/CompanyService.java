package org.siit.logisticsystem.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {
    private final Logger logger = LoggerFactory.getLogger(CompanyService.class);
    private double profit = 0;
    public double getProfit() {
        return profit;
    }
    public synchronized void updateProfit(double orderProfit) {
        profit += orderProfit;
        logger.info("The profit was updated. Company profit = {}", profit);
    }

}
