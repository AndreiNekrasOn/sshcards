package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.ConsoleView;
import org.andnekon.view.GameView;
import org.andnekon.view.reader.Reader;
import org.andnekon.view.reader.ConsoleReader;

public class GameController {

    private final GameLogic game;

    private final GameView view;

    public GameController(GameLogic game, GameView view) {
        this.game = game;
        this.view = view;
    }

    public static void main( String[] args ) {
        GameLogic game = new GameLogic();
        GameView view = new ConsoleView(game);
        GameController controller = new GameController(game, view);
        controller.start();

        Reader reader = new ConsoleReader();

        while (true) {
            reader.consume();
            String input = reader.flush();
            game.handleInput(input);
            view.display();
        }
    }

    private void start() {
        view.welcome();
        view.display();
    }
}

