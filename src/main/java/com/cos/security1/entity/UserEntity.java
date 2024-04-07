package com.cos.security1.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    //username 중복x
    @Column(unique = true)
    private String username;
    private String password;
    private String role;
}
