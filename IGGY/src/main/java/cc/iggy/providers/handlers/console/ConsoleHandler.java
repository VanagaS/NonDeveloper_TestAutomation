package cc.iggy.providers.handlers.console;

import cc.iggy.api.TokenHandler;

import java.io.Closeable;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

/**
 * Created by VanagaS on 18-07-2017.
 *
 */
public class ConsoleHandler implements TokenHandler, Serializable, Closeable {

    private Scanner scanner = new Scanner(System.in);

    public ConsoleHandler() {
        String input = "";
        while (!input.equalsIgnoreCase("quit")) {
            input = getLine("");
            System.out.println("New input found: " + input);
        }
    }

    @Override
    public String getLine(String variable) {
        return scanner.nextLine();
    }

    @Override
    public String getLine(String path, String variable) {
        return null;
    }

    @Override
    public void close() throws IOException {

    }
}
