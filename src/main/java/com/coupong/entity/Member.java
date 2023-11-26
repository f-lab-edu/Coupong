package com.coupong.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Member {
    @Id
    @GeneratedValue
    private Long id;

    private String email;

    private String password;

    private String nickname;

    private String ip;

    private String phoneNumber;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime leavedAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    public Member() {
    }

    public Member(Long id, String email, String password, String nickname, String ip, String phoneNumber, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime leavedAt, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.ip = ip;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.leavedAt = leavedAt;
        this.role = role;
    }
}
