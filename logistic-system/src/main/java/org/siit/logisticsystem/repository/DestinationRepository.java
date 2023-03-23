package org.siit.logisticsystem.repository;

import org.siit.logisticsystem.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends JpaRepository<Destination, Long> {

    Destination findByName(String name);

}
