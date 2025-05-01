package org.andnekon.server;

import org.andnekon.controller.GameController;
import org.andnekon.controller.GameControllerFactory;
import org.andnekon.utils.MonitoredOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer {

    private static final Logger logger = LoggerFactory.getLogger(TcpServer.class);

    public static void main(String[] args) throws IOException {
        // simple telnet server as a dummy
        try (ServerSocket socket = new ServerSocket(1234)) {
            while (true) {
                Socket client = socket.accept();
                new Thread(
                                () -> {
                                    handleClient(client);
                                    logger.info("handle client done");
                                })
                        .start();
            }
        }
    }

    private static void handleClient(Socket client) {
        try (MonitoredOutputStream mos = new MonitoredOutputStream(client.getOutputStream())) {
            GameController controller =
                    GameControllerFactory.createController(true, client.getInputStream(), mos);
            logger.info("Controller setup done");
            try {
                controller.run();
            } finally {
                logger.info("Total bytes sent: {}", mos.getByteCounter());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
