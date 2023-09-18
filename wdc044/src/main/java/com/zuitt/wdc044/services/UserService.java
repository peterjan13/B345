package com.zuitt.wdc044.services;

import com.zuitt.wdc044.models.User;
import java.util.Optional;
public interface UserService {
    //We just created a default service;
    void createUser(User user);

    //We just created a default service wherein it will return the record that will match the username provided in the parameter, otherwise Optional will return null.
    Optional<User> findByUsername(String username);
}
