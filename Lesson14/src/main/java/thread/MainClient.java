package thread;

import com.sbt.lesson14.threadInteraction.ThreadClient;

import java.io.IOException;

public class MainClient {
    public static void main(String[] args) {
        try {
            ThreadClient.start("localhost", 1234);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
