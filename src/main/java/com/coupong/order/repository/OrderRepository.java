package com.coupong.order.repository;

import com.coupong.entity.Order;
import com.coupong.entity.OrderItem;
import com.coupong.order.dto.OrderDto;
import com.coupong.order.dto.OrderHistDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
public class OrderRepository {

    private EntityManager em;

    @Autowired
    public OrderRepository(EntityManager entityManager) {
        this.em = entityManager;
    }

    public Order save(Order order) {
        em.persist(order);
        return order;
    }

    public List<Order> findByPhoneNumber(String phoneNumber) {
        return em.createQuery(
                        "select o from order o " +
                                "where o.phoneNumber = :phoneNumber", Order.class)
                .setParameter("phoneNumber", phoneNumber)
                .getResultList();
    }

    public List<Order> findByOrderRid(String orderRid) {
        return em.createQuery(
                        "select o from order o " +
                                "where o.order_rid = :orderRid", Order.class)
                .setParameter("orderRid", orderRid)
                .getResultList();
    }


}
