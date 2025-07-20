package com.thanh.fashion_shop.domain.respone.user;

import java.time.Instant;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponeDto {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private Instant createdDate;
    private Instant updatedDate;
    private String createdBy;
    private String updatetedBy;
}
