package com.sbt.lesson14.chat;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatServer implements Runnable {
    private final Socket socket;
    private final PoolServers poolServers;

    public ChatServer(Socket socket, PoolServers poolServers) {
        this.socket = socket;
        this.poolServers = poolServers;
    }

    public void run() {
        try {
            workingWithClient();
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void exitFromChat(String login) {
        poolServers.disconnectUser(login);
        System.out.println("Connection is closed");
    }

    private String loginUser(Scanner scannerIn, PrintWriter writerOut) {
        while (true) {
            String login = scannerIn.nextLine();
            if (login.equalsIgnoreCase("exit")) return null;
            if (poolServers.connectUser(login, socket)) return login;
            writerOut.println("User with login " + login + " already exists. Input another login or exit for quit the chat");
        }
    }

    private void sendMessage(Scanner scannerIn, PrintWriter writerOut, String loginUser) {
        System.out.println("Try send message from user " + loginUser);
        writerOut.println("Input login recipient");
        String loginRecipientUser = scannerIn.nextLine();
        writerOut.println("Input message");
        String messageInfo = scannerIn.nextLine();
        Message message = new Message(loginRecipientUser, loginUser, messageInfo);
        if (poolServers.sendMessage(loginRecipientUser, message)) {
            writerOut.println("Message sends to user " + loginRecipientUser);
        } else {
            writerOut.println("User with login " + loginRecipientUser + " isn't found in the chat");
        }
    }

    private void receiveHistory(PrintWriter writerOut, String loginUser) {
        System.out.println("Send history to user " + loginUser);
        String historyUser = poolServers.getHistory(loginUser);
        writerOut.println("User history\r\n" + historyUser);
    }


    private void workingWithClient() throws IOException {
        try (Scanner scannerIn = new Scanner(socket.getInputStream());
             PrintWriter writerOut = new PrintWriter(socket.getOutputStream(), true)) {
            writerOut.println("Input your login in the chat: ");

            String login = loginUser(scannerIn, writerOut);
            if (login == null) return;
            System.out.println("User " + login + " connect to server");

            String infoCommand = "Input command: \r\nsend - for send message for user\r\n" +
                    "history - for receive messages sent to you\r\n" +
                    "exit - for finish the chat\r\n";

            writerOut.println(infoCommand);
            while (true) {
                String command = scannerIn.nextLine();

                switch (command) {
                    case "send":
                        sendMessage(scannerIn, writerOut, login);
                        break;
                    case "history":
                        receiveHistory(writerOut, login);
                        break;
                    case "exit":
                        exitFromChat(login);
                        return;
                    default: {
                        writerOut.println("The command '" + command + "' isn't found\n\r");
                        writerOut.println(infoCommand);
                        break;
                    }
                }
            }
        }
    }
}
