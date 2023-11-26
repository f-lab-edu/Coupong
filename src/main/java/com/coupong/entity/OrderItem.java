package com.coupong.entity;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class OrderItem {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "itemId")
    private Item item;

    private Integer quantity;

    public static OrderItem createOrderItem(Item item, Integer quantity) {
        // 상품이 주문할 수 있는 상품인지 확인
        item.canOrder(quantity);

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setQuantity(quantity);
        return orderItem;
    }

    public void setOrder(Order order) { this.order = order; }
    private void setItem(Item item) { this.item = item; }

    private void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
