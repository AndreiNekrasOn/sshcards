package org.andnekon.server;

import org.andnekon.controller.GameController;
import org.andnekon.controller.GameControllerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    public static void main(String[] args) throws IOException {
        // simple telnet server as a dummy
        try (ServerSocket socket = new ServerSocket(1234)) {
            // while (true) {
            Socket client = socket.accept();
            // new Thread(() -> {
            //     try {
            handleClient(client);
            logger.info("handle client done");
            // }
            // catch (IOException e) { e.printStackTrace(); }
            // }).start();
            // }
        }
    }

    private static void handleClient(Socket client) throws IOException {
        MonitoredOutputStream mos = new MonitoredOutputStream(client.getOutputStream());
        GameController controller =
                GameControllerFactory.createController(true, client.getInputStream(), mos);
        logger.info("Controller setup done");
        try {
            controller.run();
        } finally {
            logger.info("Total bytes sent: {}", mos.getByteCounter());
        }
    }

    public static class MonitoredOutputStream extends OutputStream {

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
}
