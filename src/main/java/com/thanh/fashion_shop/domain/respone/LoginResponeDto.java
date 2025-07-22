package com.thanh.fashion_shop.domain.respone;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class LoginResponeDto {
    private UserLogin user;
    @JsonProperty("access_token")
    private String accessToken;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserLogin {
        private Long id;
        private String email;
        private String name;

    }

    @Getter
    @Setter
    public static class UserGetAccount {
        private UserLogin user;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class UserInsideToken {
        private Long id;
        private String email;
        private String name;

    }
}
