package com.poly.WebGiaDung.service;

import com.poly.WebGiaDung.entity.UserApp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Optional;

public interface MyUserService extends UserDetailsService {
    UserApp create(UserApp userApp1);

    UserApp findByEmail(String email);

    Page<UserApp> getUsersWithSortAndPagination(Pageable pageable);

    Page<UserApp> findByKeyword(String keyword, Pageable pageable);

    UserApp deleteUser(Integer id);

    Optional<UserApp> findById(Integer id);

    public Boolean doLogin(String username, String password);

    void updatePassword(UserApp userApp, String newPassword);

}
