package org.andnekon.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.andnekon.game.GameLogic;
import org.andnekon.view.ConsoleRawView;
import org.andnekon.view.ConsoleView;
import org.andnekon.view.GameView;
import org.andnekon.view.reader.ConsoleReader;
import org.andnekon.view.reader.Reader;

public class GameController {

    private final GameLogic game;

    private final GameView view;

    private Reader reader;

    private InputStream is;

    private OutputStream os;

    private GameController(GameLogic game, GameView view, Reader reader) {
        this.game = game;
        this.view = view;
        this.reader = reader;
    }

    // TODO: type -> enum (-> controller factory / inheritance)
    public static GameController createController(int type, InputStream is, OutputStream os) {
        GameController controller;
        GameLogic game = new GameLogic();
        GameView view;
        Reader reader;
        if (type == 0) {
            view = new ConsoleView(game.getSession());
            reader = new ConsoleReader();
            controller = new GameController(game, view, reader);
        } else if (type == 1) {
            try {
                view = new ConsoleRawView(game.getSession(), is, os);
                reader = (Reader) view;
            } catch (IOException e) {
                e.printStackTrace();
                view = new ConsoleView(game.getSession());
                reader = new ConsoleReader();
            }
            controller = new GameController(game, view, reader);
            controller.is = is;
            controller.os = os;
        } else {
            throw new UnsupportedOperationException();
        }
        return controller;
    }

    public void run(String line) {
        game.handleInput(line);
        view.display(game.getCurrentState());
    }

    public void run() {
        try(BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            view.display(game.getCurrentState());
            while(in != null) {
                // TODO: `game.handleInput(reader.read());` doesn't work for now
                game.handleInput(in.readLine());
                view.display(game.getCurrentState());
            }
        } catch (IOException ignored) {}
    }
}

