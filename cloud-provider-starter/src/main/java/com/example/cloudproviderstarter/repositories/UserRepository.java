package com.example.cloudproviderstarter.repositories;

import com.example.cloudproviderstarter.models.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserById(long userId);
}
