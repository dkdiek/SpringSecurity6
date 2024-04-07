package com.cos.security1.repository;

import com.cos.security1.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<UserEntity, Integer> {

// wher절을 사용하여    username 존재하면 true 존재하지 않으면 false
    boolean existsByUsername(String username);

    UserEntity findByUsername(String username);
}
