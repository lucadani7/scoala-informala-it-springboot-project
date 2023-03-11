package org.siit.logisticsystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.siit.logisticsystem.enums.OrderStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "destination_id", nullable = false)
    private Destination destinationID;
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "delivery_date")
    private LocalDate deliveryDate;
    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @NotNull
    @Column(name = "last_updated")
    private LocalDateTime lastUpdated;
}
