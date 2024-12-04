package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.game.state.State;
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
        controller.run();
    }

    private void run() {
        Reader reader = new ConsoleReader();
        while (true) {
            view.display();
            reader.consume();
            String input = reader.flush();
            game.handleInput(input);
        }
    }
}

