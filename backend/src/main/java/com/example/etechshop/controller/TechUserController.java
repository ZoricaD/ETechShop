package com.example.etechshop.controller;

import com.example.etechshop.entity.TechUser;
import com.example.etechshop.service.TechUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public class TechUserController {

    private final TechUserService techUserService;

    public TechUserController(TechUserService techUserService) {
        this.techUserService = techUserService;
    }

    @PostMapping("/create")
    public ResponseEntity<TechUser> createUser(@RequestBody TechUser user) {
        if (user == null || user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TechUser createdUser = techUserService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TechUser> getUserById(@PathVariable Long id) {
        try {
            TechUser user = techUserService.getUserById(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TechUser>> getAllUsers() {
        List<TechUser> users = techUserService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(users);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<TechUser> getUserByEmail(@PathVariable String email) {
        try {
            TechUser user = techUserService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<TechUser> updateUser(@PathVariable Long id, @RequestBody TechUser user) {
        if (user == null || user.getUsername().isBlank() || user.getEmail().isBlank() || user.getPassword().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            TechUser updatedUser = techUserService.updateUser(id, user);
            return ResponseEntity.ok(updatedUser);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            techUserService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
