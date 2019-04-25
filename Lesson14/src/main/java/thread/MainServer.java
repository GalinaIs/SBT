package thread;

import com.sbt.lesson14.threadInteraction.ThreadServer;

import java.io.*;
import java.net.ServerSocket;
import java.util.concurrent.*;

public class MainServer {

    public static void main(String[] args) throws IOException {
        int countThread = 1;//меняем на 10 - получаем допустимы одновременно не более 10 соединений
        ExecutorService executorService = Executors.newFixedThreadPool(countThread);
        ServerSocket serverSocket = new ServerSocket(1234);
        for (int i = 0; i < countThread; i++) {
            executorService.submit(new ThreadServer(serverSocket));
        }

    }
}