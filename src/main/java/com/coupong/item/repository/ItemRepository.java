package com.coupong.item.repository;

import com.coupong.config.exception.NotFoundException;
import com.coupong.entity.Coupon;
import com.coupong.entity.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
public class ItemRepository {

    private final EntityManager em;

    public ItemRepository(EntityManager em) {
        this.em = em;
    }

    public Item findByRid(String itemRid) {

        List<Item> items = em.createQuery(
                        "select i from Item as i where i.rid = :rid", Item.class)
                .setParameter("rid", itemRid)
                .getResultList();

        return items.stream().findAny().orElseThrow(NotFoundException::new);
    }


}
