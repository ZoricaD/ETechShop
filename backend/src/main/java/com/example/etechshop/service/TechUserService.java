package com.example.etechshop.service;

import com.example.etechshop.entity.TechUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface TechUserService extends UserDetailsService {
    TechUser createUser(TechUser user);
    TechUser updateUser(Long id, TechUser user);
    void deleteUser(Long id);
    TechUser getUserById(Long id);
    List<TechUser> getAllUsers();
    TechUser getUserByEmail(String email);

}
