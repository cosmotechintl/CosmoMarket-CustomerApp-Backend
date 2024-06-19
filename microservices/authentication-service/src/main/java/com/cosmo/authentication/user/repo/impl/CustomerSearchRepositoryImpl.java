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
import static com.cosmo.common.constant.SearchParamConstant.NAME;
import static com.cosmo.common.constant.SearchParamConstant.STATUS;

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
                        "(:name is null or c.name like CONCAT('%', :name, '%')) and " +
                        "(:status is null or s.description=:status) ")
                .setParameter("name", SearchParamUtil.getString(searchParam, NAME))
                .setParameter("status", SearchParamUtil.getString(searchParam, STATUS))
                .getSingleResult();
    }

    @Override
    public List<Customer> getAll(SearchParam searchParam) {
        return em.createQuery("select c " +
                        "from Customer  c " +
                        "join Status s on s.id=c.status.id " +
                        " where " +
                        "(:name is null or c.name like CONCAT('%', :name, '%')) and " +
                        "(:status is null or s.description=:status) ")
                .setParameter("name", SearchParamUtil.getString(searchParam, NAME))
                .setParameter("status", SearchParamUtil.getString(searchParam, STATUS))
                .getResultList();
    }
}
