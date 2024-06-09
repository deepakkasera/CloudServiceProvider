package com.example.cloudproviders.controllers;

import com.example.cloudproviders.dtos.CreateConnectionRequestDto;
import com.example.cloudproviders.dtos.CreateConnectionResponseDto;
import com.example.cloudproviders.dtos.ResponseStatus;
import com.example.cloudproviders.exceptions.UserNotFoundException;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.services.CloudService;

public class CloudController {
    private CloudService cloudService;

    public CloudController(CloudService cloudService) {
        this.cloudService = cloudService;
    }

    public CreateConnectionResponseDto createConnection(CreateConnectionRequestDto createConnectionRequestDto) {
        CreateConnectionResponseDto responseDto = new CreateConnectionResponseDto();
        try {
            Connection connection = cloudService.createConnection(createConnectionRequestDto.getUserId());
            responseDto.setConnectionId(connection.getConnectionId());
            responseDto.setConnectionStatus(connection.getConnectionStatus());
            responseDto.setResponseStatus(ResponseStatus.SUCCESS);
        } catch (UserNotFoundException e) {
            responseDto.setResponseStatus(ResponseStatus.FAILURE);
        }
        return responseDto;
    }
}
