package com.example.cloudprovidersolution.repositories;

import com.example.cloudprovidersolution.models.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserById(long userId);
}
