package org.andnekon.view.reader;

import org.jline.reader.LineReader;

public class ConsoleRawReader implements Reader {

    private final LineReader reader;

    public ConsoleRawReader(LineReader reader) {
        this.reader = reader;
    }

    @Override
    public String read() {
        return reader.readLine();
    }
}

