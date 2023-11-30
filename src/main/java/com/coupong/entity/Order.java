package com.coupong.entity;

import com.coupong.constant.OrderCsStatus;
import com.coupong.constant.OrderStatus;
import lombok.Getter;
import lombok.ToString;
import org.aspectj.weaver.ast.Or;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Getter
@ToString
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rid;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @CreationTimestamp
    private LocalDateTime order_at;

    @NotBlank
    private String address;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus status;

    @Enumerated(EnumType.ORDINAL)
    private OrderCsStatus csStatus;

    @NotNull
    private Integer totalItemFee;

    @NotNull
    private Integer deliveryFee;

    @NotNull
    private Integer totalOrderFee;

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
    public static Order createOrder(Member member, String address
            , IssuedCoupon issuedCoupon
            , List<OrderItem> orderItems
            ) {
        Order order = new Order();
        order.setRid();
        order.setMember(member);
        order.setOrderAt(LocalDateTime.now());
        order.setAddress(address);
        order.setStatus(OrderStatus.ORDER_RECEIVED);

        order.setIssuedCoupon(issuedCoupon);

        // 총상품금액 = 주문상품의 쿠폰적용금액 합산 - 장바구니 쿠폰 할인
        int totalItemFee = 0;
        for(OrderItem orderItem : orderItems) {
            totalItemFee += orderItem.getCouponAppAmt();
            order.addOrderItem(orderItem);
        }
        int discountFee = (totalItemFee * issuedCoupon.getCoupon().getDiscountPercent()) / 100;
        if(discountFee > issuedCoupon.getCoupon().getMaxPrice()) {
            discountFee = issuedCoupon.getCoupon().getMaxPrice();
        }
        totalItemFee -= discountFee;
        order.setTotalItemFee(totalItemFee);

        // 총주문금액 = 총상품금액 + 배송비
        int deliveryFee = 3000;     // TODO: 어떻게 가져올지 정해야함
        order.setDeliveryFee(deliveryFee);
        order.setTotalOrderFee(totalItemFee + deliveryFee);

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

    private void setOrderAt(LocalDateTime orderAt) {
        this.order_at = orderAt;
    }

    private void setAddress(String address) {
        this.address = address;
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    private void setCsStatus(OrderCsStatus csStatus) {
        this.csStatus = csStatus;
    }

    private void setTotalItemFee(Integer totalItemFee) {
        this.totalItemFee = totalItemFee;
    }

    private void setDeliveryFee(Integer deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    private void setTotalOrderFee(Integer totalOrderFee) {
        this.totalOrderFee = totalOrderFee;
    }

    private void setIssuedCoupon(IssuedCoupon issuedCoupon) {
        this.issuedCoupon = issuedCoupon;
    }
}