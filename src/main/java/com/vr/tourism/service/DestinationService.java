package com.vr.tourism.service;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.entity.Destination;
import com.vr.tourism.mapper.DestinationMapper;
import com.vr.tourism.repository.DestinationRepository;
import lombok.AllArgsConstructor;
import org.hibernate.exception.DataException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class DestinationService {
    private final DestinationRepository repo;
    private final DestinationMapper mapper;

    public List<DestinationDTO> getAll() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    public DestinationDTO getById(Long id) {
        return repo.findById(id).map(mapper::toDTO).orElse(null);
    }

    public List<DestinationDTO> getByCity(String city) {
        return repo.findByCity(city).stream().map(mapper::toDTO).toList();
    }

    public List<DestinationDTO> getWith360() {
        return repo.findByHas360True().stream().map(mapper::toDTO).toList();
    }

    public List<DestinationDTO> search(String keyword) {
        return repo.findByNameContainingIgnoreCaseOrCityContainingIgnoreCase(keyword, keyword)
                .stream().map(mapper::toDTO).toList();
    }

    public DestinationDTO save(DestinationDTO dto) {
        Destination saved = repo.save(mapper.toEntity(dto));
        return mapper.toDTO(saved);
    }

    public List<String> getCities() {
        return repo.findAll().stream()
                .map(Destination::getCity)
                .distinct()
                .toList();
    }

    public DestinationDTO create(DestinationDTO dto) {
        if (dto == null && repo.existsById(dto.getId())){
            throw new IllegalArgumentException("Destination already exists");
        }
        if (repo.existsByName(dto.getName())){
            throw new IllegalArgumentException("Destination already exists");
        }
        Destination destination = mapper.toEntity(dto);
        Destination saved = repo.save(destination);
        return mapper.toDTO(saved);
    }

    public DestinationDTO update(DestinationDTO dto, Long id) {
        if (dto == null) {
            throw new IllegalArgumentException("DestinationDTO không được phép null");
        }

        Destination existingDestination = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay Destination co ID: " + dto.getId()));
        Destination destination = mapper.toEntity(dto);
        destination.setId(existingDestination.getId());

        Destination saved = repo.save(destination);
        return mapper.toDTO(saved);
    }

    public DestinationDTO delete(Long id) {
        Destination destination = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay Destination co ID: " + id));
        repo.delete(destination);
        return mapper.toDTO(destination);
    }

    public DestinationDTO uploadCoverImage(Long id, MultipartFile file) throws IOException {
        if(file.isEmpty()){
            throw new IllegalArgumentException("File cannot be  empty");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")){
            throw new IllegalArgumentException("File must be an image");
        }

        Destination existingDestination = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay Destination co ID: " + id));

        // tao file neu khong co
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

        // duong dan de luu file
        String uploadDir = "uploads/destinations/covers";
        Path uploadPath = Paths.get(uploadDir);

        // tao thu muc anh neu chua ton tai
        if (!Files.exists(uploadPath)){
            Files.createDirectories(uploadPath);
        }

        // Neu co anh cu, xoa
        if (existingDestination.getCover() != null){
            Path oldPath = Paths.get(existingDestination.getCover());
            Files.deleteIfExists(oldPath);
        }

        // Luu moi
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // cap nhat duong dan vao db
        existingDestination.setCover(filePath.toString());
        Destination saved = repo.save(existingDestination);

        return mapper.toDTO(saved);

    }
}
