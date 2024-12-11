package org.andnekon.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.andnekon.controller.GameController;

public class TcpServer {

    public static void main(String[] args) throws IOException {
        // simple telnet server as a dummy
        try (ServerSocket socket = new ServerSocket(1234)) {
            while (true) {
                Socket client = socket.accept();
                new Thread(() -> {
                    try { handleClient(client); }
                    catch (IOException e) { e.printStackTrace(); }
                }).start();
            }
        }
    }

    private static void handleClient(Socket client) throws IOException {
        GameController controller = GameController.createController(1,
                client.getInputStream(), client.getOutputStream());
        controller.run();
    }


}
