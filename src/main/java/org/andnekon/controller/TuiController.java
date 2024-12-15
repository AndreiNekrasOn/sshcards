package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.Reader;
import org.andnekon.view.tui.TuiView;

import java.io.IOException;
import java.io.InputStream;
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
    // only difference is that this controller also displays welcome (and constructor ofc)?
    @Override
    public void run() {
        view.welcome(); // should run an animation and be quittable during it
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        view.display(game.getCurrentState());
        String in = null;
        while (in != "q") { // TODO: KeyStrokeUtil.isQuit()
            in = reader.read();
            game.handleInput(in);
            view.display(game.getCurrentState());
        }
    }
}
