package com.coupong.entity;

import com.coupong.constant.OrderStatus;
import lombok.Getter;
import lombok.ToString;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@ToString
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String rid;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    private String address;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status; // 주문, 취소

    @CreationTimestamp
    private LocalDateTime order_at;

    @OneToOne
    @JoinColumn(name = "issuedCouponId")
    private IssuedCoupon issuedCoupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;

        if(o instanceof Order) {
            Order order = (Order)o;
            return this.id.equals(order.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Order 정적 팩토리 메서드
     * @param member
     * @param orderItems
     * @param issuedCoupon
     * @param address
     * @return
     */
    public static Order createOrder(Member member, List<OrderItem> orderItems, IssuedCoupon issuedCoupon, String address) {
        Order order = new Order();
        order.setRid();
        order.setMember(member);
        order.setAddress(address);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderAt(LocalDateTime.now());
        order.setIssuedCoupon(issuedCoupon);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }
        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    private void setRid() {
        this.rid = "ORD-" + UUID.randomUUID();
    }
    private void setMember(Member member) {
        this.member = member;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    private void setOrderAt(LocalDateTime orderAt) {
        this.order_at = orderAt;
    }

    private void setIssuedCoupon(IssuedCoupon issuedCoupon) {
        this.issuedCoupon = issuedCoupon;
    }
}