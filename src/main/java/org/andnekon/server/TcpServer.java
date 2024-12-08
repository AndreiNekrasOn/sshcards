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
                // new Thread(() -> handleClient(client)).start();
                handleClient(client);
            }
        }
    }

    private static void handleClient(Socket client) {
        GameController controller = GameController.createController(1);
        try {
            controller.setupIO(client.getInputStream(), client.getOutputStream());
            controller.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //     out.print(controller.refresh());
        //     out.flush();
        //     while (true) {
        //         String line = in.readLine();
        //         if (line == null) {
        //             break;
        //         }
        //         out.print(controller.run(line));
        //         out.flush();
        //     }
        // } catch (IOException e) {
        //     e.printStackTrace();
        // }
    }


}
