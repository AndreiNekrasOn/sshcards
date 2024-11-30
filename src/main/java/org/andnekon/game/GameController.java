package org.andnekon.game;

import org.andnekon.ui.ConsoleView;
import org.andnekon.ui.GameView;
import org.andnekon.ui.Reader;
import org.andnekon.ui.console.ConsoleReader;

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

