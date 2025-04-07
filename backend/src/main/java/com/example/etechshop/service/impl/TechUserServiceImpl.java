package com.example.etechshop.service.impl;

import com.example.etechshop.entity.TechUser;
import com.example.etechshop.repository.TechUserRepository;
import com.example.etechshop.service.CartService;
import com.example.etechshop.service.TechUserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TechUserServiceImpl implements TechUserService, UserDetailsService {

    private final TechUserRepository techUserRepository;
    private final CartService cartService;
    private final PasswordEncoder passwordEncoder;

    public TechUserServiceImpl(TechUserRepository techUserRepository, CartService cartService, PasswordEncoder passwordEncoder) {
        this.techUserRepository = techUserRepository;
        this.cartService = cartService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public TechUser createUser(TechUser user) {
        if (techUserRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        if (techUserRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setId(null);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return techUserRepository.save(user);
    }

    @Override
    @Transactional
    public TechUser updateUser(Long id, TechUser user) {
        return techUserRepository.findById(id).map(existingUser -> {
            existingUser.setUsername(user.getUsername());
            existingUser.setEmail(user.getEmail());

            if (!user.getPassword().equals(existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }

            existingUser.setRole(user.getRole());
            return techUserRepository.save(existingUser);
        }).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        if (!techUserRepository.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        techUserRepository.deleteById(id);
    }

    @Override
    public TechUser getUserById(Long id) {
        return techUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<TechUser> getAllUsers() {
        return techUserRepository.findAll();
    }

    @Override
    public TechUser getUserByEmail(String email) {
        return techUserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        TechUser user = techUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .roles(user.getRole().name())
                .build();
    }
}
