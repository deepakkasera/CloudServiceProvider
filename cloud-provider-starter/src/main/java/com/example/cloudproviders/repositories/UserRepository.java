package com.example.cloudproviders.repositories;

import com.example.cloudproviders.models.User;

import java.util.Optional;

public interface UserRepository {
    User save(User user);

    Optional<User> findUserById(long userId);
}
