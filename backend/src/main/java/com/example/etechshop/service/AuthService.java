package com.example.etechshop.service;

import com.example.etechshop.entity.Dto.AuthRequest;
import com.example.etechshop.entity.Dto.AuthResponse;

public interface AuthService {
    AuthResponse register(AuthRequest request);
    AuthResponse login(AuthRequest request);
}
