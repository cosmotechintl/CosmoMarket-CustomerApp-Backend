package com.cosmo.authentication.user.entity;

import com.cosmo.common.abstractEntity.AbstractEntity;
import com.cosmo.common.entity.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "customer")
public class Customer extends AbstractEntity implements UserDetails {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @JoinColumn(name = "status_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Status status;

    @Column(name = "password_changed_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordChangeDate;

    @Column(name = "last_logged_in_time")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoggedInTime;

    @Column(name = "wrong_password_attempt_count")
    private Integer wrongPasswordAttemptCount;

    @Column(name = "profile_picture_name")
    private String profilePictureName;

    @Column(name = "otp_auth_secret")
    private String otpAuthSecret;

    @Column(name = "two_factor_enabled", nullable = false)
    private boolean twoFactorEnabled;

    @Column(name = "wrong_oto_auth_attempt_count")
    private Integer wrongOtpAuthAttemptCount;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
