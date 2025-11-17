package com.vr.tourism.service;

import com.vr.tourism.dto.SceneDTO;
import com.vr.tourism.dto.UserDTO;
import com.vr.tourism.dto.UserUpdateDTO;
import com.vr.tourism.entity.Scene;
import com.vr.tourism.entity.User;
import com.vr.tourism.mapper.UserMapper;
import com.vr.tourism.repository.UserRepository;
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

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;

    public UserDTO getUserByID(Long id){
        User user = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UserDTO dto = mapper.toDTO(user);

        dto.setPassword("*********");

        if (user.getAvatar() != null) {
            String fileName = Paths.get(user.getAvatar()).getFileName().toString();
            dto.setAvatar("/uploads/users/avatar/" + fileName);
        }

        return dto;
    }


    public UserDTO updateUser(Long id, UserUpdateDTO userDTO){
        User existingUser = repo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if(userDTO.getUsername() != null){
            existingUser.setUsername(userDTO.getUsername());
        }
        if(userDTO.getEmail() != null){
            existingUser.setEmail(userDTO.getEmail());
        }
        if(userDTO.getFullName() != null){
            existingUser.setFullName(userDTO.getFullName());
        }

//        existingUser.setId(userDTO.getId());
        User updatedUser = repo.save(existingUser);

        return mapper.toDTO(updatedUser);
    }

    public UserDTO uploadAvatar(Long id, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new IllegalArgumentException("File cannot be empty");
            }

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                throw new IllegalArgumentException("File must be an image");
            }

            User existingUser = repo.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy User với ID: " + id));

            // Tạo tên file mới luôn khác
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            fileName = fileName.replaceAll("\\s+", "_");

            // Thư mục lưu file
            String uploadDir = "uploads/users/avatar";
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Xóa file cũ nếu có
            if (existingUser.getAvatar() != null) {
                Path oldPath = Paths.get(existingUser.getAvatar());
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
            existingUser.setAvatar(filePath.toString());
            User saved = repo.save(existingUser);

            return mapper.toDTO(saved);

        } catch (IOException | IllegalArgumentException e) {
            throw new RuntimeException("Upload Avatar thất bại: " + e.getMessage(), e);
        }
    }

    public ResponseEntity<byte[]> getAvatar(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        String avatar = user.getAvatar();
        if (avatar == null || avatar.isBlank()) {
            throw new RuntimeException("User không có avatar");
        }

        Path path = Paths.get(avatar);
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
}
