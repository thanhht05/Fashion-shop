package com.thanh.fashion_shop.domain.respone.role;

import java.time.Instant;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleCreatedRespone {
    private Long id;
    private Instant createdDate;
    private String createdBy;
    private Instant updatedAt;
    private String updatedBy;
    private String name;
    private List<String> permissions;
}
