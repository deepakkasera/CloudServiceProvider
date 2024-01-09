package com.example.cloudproviderstarter.services;

import com.example.cloudproviderstarter.exceptions.UserNotFoundException;
import com.example.cloudproviderstarter.models.Connection;

public interface CloudService {
    Connection createConnection(Long userId) throws UserNotFoundException;
}
