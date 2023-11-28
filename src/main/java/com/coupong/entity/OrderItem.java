package com.coupong.entity;

import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Getter
@ToString
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public static OrderItem createOrderItem(Item item, Integer quantity) {
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