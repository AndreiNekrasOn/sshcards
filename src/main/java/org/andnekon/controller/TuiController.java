package org.andnekon.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.andnekon.game.GameLogic;
import org.andnekon.view.tui.TuiView;

public class TuiController extends AbstractGameController {

    public TuiController(GameLogic game, InputStream is, OutputStream os) throws IOException {
        this.game = game;
        this.view = new TuiView(game.getSession(), is, os);
        this.reader = (TuiView) this.view;
    }

    @Override
    public void run() {
        view.welcome();
    }
}

