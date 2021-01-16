package otus.amogilevskiy.spring.service.io;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

@Service
public class ConsoleIOService implements IOService {

    private final PrintStream out;

    private final InputStream in;

    private final Scanner scanner;

    public ConsoleIOService(@Value("#{T(java.lang.System).out}") PrintStream out,
                            @Value("#{T(java.lang.System).in}") InputStream in) {
        this.out = out;
        this.in = in;
        this.scanner = new Scanner(in);
    }

    @Override
    public void out(String text) {
        out.println(text);
    }

    @Override
    public String in() {
        return scanner.nextLine();
    }
}
