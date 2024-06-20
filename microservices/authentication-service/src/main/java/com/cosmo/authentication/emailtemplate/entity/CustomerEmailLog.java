package com.cosmo.authentication.emailtemplate.entity;

import com.cosmo.authentication.user.entity.Customer;
import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_email_log")
public class CustomerEmailLog extends AbstractEntity {

    @Column(name="email",nullable = false)
    private String email;

    @ManyToOne(optional=true, fetch = FetchType.LAZY)
    @JoinColumn(name="customer", nullable=false, referencedColumnName = "id")
    private Customer customer;

    @Column(name="message",nullable = false)
    private String message;

    @Column(name="is_sent")
    private boolean isSent;

    @Column(name="otp")
    private String otp;

    @Column(name="timestamp",nullable = false)
    private LocalDateTime timestamp;
}
