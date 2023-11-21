package com.coupong.member.repository;

import com.coupong.entity.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class MemberRepository {

    private final EntityManager em;

    public MemberRepository(EntityManager em) {
        this.em = em;
    }

    public void save(Member member){
        em.persist(member);
    }
}
