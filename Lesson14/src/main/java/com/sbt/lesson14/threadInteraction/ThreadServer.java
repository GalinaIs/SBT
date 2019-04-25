package com.sbt.lesson14.threadInteraction;

import java.io.*;
import java.net.*;
import java.util.Random;

public class ThreadServer implements Runnable {
    private ServerSocket server;

    public ThreadServer(ServerSocket server) {
        this.server = server;
    }

    public void run() {
        try {
            while (true) {
                workingWithClient();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    private void workingWithClient() throws IOException {
        try (Socket serverSocket = server.accept();
             DataInputStream dataInputStream = new DataInputStream(serverSocket.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(serverSocket.getOutputStream())) {
            int number = new Random().nextInt(10);
            dataOutputStream.writeUTF("We connect to server. I generate a number from 0 to 9");
            System.out.println("New connection. Number is " + number);

            while (true) {
                int numberClient = dataInputStream.readInt();
                if (number == numberClient) {
                    dataOutputStream.writeBoolean(true);
                    System.out.println("Client input is correct number. Connection is closed");
                    return;
                } else {
                    dataOutputStream.writeBoolean(false);
                }
            }
        }
    }
}


