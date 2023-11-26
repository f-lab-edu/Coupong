package com.coupong.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Builder
public class Role {
    @Id @GeneratedValue
    private Long id;

    private String role;

    public Role() {}

    public Role(Long id, String role) {
        this.id = id;
        this.role = role;
    }
}
