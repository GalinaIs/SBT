package com.sbt;

import org.apache.ignite.configuration.IgniteConfiguration;

public class Config {
    public static IgniteConfiguration configuration(boolean isClient) {
        IgniteConfiguration configuration = new IgniteConfiguration();
        configuration.setClientMode(isClient);
        configuration.setLocalHost("127.0.0.1");
        return configuration;
    }
}
