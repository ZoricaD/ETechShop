package com.example.etechshop.service.impl;

import com.example.etechshop.entity.Cart;
import com.example.etechshop.entity.Dto.AuthRequest;
import com.example.etechshop.entity.Dto.AuthResponse;
import com.example.etechshop.entity.TechUser;
import com.example.etechshop.entity.enums.UserRole;
import com.example.etechshop.repository.CartRepository;
import com.example.etechshop.repository.TechUserRepository;
import com.example.etechshop.config.JwtUtil;
import com.example.etechshop.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final TechUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CartRepository cartRepository;

    @Override
    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        UserRole userRole = UserRole.USER;
        if (request.getEmail().equals("admin@example.com")) {
            userRole = UserRole.ADMIN;
        }

        TechUser user = TechUser.builder()
                .email(request.getEmail())
                .username(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(userRole)
                .build();

        TechUser savedUser = userRepository.save(user);

        if (savedUser.getId() == null) {
            throw new RuntimeException("Failed to save user");
        }

        Cart cart = new Cart();
        cart.setTechUser(savedUser);
        cart.setCartItems(List.of());
        cartRepository.save(cart);

        String token = jwtUtil.generateJwtToken(savedUser.getEmail(), savedUser.getRole());

        return new AuthResponse(token, savedUser.getId(), savedUser.getRole().name());
    }

    @Override
    public AuthResponse login(AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        TechUser user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtil.generateJwtToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getId(), user.getRole().name());
    }
}
