package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.tui.windows.SimpleLabelPopupWindow;
import org.andnekon.view.tui.windows.TuiWindow;
import org.andnekon.view.tui.windows.impl.BattleWindow;
import org.andnekon.view.tui.windows.impl.CheckWindow;
import org.andnekon.view.tui.windows.impl.MainMenuWindow;
import org.andnekon.view.tui.windows.impl.NavigationWindow;
import org.andnekon.view.tui.windows.impl.QuitConfirmation;
import org.andnekon.view.tui.windows.impl.RewardWindow;
import org.andnekon.view.tui.windows.impl.WelcomeWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
    private TuiWindow checkPopup;

    private boolean helpShown = false;

    private boolean refresh;

    private boolean check;

    TuiView(GameSession session, TuiManager manager) throws IOException {
        this.session = session;
        this.manager = manager;
        this.gui = manager.getGui();
        this.screen = gui.getScreen();
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();

        init();

        asciiReaderService = new AsciiReaderService();
    }

    public void init() {
        menuWindow = new MainMenuWindow(gui);
        navigationWindow = new NavigationWindow(gui, session);
        battleWindow = new BattleWindow(gui, asciiReaderService, session);

        welcomeWindow = new WelcomeWindow(gui);
        rewardPopup = new RewardWindow(gui, session);
        quitPopup = new QuitConfirmation(gui);
        deathhPopup = new SimpleLabelPopupWindow(gui, "You died. Continue? [y/n]");
        helpPopup = new SimpleLabelPopupWindow(gui, "Help messsage");
        checkPopup = new CheckWindow(gui, session);
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
    protected void showDeath() {
        deathhPopup.show();
    }

    @Override
    protected void showBattle() {
        battleWindow.show();
    }

    @Override
    public void display(State state) {
        if (refresh) {
            screen.clear();
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
