package com.vr.tourism.controller;

import com.vr.tourism.dto.SceneDTO;
import com.vr.tourism.entity.Scene;
import com.vr.tourism.repository.SceneRepository;
import com.vr.tourism.service.SceneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        try{
            SceneDTO sceneDTO = sceneService.getById(id);
            return ResponseEntity.ok(sceneDTO);
        } catch (RuntimeException e) {
            throw new RuntimeException(e.getMessage());
        }

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
    public ResponseEntity<byte[]> getPanoFile(@PathVariable String id) {
        try{
            return sceneService.getPanoFile(id);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteScene (@PathVariable String id) {
        try{
            SceneDTO existingScene = sceneService.delete(id);
            return ResponseEntity.ok("Xoa thanh cong Scene co ID: " + existingScene.getId());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
        }
    }


}
