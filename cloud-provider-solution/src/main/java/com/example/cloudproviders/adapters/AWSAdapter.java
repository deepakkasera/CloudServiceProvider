package com.example.cloudproviders.adapters;

import com.example.cloudproviders.libraries.aws.AWSApi;
import com.example.cloudproviders.libraries.aws.AWSConnectionResponse;
import com.example.cloudproviders.models.Connection;
import com.example.cloudproviders.models.ConnectionStatus;

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
