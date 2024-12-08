package org.andnekon.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import org.andnekon.controller.GameController;
import org.andnekon.game.GameLogic;
import org.andnekon.view.ConsoleView;
import org.andnekon.view.GameView;
import org.andnekon.view.reader.ConsoleReader;
import org.andnekon.view.reader.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TelnetServer {

    private static final Logger logger = LoggerFactory.getLogger(TelnetServer.class);

    public static void main(String[] args) throws IOException {
        // simple telnet server as a dummy
        try (ServerSocket socket = new ServerSocket(1234)) {
            // while (true) {
            Socket client = socket.accept();
            // new Thread(() -> handleClient(client)).start();
            handleClient(client);
            // }
        }
    }

    private static void handleClient(Socket client) {
        try (PrintWriter out = new PrintWriter(client.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            GameLogic game = new GameLogic();
            GameView view = new ConsoleView(game.getSession());
            Reader reader = new ConsoleReader();
            GameController controller = new GameController(game, view, reader);
            out.print(controller.refresh());
            out.flush();
            while (true) {
                String line = in.readLine();
                if (line == null) {
                    break;
                }
                out.print(controller.run(line));
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
