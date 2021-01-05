package otus.amogilevskiy.spring.service;

import java.io.PrintStream;

public class ConsoleIOService implements IOService {

    private final PrintStream out;

    public ConsoleIOService(PrintStream out) {
        this.out = out;
    }

    @Override
    public void out(String text) {
        out.println(text);
    }

}
