package com.coupong.token.repository;
import com.coupong.entity.Member;
import com.coupong.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class TokenRepository {
    private final EntityManager em;

    public Token findOne(Long id){
        return em.find(Token.class, id);
    }

    public Token findByMember(Member m){
        String sql = "select t from Token t where t.member.id = " + m.getId();
        return em.createQuery(sql, Token.class).getResultList().get(0);
    }

    public void save(Token token){
        em.persist(token);
    }
}
