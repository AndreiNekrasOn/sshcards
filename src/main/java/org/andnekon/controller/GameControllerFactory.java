package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.game.GameSession;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;
import org.andnekon.view.repl.ConsoleReader;
import org.andnekon.view.repl.ConsoleView;
import org.andnekon.view.tui.TuiManager;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class GameControllerFactory {

    public static GameController createController(boolean tui, InputStream is, OutputStream os)
            throws IOException {
        GameLogic game = new GameLogic();
        GameSession session = game.getSession();
        GameView view;
        Reader reader;
        if (tui) {
            TuiManager manager = new TuiManager(session, is, os);
            view = manager.getView();
            reader = manager.getReader();
        } else {
            view = new ConsoleView(session, os);
            reader = new ConsoleReader(is);
        }
        return new GameController(game, view, reader);
    }
}
