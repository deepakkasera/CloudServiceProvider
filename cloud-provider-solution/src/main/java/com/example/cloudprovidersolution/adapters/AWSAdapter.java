package com.example.cloudprovidersolution.adapters;

import com.example.cloudprovidersolution.libraries.aws.AWSApi;
import com.example.cloudprovidersolution.libraries.aws.AWSConnectionResponse;
import com.example.cloudprovidersolution.models.Connection;
import com.example.cloudprovidersolution.models.ConnectionStatus;

public class AWSAdapter implements CloudAdapter {
    private AWSApi awsApi;
    public AWSAdapter() {
        this.awsApi = new AWSApi();
    }

    @Override
    public Connection createConnection(long userId) {
        AWSConnectionResponse awsConnectionResponse = awsApi.createConnection(userId);
        Connection connection = new Connection();
        connection.setConnectionId(awsConnectionResponse.getConnectionId());
        connection.setConnectionStatus(ConnectionStatus.valueOf(awsConnectionResponse.getConnectionStatus()));
        connection.setUserId(userId);
        return connection;
    }
}
