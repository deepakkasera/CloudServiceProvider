package com.example.cloudproviders.controllers;

import com.example.cloudproviders.dtos.CreateConnectionRequestDto;
import com.example.cloudproviders.dtos.CreateConnectionResponseDto;
import com.example.cloudproviders.services.CloudService;

public class CloudController {
    private CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public CreateConnectionResponseDto createConnection(CreateConnectionRequestDto createConnectionRequestDto) {
        return null;
    }
}
