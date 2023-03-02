package org.siit.logisticsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import org.siit.logisticsystem.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private final Destination destinationID;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "delivery_date")
    private final LocalDate deliveryDate;
    @NotNull
    @Column(name = "status")
    private final OrderStatus status;
    @NotNull
    @Column(name = "last_updated")
    private final LocalDateTime lastUpdated;
}
