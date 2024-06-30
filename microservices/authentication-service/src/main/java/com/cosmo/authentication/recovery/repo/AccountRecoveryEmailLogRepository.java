package com.cosmo.authentication.recovery.repo;

import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.recovery.entity.AccountRecoveryEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRecoveryEmailLogRepository extends JpaRepository<AccountRecoveryEmailLog, Long> {
    AccountRecoveryEmailLog findByOtp(String otp);
    Optional<AccountRecoveryEmailLog> findByEmailAndOtpAndIsOtpVerifiedTrue(String email, String otp);
}
