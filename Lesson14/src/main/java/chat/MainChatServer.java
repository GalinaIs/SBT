package chat;

import com.sbt.lesson14.chat.PoolServersImpl;

import java.io.*;
public class MainChatServer {
    public static void main(String[] args) throws IOException {
        new PoolServersImpl(1234).start();
    }
}