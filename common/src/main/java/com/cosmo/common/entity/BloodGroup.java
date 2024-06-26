package com.cosmo.common.entity;

import com.cosmo.common.abstractEntity.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="blood_group")
public class BloodGroup extends AbstractEntity {
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="description", nullable=false)
    private String description;
    @Column(name="uuid", nullable = false, unique = true)
    private String uuid;
}
