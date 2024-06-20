package com.cosmo.authentication.emailtemplate.repo;

import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerEmailLogRepository extends JpaRepository<CustomerEmailLog,Long> {
    boolean existsByOtp(String otp);

    Optional<CustomerEmailLog> findByEmail(String email);

    CustomerEmailLog findByOtp(String otp);
}
