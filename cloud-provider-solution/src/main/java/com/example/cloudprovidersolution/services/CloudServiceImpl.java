package com.example.cloudprovidersolution.services;

import com.example.cloudprovidersolution.adapters.CloudAdapter;
import com.example.cloudprovidersolution.exceptions.UserNotFoundException;
import com.example.cloudprovidersolution.models.Connection;
import com.example.cloudprovidersolution.models.User;
import com.example.cloudprovidersolution.repositories.UserRepository;

public class CloudServiceImpl implements CloudService {
    private UserRepository userRepository;
    private CloudAdapter cloudAdapter;
    public CloudServiceImpl(UserRepository userRepository, CloudAdapter cloudAdapter) {
        this.userRepository = userRepository;
        this.cloudAdapter = cloudAdapter;
    }
    @Override
    public Connection createConnection(Long userId) throws UserNotFoundException {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));
        return cloudAdapter.createConnection(userId);
    }
}
