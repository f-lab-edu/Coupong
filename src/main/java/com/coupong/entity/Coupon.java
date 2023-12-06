package com.coupong.entity;

import com.coupong.config.exception.BadRequestException;
import com.coupong.config.response.BaseResponse;
import com.coupong.constant.CouponKind;
import com.coupong.constant.CouponStatus;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

import static com.coupong.constant.BaseStatus.*;

@Entity(name = "coupon")
public class Coupon implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CouponKind kind;

    @Max(100)
    private Integer discntRate;

    private Integer maxDiscntPrice;

    private Integer minOrderAmt;

    @NotNull
    private Integer totalCnt;

    @ColumnDefault("0")
    private Integer issuedCnt;

    @Enumerated(EnumType.STRING)
    @NotNull
    private CouponStatus status;

    private LocalDateTime applyAt;

    private LocalDateTime expireAt;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    protected Coupon() {}  // JPA를 위해 기본생성자 추가

    protected Coupon(Long id, String name, CouponKind kind
            , Integer discntRate, Integer maxDiscntPrice, Integer minOrderAmt, Integer totalCnt, Integer issuedCnt
            , CouponStatus status, LocalDateTime applyAt, LocalDateTime expireAt, LocalDateTime createdAt, LocalDateTime updatedAt) {

        this.id = id;
        this.name = name;
        this.kind = kind;
        this.discntRate = discntRate;
        this.maxDiscntPrice = maxDiscntPrice;
        this.minOrderAmt = minOrderAmt;
        this.totalCnt = totalCnt;
        this.issuedCnt = issuedCnt;
        this.status = status;
        this.applyAt = applyAt;
        this.expireAt = expireAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;

        if(obj instanceof Coupon) {
            Coupon coupon = (Coupon)obj;
            return this.id.equals(coupon.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * 정적 팩토리 메서드
     * @param name
     * @param kind
     * @param discntRate
     * @param maxDiscntPrice
     * @param minOrderAmt
     * @param totalCnt
     * @param applyAt
     * @param expireAt
     * @return
     */
    public static Coupon createCoupon(String name, CouponKind kind
            , Integer discntRate, Integer maxDiscntPrice, Integer minOrderAmt, Integer totalCnt
            , LocalDateTime applyAt, LocalDateTime expireAt) {

        Coupon coupon = new Coupon();
        coupon.setName(name);
        coupon.setKind(kind);
        coupon.setDiscntRate(discntRate);
        coupon.setMaxDiscntPrice(maxDiscntPrice);
        coupon.setMinOrderAmt(minOrderAmt);
        coupon.setTotalCnt(totalCnt);
        coupon.setStatus(CouponStatus.AVAILABLE);
        coupon.setApplyAt(applyAt);
        coupon.setExpireAt(expireAt);
        return coupon;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public CouponKind getKind() {
        return kind;
    }

    public Integer getDiscntRate() {
        return discntRate;
    }

    public Integer getMaxDiscntPrice() {
        return maxDiscntPrice;
    }

    public Integer getMinOrderAmt() {
        return minOrderAmt;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public CouponStatus getStatus() {
        return status;
    }

    public LocalDateTime getApplyAt() {
        return applyAt;
    }

    public LocalDateTime getExpireAt() {
        return expireAt;
    }

    public Integer getIssuedCnt() {
        return issuedCnt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setName(String name) {
        this.name = name;
    }

    private void setKind(CouponKind kind) {
        this.kind = kind;
    }

    private void setDiscntRate(Integer discntRate) {
        this.discntRate = discntRate;
    }

    private void setMaxDiscntPrice(Integer maxDiscntPrice) {
        this.maxDiscntPrice = maxDiscntPrice;
    }

    private void setMinOrderAmt(Integer minOrderAmt) {
        this.minOrderAmt = minOrderAmt;
    }

    private void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    private void setStatus(CouponStatus status) {
        this.status = CouponStatus.AVAILABLE;
    }

    private void setApplyAt(LocalDateTime applyAt) {
        this.applyAt = applyAt;
    }

    private void setExpireAt(LocalDateTime expireAt) {
        this.expireAt = expireAt;
    }

    public void issue() throws BadRequestException {
        this.issuedCnt++;
    }
}