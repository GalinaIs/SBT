import com.sbt.lesson16.Outer;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        //testEx1();
        testEx2();
    }

    private static void testEx2() {
        int count = 0;
        while (true) {
            System.out.println(count++);
            new Outer().register();
        }
    }

    private static void testEx1() {
        Map<Integer, String> map = new HashMap<>();
        int countSteps = 100000;
        for (int i = 0; i < countSteps; i++) {
            map.put(i, "value" + i);
        }
    }
}
