package org.siit.logisticsystem.repository;

import org.siit.logisticsystem.algorithm.IterableConverter;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    //o fac manual
    List<Order> findAllByDestinationID(Optional<Destination> destinationId);


    default List<Destination> findDestinationsWithDeliveringOrders() {
        List<Order> newList = (List<Order>) findAll();
        List<Order> deliveringOrders = newList
                .stream()
                .filter(elem -> elem.getStatus().equals(OrderStatus.DELIVERING))
                .toList();
        Map<Destination, List<Order>> ordersByDestination = new HashMap<>();
        for (Order order : deliveringOrders) {
            Destination destination = order.getDestinationID();
            List<Order> ordersForDestination = ordersByDestination.getOrDefault(destination, new ArrayList<>());
            ordersForDestination.add(order);
            ordersByDestination.put(destination, ordersForDestination);
        }
        return ordersByDestination.keySet().stream().toList();
    }

    default List<Order> findByDeliveryDate(LocalDate date) {
        List<Order> newList = (List<Order>) findAll();
        return newList
                .stream()
                .filter(elem -> elem.getDeliveryDate() == date)
                .toList();
    }

    default List<Order> findAllByDeliveryDateAndStatus(LocalDate day, OrderStatus status) {
        List<Order> newList = (List<Order>) findAll();
        return newList
                .stream()
                .filter(elem -> elem.getDeliveryDate() == day && elem.getStatus().equals(status))
                .toList();
    }

    default List<Integer> findDeliveringOrderIdsByDestination(long orderIds) {
        List<Long> ordersIdsToLong = new ArrayList<>(List.of(orderIds));
        Long[] array = ordersIdsToLong.toArray(Long[]::new);
        var iterable = new IterableConverter().iteratorToIterable(Arrays.stream(array).iterator());
        List<Order> orderList = (List<Order>) findAllById(iterable);
        Map<Destination, List<Integer>> orderIdsByDestination = new HashMap<>();
        for (Order order : orderList) {
            Destination destination = order.getDestinationID();
            List<Integer> destinationOrderIds = orderIdsByDestination.computeIfAbsent(destination, k -> new ArrayList<>());
            destinationOrderIds.add((int) order.getId());
        }
        return new ArrayList<>(orderIdsByDestination.values().size());
    }
}
