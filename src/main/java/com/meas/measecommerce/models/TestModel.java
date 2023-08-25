package com.meas.measecommerce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class TestModel {
    @Id
    private long id;

    @Column
    private String username;
}
