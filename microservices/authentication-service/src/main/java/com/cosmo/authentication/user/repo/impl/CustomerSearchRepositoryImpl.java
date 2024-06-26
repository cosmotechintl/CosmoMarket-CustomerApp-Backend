package com.cosmo.authentication.user.repo.impl;


import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.authentication.user.repo.CustomerSearchRepository;
import com.cosmo.common.model.SearchParam;
import com.cosmo.common.util.SearchParamUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

import static com.cosmo.common.constant.SearchParamConstant.*;

@Repository
@RequiredArgsConstructor
public class CustomerSearchRepositoryImpl implements CustomerSearchRepository {

    @PersistenceContext
    protected EntityManager em;

    @Override
    public Long count(SearchParam searchParam) {
        return (Long) em.createQuery("select COUNT(c.id) " +
                        "from Customer  c " +
                        "join Status s on s.id=c.status.id " +
                        " where " +
                        "(:firstName is null or c.firstName like CONCAT('%', :firstName, '%')) and " +
                        "(:lastName is null or c.lastName like CONCAT('%', :lastName, '%')) and " +
                        "(:status is null or s.description=:status) ")
                .setParameter("firstName", SearchParamUtil.getString(searchParam, FIRST_NAME))
                .setParameter("lastName", SearchParamUtil.getString(searchParam, LAST_NAME))
                .setParameter("status", SearchParamUtil.getString(searchParam, STATUS))
                .getSingleResult();
    }

    @Override
    public List<Customer> getAll(SearchParam searchParam) {
        return em.createQuery("select c " +
                        "from Customer  c " +
                        "join Status s on s.id=c.status.id " +
                        " where " +
                        "(:firstName is null or c.firstName like CONCAT('%', :firstName, '%')) and " +
                        "(:lastName is null or c.lastName like CONCAT('%', :lastName, '%')) and " +
                        "(:status is null or s.description=:status) ")
                .setParameter("firstName", SearchParamUtil.getString(searchParam,FIRST_NAME))
                .setParameter("lastName", SearchParamUtil.getString(searchParam,LAST_NAME))
                .setParameter("status", SearchParamUtil.getString(searchParam, STATUS))
                .getResultList();
    }
}
