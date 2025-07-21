package com.thanh.fashion_shop.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.thanh.fashion_shop.domain.User;
import com.thanh.fashion_shop.domain.respone.PaginationResponse;
import com.thanh.fashion_shop.domain.respone.user.UserResponeDto;
import com.thanh.fashion_shop.repository.UserRepository;
import com.thanh.fashion_shop.util.exceptions.CommonException;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponeDto createUser(User user) throws CommonException {
        if (this.checkExistsByEmail(user.getEmail())) {
            throw new CommonException("Email aleardy exist in system");
        }
        log.info("Creating a user");
        user.setDeleted(false);
        User savedUser = this.userRepository.save(user);
        log.info("User created with id: {}", savedUser.getId());
        return this.converToUserResponeDto(savedUser);

    }

    @Override
    public boolean checkExistsByEmail(String email) {
        return this.userRepository.existsByEmail(email);
    }

    @Override
    public UserResponeDto updateUser(User reqUser) throws CommonException {
        User user = userRepository.findById(reqUser.getId())
                .orElseThrow(() -> new CommonException("User not found"));

        if (reqUser.getAddress() != null) {
            user.setAddress(reqUser.getAddress());
        }
        if (reqUser.getPhone() != null) {
            user.setPhone(reqUser.getPhone());
        }
        if (reqUser.getName() != null) {
            user.setName(reqUser.getName());
        }

        user = this.userRepository.save(user);

        return this.converToUserResponeDto(user);
    }

    @Override
    public UserResponeDto fetchUserById(Long id) throws CommonException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException("User not found"));
        if (user.isDeleted() == true) {
            throw new CommonException("User deleted");
        }
        return this.converToUserResponeDto(user);

    }

    public UserResponeDto converToUserResponeDto(User user) {
        if (user == null) {
            return null;
        }

        UserResponeDto res = new UserResponeDto();
        res.setEmail(user.getEmail());
        res.setId(user.getId());
        res.setName(user.getName());
        res.setPhone(user.getPhone());
        res.setAddress(user.getAddress());
        res.setCreatedBy(user.getCreatedBy());
        res.setCreatedDate(user.getCreatedDate());
        res.setUpdatedDate(user.getUpdatedDate());
        res.setUpdatetedBy(user.getUpdatedBy());
        return res;

    }

    @Override
    public PaginationResponse fetchAllUser(Pageable pageable, Specification<User> spec) {
        Specification<User> notDeleted = (root, query, cb) -> cb.equal(root.get("isDeleted"), false);
        if (spec != null) {
            spec = spec.and(notDeleted);
        } else {
            spec = notDeleted;
        }
        Page<User> userPage = this.userRepository.findAll(spec, pageable);
        PaginationResponse res = new PaginationResponse();
        PaginationResponse.Meta meta = new PaginationResponse.Meta();

        meta.setPage(pageable.getPageNumber() + 1);
        meta.setPageSize(pageable.getPageSize());
        meta.setPages(userPage.getTotalPages());
        meta.setTotalElements(userPage.getTotalElements());
        res.setMeta(meta);

        List<UserResponeDto> users = userPage.getContent().stream()
                .map(item -> this.converToUserResponeDto(item))
                .collect(Collectors.toList());
        res.setResult(users);
        return res;
    }

    public void deleteUserById(Long id) throws CommonException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CommonException("User not found"));
        user.setDeleted(true);
        this.updateUser(user);
        System.out.println("ok");
    }

    @Override
    public User fetchUserByUsername(String username) {
        User user = this.userRepository.findByEmail(username);
        return user;
    }

}
