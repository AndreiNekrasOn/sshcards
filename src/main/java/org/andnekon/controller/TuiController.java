package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.Reader;
import org.andnekon.view.tui.TuiView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class TuiController extends AbstractGameController {

    public TuiController(GameLogic game, InputStream is, OutputStream os) throws IOException {
        this.game = game;
        this.view = new TuiView(game.getSession(), is, os);
        this.reader = (Reader) this.view;
        this.is = is;
        this.os = os;
    }

    // TODO: Maybe I don't need separate classes for each controller, since
    // only difference is that this controller also displays welcome?
    @Override
    public void run() {
        view.welcome(); // should run an animation and be quittable during it
        // TODO: copipasta
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
