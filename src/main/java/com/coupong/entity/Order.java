package com.coupong.entity;

import com.coupong.constant.OrderStatus;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {

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
    private LocalDateTime orderDate;

    @OneToOne
    @JoinColumn(name = "issuedCouponId")
    private IssuedCoupon issuedCoupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.EAGER)
    private List<OrderItem> orderItems = new ArrayList<>();

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
        order.setOrderDate(LocalDateTime.now());
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

    // TODO rid 채번
    private void setRid() {
        this.rid = "ORD" + LocalDateTime.now().format(DateTimeFormatter.BASIC_ISO_DATE);
    }
    private void setMember(Member member) {
        this.member = member;
    }

    private void setAddress(String address) { this.address = address; }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }

    private void setOrderDate(LocalDateTime date) {
        this.orderDate = date;
    }

    private void setIssuedCoupon(IssuedCoupon issuedCoupon) { this.issuedCoupon = issuedCoupon; }
}