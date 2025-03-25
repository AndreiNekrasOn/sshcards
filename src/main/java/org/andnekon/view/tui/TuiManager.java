package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import org.andnekon.game.GameSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class TuiManager {

    private TuiView view;

    private TuiReader reader;

    private Screen screen;

    private String screenName;

    private GameSession session;

    public TuiManager(GameSession session, InputStream is, OutputStream os) throws IOException {
        UnixTerminal terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        this.session = session;
        this.screen = new TerminalScreen(terminal);
        this.view = new TuiView(session, this);
        this.reader = new TuiReader(this);
    }

    public Screen getScreen() {
        return this.screen;
    }

    public String getScreenName() {
        return this.screenName;
    }

    public TuiReader getReader() {
        return this.reader;
    }

    public TuiView getView() {
        return this.view;
    }

    public GameSession getSession() {
        return this.session;
    }

    public void processSpecialInput(String result) {
        this.view.setHelpShow(0);
        if ("?".equals(result)) {
            this.view.setHelpShow(1);
        } else if (session.isInBattle() && "b".equals(result)) {
            this.view.setHelpShow(2);
        }
    }
}
