package com.coupong.coupon.repository;

import com.coupong.config.exception.NotFoundException;
import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class CouponRepository {

    private final EntityManager em;

    @Autowired
    public CouponRepository(EntityManager em) {
        this.em = em;
    }

    public Coupon save(Coupon coupon) {
        em.persist(coupon);
        return coupon;
    }

    public IssuedCoupon save(IssuedCoupon issuedCoupon) {
        em.persist(issuedCoupon);
        return issuedCoupon;
    }

    public Optional<Coupon> findById(Long couponId) {
        List<Coupon> coupons = em.createQuery(
                "select c from coupon as c where c.id = :id", Coupon.class)
                .setParameter("id", couponId)
                .getResultList();

        return coupons.stream().findAny();
    }
    public Optional<IssuedCoupon> findIssuedCouponById(Integer issuedCouponId) {
        List<IssuedCoupon> issuedCoupons = em.createQuery(
                        "select i from issued_coupon as i where i.id = :id", IssuedCoupon.class)
                .setParameter("id", issuedCouponId)
                .getResultList();

        return issuedCoupons.stream().findAny();
    }
}
