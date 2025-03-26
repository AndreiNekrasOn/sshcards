package org.andnekon.utils;

import java.io.IOException;
import java.io.OutputStream;

/** MonitoredOutputStream */
public class MonitoredOutputStream extends OutputStream {

    private final OutputStream os;
    private int byteCounter;

    public MonitoredOutputStream(OutputStream os) {
        this.os = os;
        byteCounter = 0;
    }

    @Override
    public void flush() throws IOException {
        this.os.flush();
    }

    @Override
    public void close() throws IOException {
        this.os.close();
    }

    // hope other writes just call this one...
    @Override
    public void write(int b) throws IOException {
        byteCounter++;
        os.write(b);
    }

    public int getByteCounter() {
        return byteCounter;
    }
}
