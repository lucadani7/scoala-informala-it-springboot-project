package org.siit.logisticsystem.service;

import lombok.AllArgsConstructor;
import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
@AllArgsConstructor
public class ShippingService {
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;
    private final CurrentData currentData;

    public void shipOrdersForNewDay() {

        // get all destinations
        List<Destination> destinations = destinationRepository.findAll();

        for (Destination destination : destinations) {
            // get orders for destination for current date
            List<Order> orders = orderRepository.findByDeliveryDateAndDestinationID(currentData.toLocalDate(), destination);
            List<Integer> ids = new ArrayList<>();
            // set orders status to "Delivering"
            for (Order order : orders) {
                order.setStatus(OrderStatus.DELIVERING);
                ids.add((int) order.getId());
                orderRepository.save(order);
            }
            // call delivery service
            if (!ids.isEmpty()) {
                deliveryService.startDeliveries(destination, ids);
            }
        }

    }
}
