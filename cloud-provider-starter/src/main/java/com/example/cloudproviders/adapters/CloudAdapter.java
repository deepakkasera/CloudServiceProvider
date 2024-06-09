package com.example.cloudproviders.adapters;

import com.example.cloudproviders.models.Connection;

public interface CloudAdapter {
    Connection createConnection(long userId);
}
