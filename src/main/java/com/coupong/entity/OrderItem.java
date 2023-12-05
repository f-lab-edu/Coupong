package com.coupong.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;
    
    // entity 생성해주셨을 때 반영 예정
    // private ItemOption itemOption;

    private Integer quantity;

    private Integer couponAppAmt;

    @ManyToOne  // TODO : @OneToOne 으로 수정한 후 IssuedCoupon 테이블도 수정해줘야 함
    @JoinColumn(name = "issuedCouponId")
    private IssuedCoupon issuedCoupon;

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;

        if(o instanceof OrderItem) {
            OrderItem orderItem = (OrderItem)o;
            return this.order.getId().equals(orderItem.getOrder().getId())
                    && this.item.getId().equals(orderItem.getItem().getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(order.getId(), item.getId());
    }

    public OrderItem() {}

    public OrderItem(Long id, Order order, Item item, Integer quantity) {
        this.id = id;
        this.order = order;
        this.item = item;
        this.quantity = quantity;
    }

    public static OrderItem createOrderItem(Item item, Integer quantity, IssuedCoupon issuedCoupon, Integer couponAppAmt) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        orderItem.setCouponAppAmt(couponAppAmt);
        orderItem.setIssuedCoupon(issuedCoupon);
        return orderItem;
    }

    public Long getId() {
        return id;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Integer getCouponAppAmt() {
        return couponAppAmt;
    }

    public IssuedCoupon getIssuedCoupon() {
        return issuedCoupon;
    }

    public void setOrder(Order order) { this.order = order; }
    private void setItem(Item item) { this.item = item; }
    private void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    private void setCouponAppAmt(Integer amt) { this.couponAppAmt = amt; }
    private void setIssuedCoupon(IssuedCoupon issuedCoupon) {
        this.issuedCoupon = issuedCoupon;
    }

}