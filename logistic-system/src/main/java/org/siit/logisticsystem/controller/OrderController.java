package org.siit.logisticsystem.controller;

import org.siit.logisticsystem.entity.DoubleList;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("/orders")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/orders/{orderID}")
    public Order getOrderById(@PathVariable long orderID) {
        return orderService.getOrderById(orderID);
    }

    @PostMapping("/orders/add")
    public DoubleList<Order> addOrders(@RequestBody List<Order> newOrders) {
        System.out.println(newOrders);
        return orderService.addOrder(newOrders);
    }
    @PostMapping("/orders/cancel")
    public DoubleList<Long> cancelOrders(@RequestBody List<Long> orderIDs){
        System.out.println(orderIDs);
        return orderService.cancelOrders(orderIDs);
    }

    @PutMapping("/orders/update/{orderID}")
    public Order updateOrder(@RequestBody Order newOrder, @PathVariable long orderID) {
        return orderService.updateOrder(newOrder, orderID);
    }

    @DeleteMapping("/orders/delete/{orderID}")
    public void deleteOrder(@PathVariable long orderID) {
        orderService.deleteOrder(orderID);
    }

}
