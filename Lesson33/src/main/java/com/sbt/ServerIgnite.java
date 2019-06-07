package com.sbt;

import org.apache.ignite.Ignite;
import org.apache.ignite.Ignition;

public class ServerIgnite {
    public static void main(String[] args) {
        Ignite ignite = Ignition.start(Config.configuration(false));
    }
}
