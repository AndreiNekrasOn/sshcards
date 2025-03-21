package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.game.manage.NavigationManager;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.tui.buffers.MainMenu;
import org.andnekon.view.tui.buffers.Navigation;
import org.andnekon.view.tui.buffers.Welcome;
import org.andnekon.view.tui.windows.TuiWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;

/**
 * TUI view provides graphical (terminal) enviroment for game logic.<br>
 * It is different from {@code ConsoleRawView} and {@code ConsoleView} in a sence, that it provides
 * additional functionality besides standard REPL operations, like choosing specific buttons with
 * navigation keys, displaying help information as a pop-up menu and so on.
 */
public class TuiView extends AbstractGameView {

    private static final Logger logger = LoggerFactory.getLogger(TuiView.class);

    TuiManager manager;

    private Screen screen;
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
    private TuiWindow checkPopup;

    private boolean helpShown = false;

    private boolean refresh;

    private boolean check;

    TuiView(GameSession session, TuiManager manager) throws IOException {
        this.session = session;
        this.manager = manager;
        this.screen = manager.getScreen();
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();

        init();

        asciiReaderService = new AsciiReaderService();
    }

    public void init() {
    }

    @Override
    public void welcome() {
        Welcome wb = new Welcome(screen.getTerminalSize(), asciiReaderService);
        wb.draw(screen);
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
        NavigationManager manager = session.getNavigationManager();
        TerminalRegion fullScreen = new TerminalRegion(screen.getTerminalSize());
        String[] options = manager.getNavigationOptionsArray();
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%d. %s", i, options[i]);
        }
        Navigation nb = new Navigation(fullScreen, options);
        nb.draw(screen);
    }

    @Override
    protected void showMenu() {
        MainMenu mb = new MainMenu(screen.getTerminalSize());
        mb.draw(screen);
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
    public void display(State state) {
        // TODO: make smarter with BufferManager
        // screen.clear();
        if (refresh) {
            refresh = false;
        }
        if (helpShown) {
            helpPopup.show();
        } else if (check) {
            checkPopup.show();
        } else {
            super.display(state);
        }
    }

    public void setHelpShown(boolean show) {
        this.helpShown = show;
    }

    public boolean isHelpShown() {
        return this.helpShown;
    }

    public void setRefresh() {
        this.refresh = true;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public boolean isCheck() {
        return this.check;
    }
}
