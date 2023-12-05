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
@Table(name = "orders")
public class Order implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String rid;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @CreationTimestamp
    private LocalDateTime orderAt;

    @NotBlank
    private String address;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Enumerated(EnumType.STRING)
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

    public Order() {}

    // JPA에서 필요한 메서드
    public Order(Long id, String rid, Member member, LocalDateTime orderAt, String address
            , OrderStatus status, OrderCsStatus csStatus
            , Integer totalItemFee, Integer deliveryFee, Integer totalOrderFee, IssuedCoupon issuedCoupon
            , List<OrderItem> orderItems
    ) {
        this.id = id;
        this.rid = rid;
        this.member = member;
        this.orderAt = orderAt;
        this.address = address;
        this.status = status;
        this.csStatus = csStatus;
        this.totalItemFee = totalItemFee;
        this.deliveryFee = deliveryFee;
        this.totalOrderFee = totalOrderFee;
        this.issuedCoupon = issuedCoupon;
        this.orderItems = orderItems;
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
            , int totalItemFee, int deliveryFee
            ) {
        Order order = new Order();
        order.setRid("ORD-" + UUID.randomUUID());
        order.setMember(member);
        order.setOrderAt(LocalDateTime.now());
        order.setAddress(address);
        order.setStatus(OrderStatus.ORDER_RECEIVED);
        order.setIssuedCoupon(issuedCoupon);
        order.setTotalItemFee(totalItemFee);
        order.setDeliveryFee(deliveryFee);
        order.setTotalOrderFee(totalItemFee + deliveryFee);

        for(OrderItem orderItem : orderItems) {
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public Long getId() {
        return id;
    }

    public String getRid() {
        return rid;
    }

    public Member getMember() {
        return member;
    }

    public LocalDateTime getOrderAt() {
        return orderAt;
    }

    public String getAddress() {
        return address;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public OrderCsStatus getCsStatus() {
        return csStatus;
    }

    public Integer getTotalItemFee() {
        return totalItemFee;
    }

    public Integer getDeliveryFee() {
        return deliveryFee;
    }

    public Integer getTotalOrderFee() {
        return totalOrderFee;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public IssuedCoupon getIssuedCoupon() {
        return issuedCoupon;
    }

    private void setRid(String rid) {
        this.rid = rid;
    }
    private void setMember(Member member) {
        this.member = member;
    }

    private void setOrderAt(LocalDateTime orderAt) {
        this.orderAt = orderAt;
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

    public void cancelOrder() {
        this.csStatus = OrderCsStatus.CANCEL;
    }

}