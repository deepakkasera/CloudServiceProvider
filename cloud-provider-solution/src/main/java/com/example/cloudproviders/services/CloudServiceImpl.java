package com.example.cloudproviders.services;

import com.example.cloudproviders.adapters.CloudAdapter;
import com.example.cloudproviders.exceptions.UserNotFoundException;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.models.User;
import com.example.cloudproviders.repositories.UserRepository;

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
