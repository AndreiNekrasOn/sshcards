package org.andnekon.controller;

import java.io.IOException;

import org.andnekon.game.GameLogic;
import org.andnekon.view.ConsoleRawView;
import org.andnekon.view.ConsoleView;
import org.andnekon.view.GameView;
import org.andnekon.view.reader.ConsoleReader;
import org.andnekon.view.reader.Reader;

public class GameController {

    public static void main( String[] args ) {
        GameLogic game = new GameLogic();
        GameView view;
        Reader reader;
        try {
            view = new ConsoleRawView(game.getSession());
            reader = (ConsoleRawView) view;
        } catch (IOException e) {
            e.printStackTrace();
            view = new ConsoleView(game.getSession());
            reader = new ConsoleReader();
        }
        GameController controller = new GameController(game, view, reader);
        controller.run();
    }

    private final GameLogic game;

    private final GameView view;

    private Reader reader;

    public GameController(GameLogic game, GameView view, Reader reader) {
        this.game = game;
        this.view = view;
        this.reader = reader;
    }

    private void run() {
        while (true) {
            view.display(game.getCurrentState());
            String input = reader.read();
            game.handleInput(input);
        }
    }
}

