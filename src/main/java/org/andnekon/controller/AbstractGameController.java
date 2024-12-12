package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.GameView;
import org.andnekon.view.Reader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public abstract class AbstractGameController implements GameController {

    protected GameLogic game;

    protected GameView view;

    protected Reader reader;

    protected InputStream is;

    protected OutputStream os;

    public void run(final String line) {
        game.handleInput(line);
        view.display(game.getCurrentState());
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(is))) {
            view.display(game.getCurrentState());
            while (in != null) {
                game.handleInput(in.readLine());
                view.display(game.getCurrentState());
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }
}
