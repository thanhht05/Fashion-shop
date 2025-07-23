package com.thanh.fashion_shop.domain;

import java.time.Instant;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thanh.fashion_shop.util.SecurityUtil;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "permissions")
@Getter
@Setter
public class Permission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Api path khong duoc de trong")
    private String apiPath;
    @NotBlank(message = "Method path khong duoc de trong")
    private String method;
    @NotBlank(message = "Mpdule path khong duoc de trong")
    private String module;
    private String name;

    private String createdBy;
    private String updatedBy;
    private Instant createdDate;
    private Instant updatedDate;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "permissions")
    @JsonIgnore
    List<Role> roles;

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
