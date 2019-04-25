package com.sbt.lesson14.threadInteraction;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ThreadClient {
    public static void start(String host, int port) throws IOException {
        try (Socket client = new Socket(host, port);
             DataInputStream dataInputStream = new DataInputStream(client.getInputStream());
             DataOutputStream dataOutputStream = new DataOutputStream(client.getOutputStream());
             Scanner in = new Scanner(System.in)) {
            System.out.println(dataInputStream.readUTF());
            while (true) {
                System.out.print("Input number: ");
                int number = in.nextInt();
                dataOutputStream.writeInt(number);
                boolean result = dataInputStream.readBoolean();
                if (result) {
                    System.out.println("This is the correct number");
                    break;
                } else {
                    System.out.println("This is the wrong number. Try again");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
