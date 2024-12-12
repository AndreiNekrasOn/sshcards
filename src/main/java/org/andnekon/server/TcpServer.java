package org.andnekon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.andnekon.controller.GameController;
import org.andnekon.controller.GameControllerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
        GameController controller = GameControllerFactory.createController(
                GameControllerFactory.ControllerType.TUI,
                client.getInputStream(), client.getOutputStream());
        logger.info("Controller setup done");
        controller.run();
    }
}
