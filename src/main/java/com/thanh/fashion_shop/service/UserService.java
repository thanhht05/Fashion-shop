package com.thanh.fashion_shop.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.domain.respone.PaginationResponse;
import com.thanh.fashion_shop.domain.respone.user.UserResponeDto;
import com.thanh.fashion_shop.util.exceptions.CommonException;

public interface UserService {
    UserResponeDto createUser(User user) throws CommonException;

    UserResponeDto updateUser(User user) throws CommonException;

    UserResponeDto fetchUserById(Long id) throws CommonException;

    boolean checkExistsByEmail(String email);

    PaginationResponse fetchAllUser(Pageable pageable, Specification<User> spec);

    void deleteUserById(Long id) throws CommonException;

}
