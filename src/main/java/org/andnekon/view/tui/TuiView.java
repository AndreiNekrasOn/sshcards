package org.andnekon.view.tui;

import java.io.IOException;

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
import org.andnekon.view.tui.widgets.Widget;
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

    // TODO: make this a method depended on the state
    // private static final String help = "HELLO!";

    TuiManager manager;

    private Screen screen;
    private AsciiReaderService arSerivce;

    private static final TerminalRegion region = new TerminalRegion(2, 2, 140, 38);
    private static final int halfCol = (140 - 2) / 2;
    private static final int halfRow = (38 - 2) / 2;

    // GUI

    private Widget welcomeWindow;
    private Widget menuWindow;
    private Widget navigationWindow;
    private Widget quitPopup;
    private Widget helpPopup;
    private Widget deathhPopup;
    private Widget rewardPopup;
    private Widget battleWindow;

    TuiView(GameSession session, TuiManager manager) throws IOException {
        this.session = session;
        this.manager = manager;
        this.screen = manager.getScreen();
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();
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
        quitPopup = new Popup(new TerminalRegion(halfCol, halfRow,
                    halfCol + quitLine.length() + 2, halfRow + 2), quitLine);
        quitPopup.draw(screen);
    }

    @Override
    protected void showNavigation() {
        NavigationManager manager = session.getNavigationManager();
        TerminalRegion fullScreen = new TerminalRegion(screen.getTerminalSize());
        String[] options = manager.getNavigationOptionsArray();
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%d. %s", i + 1, options[i]);
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
        final String dedLine = "You ded. Again? y/n";
        deathhPopup = new Popup(new TerminalRegion(halfCol, halfRow,
                    halfCol + dedLine.length() + 2, halfRow + 2), dedLine);
        deathhPopup.draw(screen);
    }

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
