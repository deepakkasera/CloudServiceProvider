package com.example.cloudprovidersolution.controllers;

import com.example.cloudprovidersolution.dtos.CreateConnectionRequestDto;
import com.example.cloudprovidersolution.dtos.CreateConnectionResponseDto;
import com.example.cloudprovidersolution.dtos.ResponseStatus;
import com.example.cloudprovidersolution.exceptions.UserNotFoundException;
import com.example.cloudprovidersolution.models.Connection;
import com.example.cloudprovidersolution.services.CloudService;

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
