package com.sbt.ex1;

import com.sbt.ex1.bean.App;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.io.IOException;

@SpringBootApplication
public class MainApp {
    public static void main(String[] args) {
        try {
            App.init();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}