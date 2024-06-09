package com.example.cloudproviders.adapters;

import com.example.cloudproviders.libraries.google.GoogleApi;
import com.example.cloudproviders.libraries.google.GoogleConnectionResponse;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.models.ConnectionStatus;

public class GoogleAdapter implements CloudAdapter {
    private GoogleApi googleApi;

    public GoogleAdapter() {
        this.googleApi = new GoogleApi();
    }

    @Override
    public Connection createConnection(long userId) {
        GoogleConnectionResponse googleConnectionResponse = googleApi.createConnection(userId);
        Connection connection = new Connection();
        connection.setUserId(userId);
        connection.setConnectionStatus(ConnectionStatus.valueOf(googleConnectionResponse.getConnectionStatus()));
        connection.setConnectionId(googleConnectionResponse.getConnectionId());
        return connection;
    }
}
