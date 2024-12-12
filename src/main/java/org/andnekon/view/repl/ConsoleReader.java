package org.andnekon.view.repl;

import org.andnekon.view.Reader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class ConsoleReader implements Reader {

    Scanner scanner;

    public ConsoleReader() {
        this.scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
    }

    @Override
    public String read() {
        return scanner.nextLine();
    }
}
