package com.coupong.coupon.repository;

import com.coupong.entity.Coupon;
import com.coupong.entity.IssuedCoupon;
import com.coupong.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

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

    public Coupon findById(Integer couponId) {
        return em.createQuery(
                        "select c from coupon c" +
                                "where c.id = :id", Coupon.class)
                .setParameter("id", couponId)
                .getSingleResult();
    }
    public IssuedCoupon findIssuedCouponById(Integer issuedCouponId) {
        return em.createQuery(
                        "select i from issuedCoupon i " +
                                "where i.id = :id", IssuedCoupon.class)
                .setParameter("id", issuedCouponId)
                .getSingleResult();
    }
}
