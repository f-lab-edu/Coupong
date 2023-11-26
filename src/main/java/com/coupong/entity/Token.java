package com.coupong.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Token {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(name = "atk")
    private String accessToken;

    private LocalDateTime atkValidAt;

    @Column(name = "rtk")
    private String refreshToken;

    private LocalDateTime rtkValidAt;

    public Token() {
    }

    public Token(Long id, Member member, String accessToken, LocalDateTime atkValidAt, String refreshToken, LocalDateTime rtkValidAt) {
        this.id = id;
        this.member = member;
        this.accessToken = accessToken;
        this.atkValidAt = atkValidAt;
        this.refreshToken = refreshToken;
        this.rtkValidAt = rtkValidAt;
    }
}
