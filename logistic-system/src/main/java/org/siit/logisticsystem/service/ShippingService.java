package org.siit.logisticsystem.service;

import lombok.AllArgsConstructor;
import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class ShippingService {
    private final DeliveryService deliveryService;
    private final OrderRepository orderRepository;
    private final DestinationRepository destinationRepository;
    private final CurrentData currentData;

    private final Logger logger = LoggerFactory.getLogger(ShippingService.class);

    public void shipOrdersForNewDay() {

        // get all destinations
        List<Destination> destinations = destinationRepository.findAll();


        Map<Destination, List<Integer>> ordersByDestination = new HashMap<>();

        StringBuilder destinationsToday = new StringBuilder();

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

            if (!ids.isEmpty()) {
                ordersByDestination.put(destination, ids);
                destinationsToday.append(destination.getName()).append(", ");
            }
        }

        logger.info("Today we will be delivering to {}", destinationsToday);

        // call delivery service for each destination
        for(Destination destination : ordersByDestination.keySet()) {
            deliveryService.startDeliveries(destination, ordersByDestination.get(destination));
        }
    }
}
