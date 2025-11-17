package com.vr.tourism.service;

import com.vr.tourism.mapper.UserMapper;
import com.vr.tourism.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final UserMapper mapper;
}
