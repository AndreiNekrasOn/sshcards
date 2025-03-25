package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.game.manage.NavigationManager;
import org.andnekon.game.manage.RewardManager;
import org.andnekon.game.state.State;
import org.andnekon.view.AbstractGameView;
import org.andnekon.view.tui.buffers.Battle;
import org.andnekon.view.tui.buffers.Help;
import org.andnekon.view.tui.buffers.MainMenu;
import org.andnekon.view.tui.buffers.Navigation;
import org.andnekon.view.tui.buffers.Popup;
import org.andnekon.view.tui.buffers.Reward;
import org.andnekon.view.tui.buffers.TabGroup;
import org.andnekon.view.tui.buffers.Welcome;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;

/**
 * TUI view provides graphical (terminal) enviroment for game logic.<br>
 * It is different from {@code ConsoleRawView} and {@code ConsoleView} in a sence, that it provides
 * additional functionality besides standard REPL operations, like choosing specific buttons with
 * navigation keys, displaying help information as a pop-up menu and so on.
 */
public class TuiView extends AbstractGameView {

    TuiManager manager;

    private Screen screen;
    private AsciiReaderService arSerivce;

    private static final TerminalRegion region = new TerminalRegion(2, 2, 140, 38);
    private static final int halfCol = (140 - 2) / 2;
    private static final int halfRow = (38 - 2) / 2;

    // GUI

    private TabGroup current;
    private TabGroup battleWindow;
    private boolean helpShow;

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
        current = new TabGroup(new Welcome(screen.getTerminalSize(), arSerivce));
        current.draw(screen);
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
        Widget rewardPopup =
                new Reward(
                        new TerminalRegion(halfCol, halfRow, halfCol, halfRow),
                        resources,
                        arSerivce);
        rewardPopup = new Border(rewardPopup);
        current = new TabGroup(rewardPopup);
    }

    @Override
    protected void showQuitConfirm() {
        final String quitLine = "Quit? y/n";
        current =
                new TabGroup(
                        new Popup(
                                new TerminalRegion(
                                        halfCol,
                                        halfRow,
                                        halfCol + quitLine.length() + 2,
                                        halfRow + 2),
                                quitLine));
    }

    @Override
    protected void showNavigation() {
        NavigationManager manager = session.getNavigationManager();
        TerminalRegion fullScreen = new TerminalRegion(screen.getTerminalSize());
        String[] options = manager.getNavigationOptionsArray();
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%d. %s", i + 1, options[i]);
        }
        current = new TabGroup(new Navigation(fullScreen, options));
    }

    @Override
    protected void showMenu() {
        current = new TabGroup(new MainMenu(screen.getTerminalSize()));
    }

    @Override
    protected void showDeath() {
        final String dedLine = "You ded. Again? y/n";
        current =
                new TabGroup(
                        new Popup(
                                new TerminalRegion(
                                        halfCol,
                                        halfRow,
                                        halfCol + dedLine.length() + 2,
                                        halfRow + 2),
                                dedLine));
    }

    @Override
    protected void showBattle() {
        Widget battleTab = new Battle(arSerivce, session.getBattleManager(), region);
        Help battleHelp = new Help(region, "Battle help");
        battleHelp.addSingle("Each turn you draw 6 cards: 3 shots and 3 skills");
        battleHelp.addSingle("You get 3 energy to play all of them");
        battleHelp.addSingle(
                "Card cost is displayed somewhere on the card, as well as description");
        battleHelp.addSingle(
                "At the right you can see what the enemy will do. They'll attack, duh");
        battleHelp.addSingle("If your health gets below 0, you lose");
        battleHelp.addSingle("Your final score is the total overkill you get on the enemies");
        battleHelp.addSingle("Good lick");
        battleWindow = new TabGroup(battleTab, battleHelp);
        current = battleWindow;
    }

    @Override
    public void display(State state) {
        // TODO: make smarter with BufferManager
        if (helpShow) {
            battleWindow.at(1).draw(screen);
        } else {
            super.display(state);
            current.draw(screen);
        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setHelpShow(boolean helpShow) {
        this.helpShow = helpShow;
    }
}
