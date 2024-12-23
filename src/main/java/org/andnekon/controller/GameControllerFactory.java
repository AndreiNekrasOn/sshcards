package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.game.GameSession;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;
import org.andnekon.view.raw.ConsoleRawView;
import org.andnekon.view.repl.ConsoleReader;
import org.andnekon.view.repl.ConsoleView;
import org.andnekon.view.tui.TuiManager;

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
        GameSession session = game.getSession();
        GameView view;
        Reader reader;
        switch (type) {
            case RAW -> {
                view = new ConsoleRawView(session, is, os);
                reader = (ConsoleRawView) view;
            }
            case REPL -> {
                view = new ConsoleView(session, os);
                reader = new ConsoleReader(is); // TODO: IO
            }
            case TUI -> {
                TuiManager manager = new TuiManager(session, is, os);
                view = manager.getView();
                reader = manager.getReader();
            }
            default -> throw new UnsupportedOperationException("ControllerType not implemented");
        }
        ;
        return new GameController(game, view, reader);
    }
}
