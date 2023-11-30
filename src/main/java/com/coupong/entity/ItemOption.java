package com.coupong.entity;

import com.coupong.constant.ItemOptionStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
@Builder
public class ItemOption {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private String info;

    @Enumerated(EnumType.STRING)
    private ItemOptionStatus status;

    private Integer price;

    private Integer remains;

}
