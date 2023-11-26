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

    public Member findByEmail(String email){
        String sql = "select m from Member m where m.email = :email";
        return em.createQuery(sql, Member.class)
                .setParameter("email", email)
                .getSingleResult();
    }
}
