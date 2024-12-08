package org.andnekon.controller;

import java.io.IOException;

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

    public GameController(GameLogic game, GameView view, Reader reader) {
        this.game = game;
        this.view = view;
        this.reader = reader;
    }

    public void run() {
        while (true) {
            view.display(game.getCurrentState());
            String input = reader.read();
            game.handleInput(input);
        }
    }

    public String run(String line) {
        game.handleInput(line);
        view.display(game.getCurrentState());
        return new String(view.prepare());
    }

    public String refresh() {
        view.display(game.getCurrentState());
        return new String(view.prepare());
    }
}

