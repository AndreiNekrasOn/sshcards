package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.game.state.State.Type;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;

public class GameController {

    protected GameLogic game;

    protected GameView view;

    protected Reader reader;

    public GameController(GameLogic game, GameView view, Reader reader) {
        this.game = game;
        this.view = view;
        this.reader = reader;
    }

    public void run() {
        view.welcome();
        String in;
        do {
            view.display(game.getCurrentState());
            in = reader.read();
            processInput(in);
        } while (game.getCurrentState().getType() != Type.QUIT);
    }

    public void processInput(String in) {
        game.handleInput(in);
    }
}
