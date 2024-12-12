package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.repl.ConsoleReader;
import org.andnekon.view.repl.ConsoleView;

public class ReplController extends AbstractGameController {

    public ReplController(GameLogic game) {
        this.game = game;
        this.view = new ConsoleView(game.getSession());
        this.reader = new ConsoleReader();
    }
}
