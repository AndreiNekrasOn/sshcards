package org.andnekon.controller;

import org.andnekon.game.GameLogic;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GameControllerFactory {

    public enum ControllerType {
        REPL,
        RAW,
        TUI
    }

    public static GameController createController(
            ControllerType type, InputStream is, OutputStream os) throws IOException {
        GameLogic game = new GameLogic();
        return switch (type) {
            case RAW -> new RawController(game, is, os);
            case REPL -> new ReplController(game);
            case TUI -> new TuiController(game, is, os);
        };
    }
}
