package otus.amogilevskiy.spring.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;

@Service
public class ConsoleIOService implements IOService {

    private final PrintStream out;

    public ConsoleIOService(@Value("#{T(java.lang.System).out}") PrintStream out) {
        this.out = out;
    }

    @Override
    public void out(String text) {
        out.println(text);
    }

}
