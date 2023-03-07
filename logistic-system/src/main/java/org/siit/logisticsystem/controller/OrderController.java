package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.exception.DataNotFoundException;
import org.siit.logisticsystem.exception.DuplicatesNotAllowedException;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class OrderController {
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @GetMapping("/orders/{orderID}")
    public Order getOrderById(@PathVariable long orderID) {
        String messageException = String.format("The order with id %s does not exist!", orderID);
        return orderRepository.findById(orderID).orElseThrow(() -> new DataNotFoundException(messageException));
    }

    @PostMapping("/orders/add")
    public Order addOrder(@RequestBody Order newOrder) {
        String messageException = String.format("The order with id %s is already taken!", newOrder.getId());
        Optional<Order> optionalOrder = orderRepository.findById(newOrder.getId());
        if (optionalOrder.isPresent()) {
            throw new DuplicatesNotAllowedException(messageException);
        }
        return orderRepository.save(newOrder);
    }

    @PutMapping("/orders/update/{orderID}")
    public Order updateOrder(@RequestBody Order newOrder, @PathVariable long orderID) {
        return orderRepository
                .findById(orderID)
                .map(order -> {
                    order.setDestinationID(order.getDestinationID());
                    order.setDeliveryDate(order.getDeliveryDate());
                    order.setStatus(order.getStatus());
                    order.setLastUpdated(order.getLastUpdated());
                    return orderRepository.save(order);
                }).orElseGet(() -> {
                    newOrder.setId(orderID);
                    return orderRepository.save(newOrder);
                });
    }

    public void deleteOrder(long orderID) {
        String messageException = String.format("The order with id %s does not exist!", orderID);
        Optional<Order> optionalOrder = orderRepository.findById(orderID);
        if (optionalOrder.isEmpty()) {
            throw new DataNotFoundException(messageException);
        }
        orderRepository.deleteById(orderID);
    }

}
