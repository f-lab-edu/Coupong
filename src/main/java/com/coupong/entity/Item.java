package com.coupong.entity;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
public class Item {
    @Id
    @GeneratedValue
    private Long id;

    private String rid;

    private String name;

    private Integer price;

    private Integer remains;

    private Integer orderCount;

    private Integer status;

    private String info;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
