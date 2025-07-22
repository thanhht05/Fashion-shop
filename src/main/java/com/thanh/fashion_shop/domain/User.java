package com.thanh.fashion_shop.domain;

import java.time.Instant;

import com.thanh.fashion_shop.util.SecurityUtil;

import jakarta.persistence.Column;
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
    private String name;
    @NotBlank(message = "Email không được trống")
    private String email;
    @NotBlank(message = "Password không được trống")
    private String password;
    @Column(columnDefinition = "MEDIUMTEXT")
    private String refreshToken;
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
        this.createdBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        System.out.println("sdff");
    }

    @PreUpdate
    public void handelPreUpdate() {
        this.updatedDate = Instant.now();
        this.updatedBy = SecurityUtil.getCurrentUserLogin().isPresent()
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";
        System.out.println("fdf");
    }
}
