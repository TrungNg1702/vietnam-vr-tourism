package com.vr.tourism.controller;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.entity.Tag;
import com.vr.tourism.repository.TagRepository;
import com.vr.tourism.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DestinationController {
    private final DestinationService service;
    private final TagRepository tagRepo;

    @PostMapping("/create")
    public ResponseEntity<?> createDestination(@RequestBody DestinationDTO dto) {
        try{
            DestinationDTO destinationDTO = service.create(dto);
            return ResponseEntity.ok(destinationDTO);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        try {
            List<DestinationDTO> destinations = service.getAll();
            return ResponseEntity.ok(destinations);
        } catch (Exception e) {
            e.printStackTrace(); //debugging
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error: " + e.getMessage());
        }
    }


    @GetMapping("/{id}")
    public DestinationDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public DestinationDTO create(@RequestBody DestinationDTO dto) {
        return service.save(dto);
    }

    @GetMapping("/city/{city}")
    public List<DestinationDTO> getByCity(@PathVariable String city) {
        return service.getByCity(city);
    }

    @GetMapping("/with360")
    public List<DestinationDTO> getWith360() {
        return service.getWith360();
    }

    @GetMapping("/search")
    public List<DestinationDTO> search(@RequestParam("name") String name) {
        return service.search(name);
    }

    @GetMapping("/cities")
    public List<String> getCities() {
        return service.getCities();
    }

    @GetMapping("/tags")
    public List<String> getTags() {
        return tagRepo.findAll()
                .stream()
                .map(Tag::getTag)
                .distinct()
                .toList();
    }
}
