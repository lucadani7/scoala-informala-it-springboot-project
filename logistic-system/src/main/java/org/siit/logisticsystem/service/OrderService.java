package org.siit.logisticsystem.service;

import org.siit.logisticsystem.component.CurrentData;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.DoubleList;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.exception.DataNotFoundException;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private final OrderRepository orderRepository;
    @Autowired
    private DestinationRepository destinationRepository;
    @Autowired
    private CurrentData currentData;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(long orderID) {
        String message = String.format("The order with id %s does not exist!", orderID);
        return orderRepository.findById(orderID).orElseThrow(() -> new DataNotFoundException(message));
    }

    public DoubleList<Order> addOrder(List<Order> newOrders) {
        DoubleList<Order> doubleList = new DoubleList<Order>();

        for (Order order : newOrders) {
            Destination destinationFromOrder = order.getDestinationID();
            Optional<Destination> destinationFromDb = destinationRepository.findById(order.getDestinationID().getId());

            if (order.getDeliveryDate().isBefore(LocalDate.now()) || destinationFromDb.isEmpty() ||
                    !destinationFromDb.get().equals(destinationFromOrder)) {
                doubleList.addInFailed(order);
            } else {
                doubleList.addInAdded(orderRepository.save(order));
            }
        }
        return doubleList;

    }

    public DoubleList<Long> cancelOrders(List<Long> orderIDs) {
        DoubleList<Long> doubleList = new DoubleList<>();

        for (Long id : orderIDs) {
            if (orderRepository.findById(id).isEmpty() ||
                    orderRepository.findById(id).get().getStatus().equals(OrderStatus.DELIVERED)) {
                // INVALID ID OR ORDER DELIVERED
                doubleList.addInFailed(id);
                continue;
            }
            doubleList.addInAdded(id);
            Order order = orderRepository.findById(id).get();
            order.setStatus(OrderStatus.CANCELLED);
            orderRepository.save(order);
        }
        return doubleList;
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

    public List<Order> getOrdersByDateAndDestinationName(LocalDate date, String destinationName) {
        if (date == null) {
            date = currentData.toLocalDate();
        }
        if (destinationName == null) {
            return orderRepository.findByDeliveryDate(date);
        }
        Destination existingDestination = destinationRepository.findByName(destinationName);
        if (existingDestination == null) {
            throw new DataNotFoundException("Destination " + destinationName + " was not found in database.");
        }
        return orderRepository.findByDeliveryDateAndDestinationID(date, existingDestination);

    }
}
