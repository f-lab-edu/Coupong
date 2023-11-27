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

    public boolean canOrder(Integer quantity) { // TODO : exception 날리기
        if(this.status.compareTo(0) > 0) {
            return false;   // 주문 가능 상태인지 확인
        }
        if(this.remains.compareTo(quantity) < 0) {
            return false;   // 주문하려는 수량보다 적은지 확인
        }
        return true;
    }

    public void order(Integer quantity) {
        // 상품을 주문할 수 있는지 확인
        canOrder(quantity);

        this.remains -= quantity;
        this.status = 1;
        this.updatedAt = LocalDateTime.now();
    }
}
