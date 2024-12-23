package org.andnekon.view.repl;

import org.andnekon.view.Reader;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleReader implements Reader {

    Scanner scanner;

    public ConsoleReader(InputStream is) {
        this.scanner = new Scanner(new BufferedReader(new InputStreamReader(is)));
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
