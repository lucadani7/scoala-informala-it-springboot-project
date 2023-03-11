package org.siit.logisticsystem.service;
import org.siit.logisticsystem.entity.Destination;
import org.siit.logisticsystem.entity.Order;
import org.siit.logisticsystem.enums.OrderStatus;
import org.siit.logisticsystem.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class DeliveryService {
   private final Logger logger = LoggerFactory.getLogger(DeliveryService.class);
   private final OrderRepository orderRepository;
   private final CompanyService companyService;

    public DeliveryService(OrderRepository orderRepository, CompanyService companyService) {
        this.orderRepository = orderRepository;
        this.companyService = companyService;
    }
    @Async
    public void startDeliveries(Destination destination, List<Integer> orderIds) {
        logger.info("Starting {} deliveries for {} on Thread {} for {} km",orderIds.size(), destination.getName(),
                Thread.currentThread().getName(), destination.getDistance());
        try {
            Thread.sleep((long) destination.getDistance() * 1000);
            for (Integer orderID : orderIds) {
                Optional<Order> orderOp = orderRepository.findById(orderID.longValue());
                if (orderOp.isPresent()) {
                    Order order = orderOp.get();
                    if (order.getStatus() != OrderStatus.CANCELLED) {
                        order.setStatus(OrderStatus.DELIVERED);
                        orderRepository.save(order);
                        companyService.updateProfit(destination.getDistance());
                    }
                }
            }
            logger.info("{} deliveries completed for {}",orderIds.size(), destination.getName());
        } catch (InterruptedException e) {
            logger.error("The delivery to {} has failed.", destination.getName());
        }
    }
}
