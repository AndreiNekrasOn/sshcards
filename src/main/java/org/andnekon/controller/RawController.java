package org.andnekon.controller;

import org.andnekon.game.GameLogic;
import org.andnekon.view.raw.ConsoleRawView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class RawController extends AbstractGameController {

    public RawController(GameLogic game, InputStream is, OutputStream os) throws IOException {
        this.game = game;
        this.view = new ConsoleRawView(game.getSession(), is, os);
        this.reader = (ConsoleRawView) view;
        this.is = is;
        this.os = os;
    }
}
