package com.coupong.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Category {
    @Id
    @GeneratedValue
    private Long id;

    private Long parentCategoryId;

    private String categoryName;

    private String useYn;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
