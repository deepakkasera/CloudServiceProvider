package com.example.cloudproviderstarter.controllers;

import com.example.cloudproviderstarter.dtos.CreateConnectionRequestDto;
import com.example.cloudproviderstarter.dtos.CreateConnectionResponseDto;
import com.example.cloudproviderstarter.services.CloudService;

public class CloudController {
    private CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public CreateConnectionResponseDto createConnection(CreateConnectionRequestDto createConnectionRequestDto) {
        return null;
    }
}
