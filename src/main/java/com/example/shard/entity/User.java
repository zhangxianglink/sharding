package com.example.shard.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class User {
    private Integer id;
    private String nickname;
    private String password;
    private String gender;
    private LocalDateTime birthday;
}
