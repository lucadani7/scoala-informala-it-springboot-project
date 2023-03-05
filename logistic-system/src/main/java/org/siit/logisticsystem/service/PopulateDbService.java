package org.siit.logisticsystem.service;

import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.DestinationRepository;
import org.siit.logisticsystem.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Service
public class PopulateDbService {
    private final DestinationRepository destinationRepository;
    private final OrderRepository orderRepository;

    private final Logger logger = LoggerFactory.getLogger(PopulateDbService.class);
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public PopulateDbService(DestinationRepository destinationRepository, OrderRepository orderRepository) {
        this.destinationRepository = destinationRepository;
        this.orderRepository = orderRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        logger.info("Populating database with initial data");
        populateDestination();
        populateOrder();
    }

    private void populateDestination() {

        List<Destination> destinationList = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get("src/main/resources/files/destinations.csv"))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] s = line.split(",");
                Destination destination = new Destination();
                destination.setName(s[0]);
                destination.setDistance(Double.parseDouble(s[1]));
                destinationList.add(destination);
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }
        destinationRepository.saveAll(destinationList);
        logger.info("Destinations successfully inserted into database.");
    }

    private void populateOrder() {
        List<Order> orderList = new ArrayList<>();

        try (Scanner scanner = new Scanner(Paths.get("src/main/resources/files/orders.csv"))) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] s = line.split(",");

                String destinationName = s[0];
                // get destination from DB
                Destination destination = destinationRepository.findByName(destinationName);

                Order order = new Order();
                order.setDestinationID(destination);
                order.setLastUpdated(LocalDateTime.now());
                order.setDeliveryDate(LocalDate.parse(s[1], dateTimeFormatter));
                order.setStatus(OrderStatus.NEW);
                orderList.add(order);
            }
        } catch (IOException e) {
            logger.error("Error reading file: {}", e.getMessage());
        }

        orderRepository.saveAll(orderList);
        logger.info("Orders successfully inserted into database.");
    }

}
