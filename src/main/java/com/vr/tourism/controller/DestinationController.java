package com.vr.tourism.controller;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.entity.Destination;
import com.vr.tourism.entity.Tag;
import com.vr.tourism.repository.TagRepository;
import com.vr.tourism.service.DestinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/destinations")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class DestinationController {
    private final DestinationService service;
    private final TagRepository tagRepo;
    private final com.vr.tourism.repository.DestinationRepository repo;

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

    @PostMapping("/upload-cover/{id}")
    public ResponseEntity<?> uploadCoverImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            DestinationDTO destinationDTO = service.uploadCoverImage(id, file);
            return ResponseEntity.ok(destinationDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDestination(@RequestBody DestinationDTO dto,
                                               @PathVariable Long id) {
        try{
            DestinationDTO destinationDTO = service.update(dto, id);
            return ResponseEntity.ok(destinationDTO);
        } catch (Exception e) {
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

    @GetMapping("/getCoverBytes/{id}")
    public ResponseEntity<byte[]> getCoverBytes(@PathVariable Long id) throws IOException {
        Destination destination = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Destination với ID: " + id));

        Path filePath = Paths.get(destination.getCover());
        byte[] bytes = Files.readAllBytes(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/webp")
                .body(bytes);
    }



    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        try {
            DestinationDTO destinationDTO = service.delete(id);
            return ResponseEntity.ok("xóa thành công Destination có ID: " + destinationDTO.getId() );
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }
}
