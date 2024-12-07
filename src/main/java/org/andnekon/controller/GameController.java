package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.game.state.State;
import org.andnekon.view.ConsoleRawView;
import org.andnekon.view.ConsoleView;
import org.andnekon.view.GameView;
import org.andnekon.view.reader.Reader;
import org.andnekon.view.reader.ConsoleRawReader;
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
        GameView view = new ConsoleRawView(game.getSession());
        GameController controller = new GameController(game, view);
        controller.run();
    }

    private void run() {
        Reader reader = new ConsoleRawReader(((ConsoleRawView) view).reader);
        while (true) {
            view.display(game.getCurrentState());
            String input = reader.read();
            game.handleInput(input);
        }
    }
}

