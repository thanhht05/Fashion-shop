package com.thanh.fashion_shop.domain;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Name không được trống")
    private String name;
    @NotBlank(message = "Email không được trống")
    private String email;
    @NotBlank(message = "Password không được trống")
    private String password;
    private String phone;
    private String address;
    private String createdBy;
    private String updatedBy;
    private Instant createdDate;
    private Instant updatedDate;
    private boolean isDeleted;

    @PrePersist
    public void handleBeforeCreate() {
        this.createdDate = Instant.now();
    }

    @PreUpdate
    public void handelPreUpdate() {
        this.updatedDate = Instant.now();
    }
}
