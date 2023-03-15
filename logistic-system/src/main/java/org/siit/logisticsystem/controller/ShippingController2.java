//package org.siit.logisticsystem.controller;
//
//import org.siit.logisticsystem.component.CurrentData;
//import org.siit.logisticsystem.entity.Destination;
//import org.siit.logisticsystem.entity.Order;
//import org.siit.logisticsystem.enums.OrderStatus;
//import org.siit.logisticsystem.repository.OrderRepository;
//import org.siit.logisticsystem.service.DeliveryService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.List;
//
//@RestController
//public class ShippingController2 {
//    private final DeliveryService deliveryService;
//    private final OrderRepository orderRepository;
//
//    @Autowired
//    public ShippingController2(DeliveryService deliveryService, OrderRepository orderRepository) {
//        this.deliveryService = deliveryService;
//        this.orderRepository = orderRepository;
//    }
//
//    @PostMapping("/shipping/new-day")
//    public void shippingController() {
//        List<Order> orderList = orderRepository
//                .findByDeliveryDate(new CurrentData().toLocalDate().plusDays(1));
//
//
//        for (Order order : orderList) {
//            order.setStatus(OrderStatus.DELIVERING);
//            orderRepository.save(order);
//        }
//        for (Destination destination : orderRepository.findDestinationsWithDeliveringOrders()) {
//            var orderIds = orderRepository.findDeliveringOrderIdsByDestination(destination.getId());
//            deliveryService.startDeliveries(destination, orderIds);
//        }
//    }
//}
