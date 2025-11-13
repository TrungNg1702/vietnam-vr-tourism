package com.vr.tourism.repository;

import com.vr.tourism.entity.Destination;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Long> {
    List<Destination> findByCity(String city);
    List<Destination> findByHas360True();
    List<Destination> findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(String name, String city);

    boolean existsByName(String name);
}
