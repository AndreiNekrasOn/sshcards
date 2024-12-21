package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;
import org.andnekon.view.raw.ConsoleRawView;
import org.andnekon.view.repl.ConsoleReader;
import org.andnekon.view.repl.ConsoleView;
import org.andnekon.view.tui.TuiView;

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
        GameView view;
        Reader reader;
        switch (type) {
            case RAW -> {
                view = new ConsoleRawView(game.getSession(), is, os);
                reader = (ConsoleRawView) view;
            }
            case REPL -> {
                view = new ConsoleView(game.getSession());
                reader = new ConsoleReader(); // TODO: IO
            }
            case TUI -> {
                view = new TuiView(game.getSession(), is, os);
                reader = (TuiView) view;
            }
            default -> throw new UnsupportedOperationException("ControllerType not implemented");
        }
        ;
        return new GameController(game, view, reader);
    }
}
