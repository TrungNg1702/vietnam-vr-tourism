package com.vr.tourism.service;

import com.vr.tourism.dto.DestinationDTO;
import com.vr.tourism.dto.SceneDTO;
import com.vr.tourism.entity.Destination;
import com.vr.tourism.entity.Scene;
import com.vr.tourism.mapper.SceneMapper;
import com.vr.tourism.repository.DestinationRepository;
import com.vr.tourism.repository.SceneRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@AllArgsConstructor
public class SceneService {

    private final SceneRepository repo;
    private final SceneMapper mapper;
    private final DestinationRepository destinationRepository;

    public SceneDTO create(SceneDTO dto) {
        try {
            Destination destination = destinationRepository.findById(dto.getDestinationId())
                    .orElseThrow(() -> new RuntimeException("Destination not found"));

            Scene scene = mapper.toEntity(dto); // gan gia tri tu DTO vao Entity
            scene.setDestination(destination);
            Scene saved = repo.save(scene);
            return mapper.toDTO(saved);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public SceneDTO update(SceneDTO dto, String id) {
        try{
            if (dto == null) {
                throw new RuntimeException("Gia tri nhap vao khong duoc de trong");
            }

            Scene existingScene = repo.findById(id)
                    .orElseThrow(() -> new RuntimeException("Scene not found"));
            Scene scene = mapper.toEntity(dto);
            scene.setId(existingScene.getId());
            if (dto.getDestinationId() != null) {
                Destination destination = destinationRepository.findById(dto.getDestinationId())
                        .orElseThrow(() -> new RuntimeException("Destination not found"));
                scene.setDestination(destination);
            }else {
                scene.setDestination(existingScene.getDestination());
            }

            if (dto.getHotspots() != null) {
                scene.getHotspots().forEach(h -> h.setScene(scene));
            }

            Scene saved = repo.save(scene);
            return mapper.toDTO(saved);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException(ex.getMessage());
        }
    }

    public SceneDTO getById(String id) {
        return repo.findById(id)
                .map(mapper::toDTO)
                .orElse(null);
    }

    public List<SceneDTO> getAll() {
       return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    public SceneDTO uploadPanoImg(String id, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File cannot be empty");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            Scene existingScene = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy Scene với ID: " + id));

            // Tạo tên file mới luôn khác
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileName = fileName.replaceAll("\\s+", "_");

            // Thư mục lưu file
            String uploadDir = "uploads/scenes/pano";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Xóa file cũ nếu có
            if (existingScene.getPanoUrl() != null) {
                Path oldPath = Paths.get(existingScene.getPanoUrl());
                try {
                    Files.deleteIfExists(oldPath);
                } catch (IOException e) {
                    // Log lỗi nhưng không throw
                    System.err.println("Không xóa được file cũ: " + oldPath + " vì " + e.getMessage());
                }
            }

            // Lưu file mới với try-with-resources đảm bảo stream được đóng
            Path filePath = uploadPath.resolve(fileName);
            try (InputStream in = file.getInputStream()) {
                Files.copy(in, filePath, StandardCopyOption.REPLACE_EXISTING);
            }

            // Cập nhật đường dẫn file mới vào DB
            existingScene.setPanoUrl(filePath.toString());
            Scene saved = repo.save(existingScene);

            return mapper.toDTO(saved);

        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Upload panorama thất bại: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<byte[]> getPanoFile(String id) {
        Scene scene = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Scene not found"));

        String panoUrl = scene.getPanoUrl();
        if (panoUrl == null || panoUrl.isBlank()) {
            throw new RuntimeException("Scene không có ảnh pano");
        }

        Path path = Paths.get(panoUrl);
        try {
            byte[] bytes = Files.readAllBytes(path);
            String contentType = Files.probeContentType(path);

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .body(bytes);

        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file ảnh");
        }
    }


//    public String getPanoImgUrl(String id) {
//        Scene scene = repo.findById(id)
//                .orElseThrow(() -> new RuntimeException("Không tìm thấy Scene với ID: " + id));
//
//        String panoUrl = scene.getPanoUrl();
//        if (panoUrl == null || panoUrl.isBlank()) {
//            throw new RuntimeException("Không có ảnh panorama cho Scene với ID: " + id);
//        }
//
//        Path path = Paths.get(panoUrl);
//        String fileName = path.getFileName().toString();
//        return "/uploads/scenes/pano/" + fileName;
//    }

    public SceneDTO delete(String id) throws IOException {
        Scene scene = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Khong tim thay Scene co ID: " + id));
        if (scene.getHotspots() != null) {
            scene.getHotspots().forEach(h -> h.setScene(null));
            scene.setHotspots(null);
        }
        if (scene.getPanoUrl() != null) {
            Files.deleteIfExists(Paths.get(scene.getPanoUrl()));
        }

        repo.delete(scene);
        return mapper.toDTO(scene);
    }

}
