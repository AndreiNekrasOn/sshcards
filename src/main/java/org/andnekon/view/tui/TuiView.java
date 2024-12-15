package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import org.andnekon.game.GameSession;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.Reader;
import org.andnekon.view.tui.components.MenuWindow;
import org.andnekon.view.tui.components.NavigationWindow;
import org.andnekon.view.tui.components.WelcomeWindow;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

/**
 * TUI view provides graphical (terminal) enviroment for game logic.<br>
 * It is different from {@code ConsoleRawView} and {@code ConsoleView} in a sence, that it provides
 * additional functionality besides standard REPL operations, like choosing specific buttons with
 * navigation keys, displaying help information as a pop-up menu and so on.
 */
public class TuiView extends AbstractGameView implements Reader {

    private Terminal terminal;
    private Screen screen;
    private StatefulMultiWindowTextGui gui;
    private AsciiReaderService asciiReaderService;

    // GUI

    private WelcomeWindow welcomeWindow;
    private MenuWindow menuWindow;
    private NavigationWindow navigationWindow;

    public TuiView(GameSession session, InputStream is, OutputStream os) throws IOException {
        this.session = session;
        this.terminal = new UnixTerminal(is, os, Charset.defaultCharset());
        screen = new TerminalScreen(terminal);
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();

        initializeGui();

        asciiReaderService = new AsciiReaderService();
    }

    private void initializeGui() {
        gui = new StatefulMultiWindowTextGui(screen);
        welcomeWindow = new WelcomeWindow(gui);
        menuWindow = new MenuWindow(gui);
        navigationWindow = new NavigationWindow(gui, session);
    }

    @Override
    public void welcome() {
        welcomeWindow.show();
    }

    @Override
    protected void showReward() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showReward'");
    }

    @Override
    protected void showQuitConfirm() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showQuitConfirm'");
    }

    @Override
    protected void showNavigation() {
        navigationWindow.show();
    }

    @Override
    protected void showMenu() {
        menuWindow.show();
    }

    @Override
    protected void showHelp() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showHelp'");
    }

    @Override
    protected void showDeath() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showDeath'");
    }

    @Override
    protected void showBattle() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showBattle'");
    }

    @Override
    public String read() {
        return gui.getCurrentWindow().read();
    }
}
