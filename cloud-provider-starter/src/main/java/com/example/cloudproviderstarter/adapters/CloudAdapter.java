package com.example.cloudproviderstarter.adapters;

import com.example.cloudproviderstarter.models.Connection;

public interface CloudAdapter {
    Connection createConnection(long userId);
}
