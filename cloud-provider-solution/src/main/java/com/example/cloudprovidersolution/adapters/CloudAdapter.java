package com.example.cloudprovidersolution.adapters;

import com.example.cloudprovidersolution.models.Connection;

public interface CloudAdapter {
    Connection createConnection(long userId);
}
