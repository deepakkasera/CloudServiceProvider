package com.example.cloudproviders.services;

import com.example.cloudproviders.exceptions.UserNotFoundException;
import com.example.cloudproviders.models.Connection;

public interface CloudService {
    Connection createConnection(Long userId) throws UserNotFoundException;
}
