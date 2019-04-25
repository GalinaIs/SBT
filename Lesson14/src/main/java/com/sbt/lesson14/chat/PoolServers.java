package com.sbt.lesson14.chat;

import java.net.Socket;

public interface PoolServers {
    boolean connectUser(String loginUser, Socket userSocket);
    boolean sendMessage(String loginToUser, Message message);
    String getHistory(String loginUser);
    void disconnectUser(String login);
}