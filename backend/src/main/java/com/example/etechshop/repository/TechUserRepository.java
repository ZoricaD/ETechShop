package com.example.etechshop.repository;

import com.example.etechshop.entity.TechUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface TechUserRepository extends JpaRepository<TechUser, Long> {
    Optional<TechUser> findByUsername(String username);
    Optional<TechUser> findByEmailAndPassword(String email, String password);
    Optional<TechUser> findByEmail(String email);

}
