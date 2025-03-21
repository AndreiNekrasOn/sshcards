package org.andnekon.view.tui;

import java.io.IOException;
import java.util.stream.Collectors;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.manage.CardManager;
import org.andnekon.game.manage.NavigationManager;
import org.andnekon.game.manage.RewardManager;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.tui.buffers.Battle;
import org.andnekon.view.tui.buffers.Buffer;
import org.andnekon.view.tui.buffers.MainMenu;
import org.andnekon.view.tui.buffers.Navigation;
import org.andnekon.view.tui.buffers.Reward;
import org.andnekon.view.tui.buffers.Welcome;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.googlecode.lanterna.screen.Screen;

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
    private AsciiReaderService arSerivce;

    // GUI

    private Buffer welcomeWindow;
    private Buffer menuWindow;
    private Buffer navigationWindow;
    private Buffer quitPopup;
    private Buffer helpPopup;
    private Buffer deathhPopup;
    private Buffer rewardPopup;
    private Buffer battleWindow;
    private Buffer checkPopup;

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

        arSerivce = new AsciiReaderService();
    }

    public void init() {
    }

    @Override
    public void welcome() {
        welcomeWindow = new Welcome(screen.getTerminalSize(), arSerivce);
        welcomeWindow.draw(screen);
    }

    @Override
    protected void showReward() {
        RewardManager rewardManager = session.getRewardManager();
        String[] resources = rewardManager.getRewardOptions().stream()
            .map(Card::getName).toList().toArray(String[]::new);
        rewardPopup = new Reward(new TerminalRegion(20, 20, 20, 20), resources, arSerivce);
        rewardPopup.draw(screen);
    }

    @Override
    protected void showQuitConfirm() {
    }

    @Override
    protected void showNavigation() {
        NavigationManager manager = session.getNavigationManager();
        TerminalRegion fullScreen = new TerminalRegion(screen.getTerminalSize());
        String[] options = manager.getNavigationOptionsArray();
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%d. %s", i, options[i]);
        }
        navigationWindow = new Navigation(fullScreen, options);
        navigationWindow.draw(screen);
    }

    @Override
    protected void showMenu() {
        menuWindow = new MainMenu(screen.getTerminalSize());
        menuWindow.draw(screen);
    }

    @Override
    protected void showDeath() {
    }

    @Override
    protected void showBattle() {
        battleWindow = new Battle(arSerivce, new TerminalRegion(screen.getTerminalSize()), session.getBattleManager());
        battleWindow.draw(screen);
    }

    @Override
    public void display(State state) {
        // TODO: make smarter with BufferManager
        // screen.clear();
        if (refresh) {
            refresh = false;
        }
        if (helpShown) {
            // helpPopup.show();
        } else if (check) {
            // checkPopup.show();
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
