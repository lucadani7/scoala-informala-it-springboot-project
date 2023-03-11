package org.siit.logisticsystem.service;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProfitCalculator {
    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;


    @Autowired
    public ProfitCalculator(OrderRepository orderRepository, DestinationRepository destinationRepository) {
        this.orderRepository = orderRepository;
        this.destinationRepository = destinationRepository;
    }

    public BigDecimal calculateProfit(LocalDate date) {
        List<Order> orders = orderRepository.findAllByDeliveryDateAndStatus(date, OrderStatus.DELIVERED);
        var profit = BigDecimal.ZERO;
        for (Order order : orders) {
            Destination destination = destinationRepository.findById(order.getDestinationID().getId()).orElseThrow();
            var deliveryProfit = BigDecimal.valueOf(destination.getDistance());
            profit = profit.add(deliveryProfit);
        }
        return profit;
    }
}
