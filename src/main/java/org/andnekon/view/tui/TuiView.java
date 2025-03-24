package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.game.manage.NavigationManager;
import org.andnekon.game.manage.RewardManager;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.tui.buffers.Battle;
import org.andnekon.view.tui.buffers.MainMenu;
import org.andnekon.view.tui.buffers.Navigation;
import org.andnekon.view.tui.buffers.Popup;
import org.andnekon.view.tui.buffers.Reward;
import org.andnekon.view.tui.buffers.Welcome;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Empty;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

/**
 * TUI view provides graphical (terminal) enviroment for game logic.<br>
 * It is different from {@code ConsoleRawView} and {@code ConsoleView} in a sence, that it provides
 * additional functionality besides standard REPL operations, like choosing specific buttons with
 * navigation keys, displaying help information as a pop-up menu and so on.
 */
public class TuiView extends AbstractGameView {

    private static final Logger logger = LoggerFactory.getLogger(TuiView.class);
    // TODO: make this a method depended on the state
    // private static final String help = "HELLO!";

    TuiManager manager;

    private Screen screen;
    private AsciiReaderService arSerivce;

    private final TerminalRegion region;

    // GUI

    private Widget welcomeWindow;
    private Widget menuWindow;
    private Widget navigationWindow;
    private Widget quitPopup;
    private Widget helpPopup;
    private Widget deathhPopup;
    private Widget rewardPopup;
    private Widget battleWindow;
    private Widget checkPopup;

    private boolean popup = false;

    TuiView(GameSession session, TuiManager manager) throws IOException {
        this.session = session;
        this.manager = manager;
        this.screen = manager.getScreen();
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();
        // hardcoded
        region = new TerminalRegion(2, 2, 140, 38);
        arSerivce = new AsciiReaderService();
    }

    @Override
    public void welcome() {
        welcomeWindow = new Welcome(screen.getTerminalSize(), arSerivce);
        welcomeWindow.draw(screen);
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void showReward() {
        popup = true;
        RewardManager rewardManager = session.getRewardManager();
        String[] resources =
                rewardManager.getRewardOptions().stream()
                        .map(c -> "tui/cards/" + c.getName())
                        .toList()
                        .toArray(String[]::new);
        // TODO: these are random values to test popup logic
        rewardPopup = new Reward(new TerminalRegion(10, 10, 60, 30), resources, arSerivce);
        rewardPopup = new Border(rewardPopup);
        rewardPopup.draw(screen);
    }

    @Override
    protected void showQuitConfirm() {
        final String quitLine = "Quit? y/n";
        int j = region.botRow() / 2;
        int i = region.rightCol() / 2;
        var quitRegion = new TerminalRegion(i, j, i + quitLine.length() + 2, j + 2);
        quitPopup = new Popup(quitRegion) {
            @Override
            protected List<Widget> widgets() {
                Widget empty = new Empty(quitRegion);
                Widget quit = new SingleLine(quitLine, quitRegion.getTopLeft());
                quit = new Border(quit)
                return List.of(empty, quit);
            }
        };
        quitPopup.draw(screen);
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
    protected void showDeath() {}

    @Override
    protected void showBattle() {
        battleWindow = new Battle(arSerivce, session.getBattleManager(), region);
        battleWindow.draw(screen);
    }

    @Override
    public void display(State state) {
        // TODO: make smarter with BufferManager
        super.display(state);
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
