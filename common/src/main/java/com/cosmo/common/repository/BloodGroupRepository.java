package com.cosmo.common.repository;

import com.cosmo.common.entity.BloodGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup,Long> {
    BloodGroup findByName(String name);
}
