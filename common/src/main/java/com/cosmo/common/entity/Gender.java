package com.cosmo.common.entity;

import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name="gender")
public class Gender extends AbstractEntity {

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="description", nullable=false)
    private String description;

    @Column(name="uuid", nullable = false, unique = true)
    private String uuid;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;
}