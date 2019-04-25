package com.sbt.lesson14.chat;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class PoolServersImpl implements PoolServers {
    private volatile ConcurrentMap<String, CopyOnWriteArrayList<Message>> historyByLogin = new ConcurrentHashMap<>();
    private volatile ConcurrentMap<String, Socket> socketsByLogin = new ConcurrentHashMap<>();
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private final int port;
    private final Lock lock = new ReentrantLock();

    public PoolServersImpl(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ChatServer(clientSocket, this));
            }
        }
    }

    public boolean connectUser(String loginUser, Socket userSocket) {
        boolean result;
        lock.lock();
        if (!historyByLogin.containsKey(loginUser)) {
            historyByLogin.put(loginUser, new CopyOnWriteArrayList<>());
            result = true;
        } else {
            result = false;
        }
        lock.unlock();

        if (result) {
            notifyAllUsers("User " + loginUser + " join to the chat");
            socketsByLogin.put(loginUser, userSocket);
        }

        return result;
    }

    public boolean sendMessage(String loginToUser, Message message) {
        CopyOnWriteArrayList<Message> historyUser = historyByLogin.get(loginToUser);
        if (historyUser == null) return false;

        historyUser.add(message);
        executorService.submit(new SendMessageRunnable(socketsByLogin.get(loginToUser), message));
        return true;
    }

    public String getHistory(String loginUser) {
        CopyOnWriteArrayList<Message> historyUser = historyByLogin.get(loginUser);
        StringBuilder stringBuilder = new StringBuilder();
        for (Message message : historyUser) {
            stringBuilder.append(message).append("\n\r");
        }
        return stringBuilder.toString();
    }


    public void disconnectUser(String login) {
        historyByLogin.remove(login);
        socketsByLogin.remove(login);
        notifyAllUsers("User " + login + " exit to the chat");
    }

    private void notifyAllUsers(String message) {
        socketsByLogin.forEach((login, socket) -> executorService.submit(new SendMessageRunnable(socket, message)));
    }

    private class SendMessageRunnable implements Runnable {
        private final String message;
        private final Socket socket;

        SendMessageRunnable(Socket socket, String message) {
            this.message = message;
            this.socket = socket;
        }

        SendMessageRunnable(Socket socket, Message message) {
            this(socket, message.toString());
        }

        @Override
        public void run() {
            try {
                PrintWriter writerOut = new PrintWriter(socket.getOutputStream(), true);
                writerOut.println("\tYour receive new message \r\n\t" + message);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}