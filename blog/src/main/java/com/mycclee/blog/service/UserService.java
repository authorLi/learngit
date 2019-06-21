package com.mycclee.blog.service;

import com.mycclee.blog.beans.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    User saveOrUpdateUser(User user);

    User registerUser(User user);

    void removeUser(Long id);

    User getUserById(Long id);

    List<User> getUserList();

    Page<User> listUserByNameLike(String name, Pageable pageable);

    User getUserByUsername(String username);

}
