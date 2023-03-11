package org.siit.logisticsystem.repository;

import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {

    default List<Order> findByDeliveryDateAndStatus(LocalDate day, OrderStatus status) {
        List<Order> newList = new ArrayList<>();
        for (var elem : findAll()) {
            if (elem.getDeliveryDate() == day && elem.getStatus().equals(status)) {
                newList.add(elem);
            }
        }
        return newList;
    }
}
