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
    List<Order> findAllByDestinationID(Destination destinationId);

    List<Order> findByDeliveryDateAndDestinationID(LocalDate date, Destination destination);

    List<Order> findByDeliveryDateAndDestinationID_name(LocalDate date, String destinationName);


    default List<Order> findByDeliveryDate(LocalDate date) {
        List<Order> newList = findAll();
        return newList
                .stream()
//                .filter(elem -> elem.getDeliveryDate() == date)
                .filter(elem -> elem.getDeliveryDate().equals(date))
                .toList();
    }

    default List<Order> findAllByDeliveryDateAndStatus(LocalDate day, OrderStatus status) {
        List<Order> newList = findAll();
        return newList
                .stream()
                .filter(elem -> elem.getDeliveryDate() == day && elem.getStatus().equals(status))
                .toList();
    }


}
