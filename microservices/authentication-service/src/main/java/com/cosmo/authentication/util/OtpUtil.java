package com.cosmo.authentication.util;

import com.cosmo.authentication.emailtemplate.entity.CustomerEmailLog;
import com.cosmo.authentication.emailtemplate.repo.CustomerEmailLogRepository;
import com.cosmo.authentication.recovery.entity.AccountRecoveryEmailLog;
import com.cosmo.authentication.recovery.repo.AccountRecoveryEmailLogRepository;
import com.cosmo.common.exception.NotFoundException;
import com.cosmo.common.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Random;

@Service
public class OtpUtil {
    @Autowired
    private CustomerEmailLogRepository customerEmailLogRepository;
    @Autowired
    private AccountRecoveryEmailLogRepository accountRecoveryEmailLogRepository;

    public String generateRegistrationOTP() {
        Random random = new Random();
        String otp;
        do {
            otp = String.valueOf(100000 + random.nextInt(900000));
        } while (isValidRegistrationOTP(otp));
        return otp;
    }

    public boolean isValidRegistrationOTP(String otp) {
        CustomerEmailLog customerEmailLog = customerEmailLogRepository.findByOtp(otp);
        if (customerEmailLog == null) {
            return false;
        }

        LocalDateTime otpTimestamp = customerEmailLog.getTimestamp();
        LocalDateTime now = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(otpTimestamp, now);
        return minutesDifference <= 2;
    }

    public String generateAccountRecoveryOTP() {
        Random random = new Random();
        String otp;
        do {
            otp = String.valueOf(100000 + random.nextInt(900000));
        } while (isValidRegistrationOTP(otp));
        return otp;
    }

    public boolean isValidAccountRecoveryOTP(String otp) {
        AccountRecoveryEmailLog accountRecoveryEmailLog = accountRecoveryEmailLogRepository.findByOtp(otp);
        if (accountRecoveryEmailLog == null) {
            return false;
        }

        LocalDateTime otpTimestamp = accountRecoveryEmailLog.getTimestamp();
        LocalDateTime now = LocalDateTime.now();
        long minutesDifference = ChronoUnit.MINUTES.between(otpTimestamp, now);
        return minutesDifference <= 2;
    }

}
