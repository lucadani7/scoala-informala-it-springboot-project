package org.siit.logisticsystem.service;


import jakarta.transaction.Transactional;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.exception.DataNotFoundException;
import org.siit.logisticsystem.exception.DuplicatesNotAllowedException;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DestinationService {

    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;
    @Autowired
    public DestinationService(DestinationRepository destinationRepository,  OrderRepository orderReqpository) {
        this.destinationRepository = destinationRepository;
        this.orderRepository = orderReqpository;
    }

    public void saveDestination(Destination destination) {
        if (destinationRepository.existsById(destination.getId())) {
            throw new DuplicatesNotAllowedException("The destination already exists!");
        }
        destinationRepository.save(destination);
    }

    public void update(Destination destination) {
        Optional<Destination> optionalDestination = destinationRepository.findById(destination.getId());
        if (optionalDestination.isEmpty()) {
            throw new DataNotFoundException(String.format("The destination with id %s does not exist!", destination.getId()));
        }
        List<Order> orderList = orderRepository.findAllByDestinationID(optionalDestination.get());
        for (Order order:orderList){
            order.setDestinationID(destination);
            orderRepository.save(order);
        }
        destinationRepository.save(destination);
    }

    public void deleteDestination(Long id) {
        Optional<Destination> optionalDestination = destinationRepository.findById(id);
        if (optionalDestination.isEmpty()) {
            throw new DataNotFoundException(String.format("The destination with id %s does not exist!", id));
        }
        List<Order> orderList = orderRepository.findAllByDestinationID(optionalDestination.get());
        for (Order order:orderList){
            orderRepository.deleteById(order.getId());
        }
        destinationRepository.deleteById(id);
    }

    public Destination getDestinationById(Long id){
        String message = String.format("The destination with id %s does not exist!", id);
        return destinationRepository.findById(id).orElseThrow(() -> new DataNotFoundException(message));
    }

    public List<Destination> findAll(){
        return destinationRepository.findAll();
    }

}
