package com.vr.tourism.controller;

import com.vr.tourism.entity.Destination;
import com.vr.tourism.repository.TagRepository;
import com.vr.tourism.service.DestinationService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class DestinationController {

    private final DestinationService service;
    private final TagRepository tagRepo;

    @GetMapping("/getAll")
    public List<Destination> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Destination getById(@PathVariable String id) { return service.getById(id); }

    @PostMapping
    public Destination create(@RequestBody Destination dest) { return service.save(dest); }

    @GetMapping("/city/{city}")
    public List<Destination> getByCity(@PathVariable String city) { return service.getByCity(city); }

    @GetMapping("/with360")
    public List<Destination> getWith360() { return service.getWith360(); }

    @GetMapping("/search")
    public List<Destination> search(@RequestParam("name") String name) { return service.search(name); }

    @GetMapping("/cities")
    public List<String> getCities() { return service.getCities(); }

    @GetMapping("/tags")
    public List<String> getTags() {
        return tagRepo.findAll().stream().map(t -> t.getTag()).distinct().toList();
    }
}
