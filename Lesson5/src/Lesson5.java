import com.sbt.javaschool.lesson5.*;

public class Lesson5 {
    public static void main(String[] args) {
        try {
            TerminalImpl terminal = new TerminalImpl(5000, "1111");
            terminal.work();
        } catch (PinValidatorException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
