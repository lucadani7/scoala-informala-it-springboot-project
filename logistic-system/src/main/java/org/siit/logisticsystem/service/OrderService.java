package org.siit.logisticsystem.service;


import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.exception.DataNotFoundException;
import org.siit.logisticsystem.exception.DuplicatesNotAllowedException;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private DestinationRepository destinationRepository;
    private CurrentData date;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    public Order getOrderById(long orderID) {
        String message = String.format("The order with id %s does not exist!", orderID);
        return orderRepository.findById(orderID).orElseThrow(() -> new DataNotFoundException(message));
    }

    public Order addOrder(Order newOrder) {
        if (orderRepository.existsById(newOrder.getId())) {
            throw new DuplicatesNotAllowedException(String.format("The order with id %s is already taken!", newOrder.getId()));
        }
        return orderRepository.save(newOrder);
    }

    public Order updateOrder(Order newOrder, long orderID) {
        return orderRepository.findById(orderID)
                .map(order -> {
                    order.setDestinationID(order.getDestinationID());
                    order.setDeliveryDate(order.getDeliveryDate());
                    order.setStatus(order.getStatus());
                    order.setLastUpdated(order.getLastUpdated());
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(orderID);
                    return orderRepository.save(newOrder);
                });
    }

    public void deleteOrder(long orderID) {
        if (!orderRepository.existsById(orderID)) {
            throw new DataNotFoundException(String.format("The order with id %s does not exist!", orderID));
        }
        orderRepository.deleteById(orderID);
    }

    public BigDecimal calculateProfit() {
        ProfitCalculator profitCalculator = new ProfitCalculator(orderRepository, destinationRepository);
        return profitCalculator.calculateProfit(LocalDate.parse(date.getCalendar().toString()));
    }
}
