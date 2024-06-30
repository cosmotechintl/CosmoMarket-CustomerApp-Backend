package com.cosmo.authentication.recovery.entity;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "account_recovery_email_log")
public class AccountRecoveryEmailLog extends AbstractEntity {
    @Column(name="email")
    private String email;

    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JoinColumn(name="customer", nullable=false, referencedColumnName = "id")
    private Customer customer;

    @Column(name="message")
    private String message;

    @Column(name="otp")
    private String otp;

    @Column(name="is_sent")
    private boolean isSent;

    @Column(name="is_otp_verified")
    private boolean isOtpVerified;
    
    @Column(name="timestamp",nullable = false)
    private LocalDateTime timestamp;
}
