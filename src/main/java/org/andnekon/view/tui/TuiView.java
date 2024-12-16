package org.andnekon.view.tui;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.ansi.UnixTerminal;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.Reader;
import org.andnekon.view.tui.windows.SimpleLabelPopupWindow;
import org.andnekon.view.tui.windows.TuiWindow;
import org.andnekon.view.tui.windows.impl.BattleWindow;
import org.andnekon.view.tui.windows.impl.MainMenuWindow;
import org.andnekon.view.tui.windows.impl.NavigationWindow;
import org.andnekon.view.tui.windows.impl.QuitConfirmation;
import org.andnekon.view.tui.windows.impl.WelcomeWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

/**
 * TUI view provides graphical (terminal) enviroment for game logic.<br>
 * It is different from {@code ConsoleRawView} and {@code ConsoleView} in a sence, that it provides
 * additional functionality besides standard REPL operations, like choosing specific buttons with
 * navigation keys, displaying help information as a pop-up menu and so on.
 */
public class TuiView extends AbstractGameView implements Reader {

    private static final Logger logger = LoggerFactory.getLogger(TuiView.class);

    private Terminal terminal;
    private Screen screen;
    private StatefulMultiWindowTextGui gui;
    private AsciiReaderService asciiReaderService;

    // GUI

    private TuiWindow welcomeWindow;
    private TuiWindow menuWindow;
    private TuiWindow navigationWindow;
    private TuiWindow quitPopup;
    private TuiWindow helpPopup;
    private TuiWindow deathhPopup;
    private TuiWindow rewardPopup;
    private TuiWindow battleWindow;

    private boolean isHelpShown = false;

    private boolean refresh;

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
        TextColor.ANSI none = TextColor.ANSI.DEFAULT; // to not type this out everytime
        gui.setTheme(SimpleTheme.makeTheme(true, none, none, none, none, none, none, none));

        welcomeWindow = new WelcomeWindow(gui);
        menuWindow = new MainMenuWindow(gui);
        navigationWindow = new NavigationWindow(gui, session);
        battleWindow = new BattleWindow(gui, asciiReaderService, session);

        quitPopup = new QuitConfirmation(gui);
        deathhPopup = new SimpleLabelPopupWindow(gui, "You died");
        rewardPopup = new SimpleLabelPopupWindow(gui, "Congrats, good job");
        helpPopup = new SimpleLabelPopupWindow(gui, "Help messsage");
    }

    @Override
    public void welcome() {
        welcomeWindow.show();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showReward() {
        rewardPopup.show();
    }

    @Override
    protected void showQuitConfirm() {
        quitPopup.show();
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
        helpPopup.show();
    }

    @Override
    protected void showDeath() {
        deathhPopup.show();
    }

    @Override
    protected void showBattle() {
        battleWindow.show();
    }

    @Override
    public String read() {
        String result = gui.getCurrentWindow().read();
        if (Objects.equals(result, "?")) {
            isHelpShown = true;
            return null;
        } else if (isHelpShown) {
            isHelpShown = false;
            return null;
        }
        if (Objects.equals(result, "r")) {
            refresh = true;
            return null;
        }
        return result;
    }

    @Override
    public void display(State state) {
        logger.info("Display for {}, gui has {} windows", state, gui.getWindows().size());
        if (refresh) {
            screen.clear();
            refresh = false;
        }
        if (isHelpShown) {
            showHelp();
        } else {
            super.display(state);
        }
    }
}
