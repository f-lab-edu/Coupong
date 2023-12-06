package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.IssuedCouponStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "issued_coupon")
public class IssuedCoupon implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "couponId")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "memberId")
    private Member member;

    @Enumerated(EnumType.STRING)
    @NotNull
    private IssuedCouponStatus status;

    private LocalDateTime usedAt;

    @CreationTimestamp
    private LocalDateTime issuedAt;

    @OneToOne(mappedBy = "issuedCoupon")
    private Order order;

    protected IssuedCoupon() {} // JPA를 위해 기본생성자 추가

    protected IssuedCoupon(Long id, Coupon coupon, Member member, IssuedCouponStatus status
            , LocalDateTime usedAt, LocalDateTime issuedAt, Order order) {

        this.id = id;
        this.coupon = coupon;
        this.member = member;
        this.status = status;
        this.usedAt = usedAt;
        this.issuedAt = issuedAt;
        this.order = order;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj instanceof IssuedCoupon) {
            IssuedCoupon issuedCoupon = (IssuedCoupon)obj;
            return this.id.equals(issuedCoupon.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * 정적 팩토리 메서드
     * @param member
     * @param coupon
     * @return
     */
    public static IssuedCoupon createIssuedCoupon(Member member, Coupon coupon) {
        IssuedCoupon issuedCoupon = new IssuedCoupon();
        issuedCoupon.setCoupon(coupon);
        issuedCoupon.setMember(member);
        issuedCoupon.setStatus(IssuedCouponStatus.ISSUED);
        issuedCoupon.setIssuedAt(LocalDateTime.now());

        return issuedCoupon;
    }

    public Long getId() {
        return id;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public Member getMember() {
        return member;
    }

    public Order getOrder() {
        return order;
    }

    public IssuedCouponStatus getStatus() {
        return status;
    }

    public LocalDateTime getIssuedAt() {
        return issuedAt;
    }

    public LocalDateTime getUsedAt() {
        return usedAt;
    }

    private void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setStatus(IssuedCouponStatus status) {
        this.status = status;
    }

    private void setIssuedAt(LocalDateTime issuedAt) {
        this.issuedAt = issuedAt;
    }

    public void use() {
        this.status = IssuedCouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }
}