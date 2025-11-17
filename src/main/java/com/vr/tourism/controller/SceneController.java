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
            SceneDTO sceneDTO = sceneService.create(dto);
            return ResponseEntity.ok(sceneDTO);
    }

    @PostMapping("/upload-pano/{id}")
    public ResponseEntity<?> uploadPanoImg(@PathVariable("id") String id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
            SceneDTO sceneDTO = sceneService.uploadPanoImg(id, file);
            return ResponseEntity.ok(sceneDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateScene(@RequestBody SceneDTO dto,
                                           @PathVariable("id") String id) {
            SceneDTO sceneDTO = sceneService.update(dto, id);
            return ResponseEntity.ok(sceneDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
            SceneDTO sceneDTO = sceneService.getById(id);
            return ResponseEntity.ok(sceneDTO);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
            List<SceneDTO> scenes = sceneService.getAll();
            return ResponseEntity.ok(scenes);
    }

    @GetMapping("/pano/{id}")
    public ResponseEntity<byte[]> getPanoFile(@PathVariable String id) {
            return sceneService.getPanoFile(id);
    }

    @GetMapping("/getSceneByDestinationId/{id}")
    public ResponseEntity<?> getSceneByDestinationId(@PathVariable Long id) {
            List<SceneDTO> scenes = sceneService.getSceneByDestinationId(id);
            return ResponseEntity.ok(scenes);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteScene (@PathVariable String id) throws IOException {
            SceneDTO existingScene = sceneService.delete(id);
            return ResponseEntity.ok("Xoa thanh cong Scene co ID: " + existingScene.getId());
    }


}
