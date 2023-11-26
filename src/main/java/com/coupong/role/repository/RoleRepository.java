package com.coupong.role.repository;

import com.coupong.entity.Role;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
public class RoleRepository {
    private final EntityManager em;

    public RoleRepository(EntityManager em) {
        this.em = em;
    }

    public Role findOne(Long id){
        return em.find(Role.class, id);
    }

    public Role getUserRole(){
        String sql = "select r from Role r where r.role = 'USER'";
        return em.createQuery(sql,Role.class).getResultList().get(0);
    }
}
