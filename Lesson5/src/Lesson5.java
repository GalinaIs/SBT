import com.sbt.javaschool.lesson5.*;

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Lesson5 {
    public static void main(String[] args) {
        //testTerminal();
        testUrl();
    }

    private static void testTerminal() {
        try {
            TerminalImpl terminal = new TerminalImpl(5000, "1111");
            terminal.work();
        } catch (PinValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private static void testUrl() {
        boolean isRead = false;
        Scanner in = new Scanner(System.in);
        String urlString = "";
        while (!isRead) {
            try {
                System.out.print("Введите URL ресурса: ");
                urlString = in.nextLine();
                readContent(urlString);
                isRead = true;
            } catch (MalformedURLException ex) {
                System.out.println("Не удалось получить доступ к URL: " + urlString);
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void readContent(String urlString) throws MalformedURLException {
        URL url = new URL(urlString);
        try (InputStream in = new BufferedInputStream(url.openStream()); Reader r = new InputStreamReader(in)) {
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
