package com.vr.tourism.service;

import com.vr.tourism.entity.Destination;
import com.vr.tourism.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationRepository repo;


    public List<Destination> getAll() {
        return repo.findAll();
    }
    public Destination getById(String id) {
        return repo.findById(id).orElse(null);
    }
    public List<Destination> getByCity(String city) {
        return repo.findByCity(city);
    }
    public List<Destination> getWith360() {
        return repo.findByHas360True();
    }
    public List<Destination> search(String name) {
        return repo.findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(name,name);
    }
    public Destination save(Destination d) {
        return repo.save(d);
    }
    public List<String> getCities() {
        return repo.findAll().stream().map(Destination::getCity).distinct().toList();
    }

}
