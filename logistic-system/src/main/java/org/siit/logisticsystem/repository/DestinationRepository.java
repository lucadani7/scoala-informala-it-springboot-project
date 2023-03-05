package org.siit.logisticsystem.repository;

import org.siit.logisticsystem.entity.Destination;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestinationRepository extends CrudRepository<Destination, Long> {

    Destination findByName(String name);

}
