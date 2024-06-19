package com.cosmo.authentication.emailtemplate.entity;

import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="email_template")
public class EmailTemplate extends AbstractEntity {

    @Column(name="name", nullable = false)
    private String name;

    @Column(name="template", nullable = false)
    private String template;

    @Column(name="created_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate;
}
