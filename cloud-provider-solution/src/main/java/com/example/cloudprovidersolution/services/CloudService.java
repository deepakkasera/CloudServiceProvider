package com.example.cloudprovidersolution.services;

import com.example.cloudprovidersolution.exceptions.UserNotFoundException;
import com.example.cloudprovidersolution.models.Connection;

public interface CloudService {
    Connection createConnection(Long userId) throws UserNotFoundException;
}
