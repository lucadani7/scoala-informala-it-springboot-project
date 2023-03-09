package org.siit.logisticsystem.service;

import java.time.LocalDate;
import java.util.List;

import org.siit.logisticsystem.controller.CurrentData;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfitCalculator {
    private LocalDate date;
    private List<Order> orders;
    private int profit;

    public ProfitCalculator(LocalDate date, List<Order> orders) {
        this.date = date;
        this.orders = orders;
        this.profit = 0;
    }
    @Autowired
    private OrderRepository orderRepository;

    public int calculateProfitForDay(CurrentData day) {
        List<Order> completedOrders = OrderRepository.findByDeliveryDateAndStatus(day, OrderStatus.DELIVERED);
        int profit = completedOrders.stream()
                .mapToInt( Destination::getDistance)
                .sum();
        return profit;
    }
}