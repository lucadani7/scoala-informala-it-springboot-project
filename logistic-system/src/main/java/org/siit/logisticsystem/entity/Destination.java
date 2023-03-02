package org.siit.logisticsystem.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity(name = "destinations")
public class Destination {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    @NotNull
    @Column(name = "name", unique = true)
    private final String name;
    @NotNull
    @Column(name = "distance")
    @Min(value = 0)
    private final double distance;
}
