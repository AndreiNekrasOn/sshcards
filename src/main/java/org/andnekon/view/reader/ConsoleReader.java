package org.andnekon.view.reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleReader implements Reader {

    Scanner scanner;

    String buffer;

    public ConsoleReader() {
        this.scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Override
    public void consume() {
        buffer = scanner.nextLine();
    }

    @Override
    public String flush() {
        return buffer;
    }
}

