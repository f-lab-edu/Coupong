package com.coupong.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Getter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rid;

    private String name;

    private Integer price;

    private Integer remains;

    private Integer orderCount;

    private Integer status;

    private String info;

    private String imageUrl;

    private Integer categoryId;

    private LocalDateTime createdAt;

    private Integer createdBy;

    private LocalDateTime updatedAt;

    public Item() {

    }

    // 원래 OrderItem의 setItem에서 사용했었는데.. 필요 없을 것 같다
    public Item(Item item) {
        this.id = item.getId();
        this.rid = item.getRid();
        this.name = item.getName();
        this.price = item.getPrice();
        this.remains = item.getRemains();
        this.orderCount = item.getOrderCount();
        this.status = item.getStatus();
        this.info = item.getInfo();
        this.imageUrl = item.getImageUrl();
        this.categoryId = item.getCategoryId();
        this.createdAt = item.getCreatedAt();
        this.createdBy = item.getCreatedBy();
        this.updatedAt = item.getUpdatedAt();
    }

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
