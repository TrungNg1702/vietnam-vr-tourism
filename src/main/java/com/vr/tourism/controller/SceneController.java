package com.vr.tourism.controller;

import com.vr.tourism.dto.SceneDTO;
import com.vr.tourism.entity.Scene;
import com.vr.tourism.repository.SceneRepository;
import com.vr.tourism.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/scene")
@RequiredArgsConstructor
public class SceneController {
    private final SceneService sceneService;
    private final SceneRepository sceneRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createScene(@RequestBody SceneDTO dto) {
        try{
            SceneDTO sceneDTO = sceneService.create(dto);
            return ResponseEntity.ok(sceneDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PostMapping("/upload-pano/{id}")
    public ResponseEntity<?> uploadPanoImg(@PathVariable("id") String id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
        try {
            SceneDTO sceneDTO = sceneService.uploadPanoImg(id, file);
            return ResponseEntity.ok(sceneDTO);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateScene(@RequestBody SceneDTO dto,
                                           @PathVariable("id") String id) {
        try{
            SceneDTO sceneDTO = sceneService.update(dto, id);
            return ResponseEntity.ok(sceneDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public SceneDTO getById(@PathVariable("id") String id) {
        return sceneService.getById(id);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        try {
            List<SceneDTO> scenes = sceneService.getAll();
            return ResponseEntity.ok(scenes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/pano/{id}")
    public ResponseEntity<byte[]> getPanoImg(@PathVariable String id) {
        Scene scene = sceneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy Scene với ID: " + id));

        String panoUrl = scene.getPanoUrl();
        if (panoUrl == null || panoUrl.isBlank()) {
            throw new RuntimeException("Scene chưa có ảnh panorama");
        }

        Path path = Paths.get(panoUrl);
        try {
            byte[] bytes = Files.readAllBytes(path);

            // Lấy content type dựa vào phần mở rộng file
            String contentType = Files.probeContentType(path);
            return ResponseEntity.ok()
                    .header("Content-Type", contentType != null ? contentType : "application/octet-stream")
                    .body(bytes);
        } catch (IOException e) {
            throw new RuntimeException("Không thể đọc file: " + e.getMessage(), e);
        }
    }





}
