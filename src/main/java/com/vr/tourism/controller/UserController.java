package com.vr.tourism.controller;

import com.vr.tourism.dto.UserDTO;
import com.vr.tourism.dto.UserUpdateDTO;
import com.vr.tourism.entity.User;
import com.vr.tourism.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/getUserById/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.getUserByID(id));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdateDTO userDTO,
                                        @PathVariable Long id){
        UserDTO updateUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @PostMapping("/uploadAvatar/{id}")
    public ResponseEntity<?> uploadAvatar(@PathVariable Long id, @RequestParam("file") org.springframework.web.multipart.MultipartFile file){
        UserDTO userDTO = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/avatar/{id}")
    public ResponseEntity<byte[]> getAvatar(@PathVariable Long id) {
        return userService.getAvatar(id);
    }

}
