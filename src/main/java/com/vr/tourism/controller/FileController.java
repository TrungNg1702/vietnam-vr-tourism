package com.vr.tourism.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/api/admin/files")
public class FileController {

    @Value("${app.file.upload-dir:uploads/panorama}")
    private String uploadDir;

    @PostMapping("/upload/pano")
    public ResponseEntity<?> uploadPanorama(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) return ResponseEntity.badRequest().body("Empty file");
            String filename = System.currentTimeMillis() + "-" + StringUtils.cleanPath(file.getOriginalFilename());
            Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(uploadPath);
            Path filePath = uploadPath.resolve(filename);
            try (InputStream is = file.getInputStream()) {
                Files.copy(is, filePath, StandardCopyOption.REPLACE_EXISTING);
            }
            String publicUrl = "/pano/" + filename;
            return ResponseEntity.ok().body(java.util.Map.of("url", publicUrl));
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Upload failed");
        }
    }
}
