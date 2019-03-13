package com.sbt.javaschool.lesson5;
import java.util.Scanner;

interface ViewUserI {
    String response();
    void print(String message);
}

public class ViewUserConsole implements ViewUserI {
    private Scanner in;

    ViewUserConsole() {
        in = new Scanner(System.in);
    }

    /**
     * Вывод инфорации пользователю на консоль
     * @param message Выводимое сообщение
     */
    public void print(String message) {
        System.out.println("-----------------------------------------------------------------------------------------");
        System.out.println(message);
        System.out.println("-----------------------------------------------------------------------------------------");
    }

    /**
     * Запрос ответа от пользователя
     * @return Ответ пользователя
     */
    public String response() {
        return in.nextLine();
    }
}
