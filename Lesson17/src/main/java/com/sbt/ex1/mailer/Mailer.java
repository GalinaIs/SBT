package com.sbt.ex1.mailer;

public interface Mailer {
    void send(String sender, String recipient, String message) throws Exception;
}