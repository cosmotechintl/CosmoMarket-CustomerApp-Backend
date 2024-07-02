package com.cosmo.authentication.profile.repo;

import com.cosmo.authentication.profile.entity.ChangeEmailLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChangeEmailLogRepository extends JpaRepository<ChangeEmailLog, Long> {
    ChangeEmailLog findByOtp(String otp);
}
