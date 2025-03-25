package org.andnekon.view.tui;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.action.CardFactory;
import org.andnekon.game.entity.enemy.EnemyFactory;
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
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.TopBotLine;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private static final TerminalRegion paddedRegion = new TerminalRegion(4, 4, 138, 36);
    private static final int halfCol = (140 - 2) / 2;
    private static final int halfRow = (38 - 2) / 2;

    private static final String HELP_GENERAL = "q:quit, ?:help, ";
    private static final String HELP_REWARD = HELP_GENERAL + "<1-3> choose reward";
    private static final String HELP_NAVIGATION = HELP_GENERAL + "<1-2> choose path";
    private static final String HELP_BATTLE =
            HELP_GENERAL
                    + "m:toggle missile; "
                    + "<1-6>:play card; "
                    + "b:how to battle; "
                    + "d:view cards; "
                    + "w:change target; "
                    + "a:view artifacts";

    // GUI

    private TabGroup current;
    private TabGroup battleWindow;
    private int helpShow;

    private Help helpWindow;

    TuiView(GameSession session, TuiManager manager) throws IOException {
        this.session = session;
        this.manager = manager;
        this.screen = manager.getScreen();
        // single threaded is fine, we have 1 gui per client
        screen.startScreen();
        arSerivce = new AsciiReaderService();
        initHelWindow();
    }

    public void initHelWindow() {
        helpWindow = new Help(region, "HELP");
        helpWindow.addMultiline(
                "Useful helpful message, that fits in the screen\n"
                        + "Some of the features are not implemented yet",
                80);
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
        rewardPopup = new TopBotLine(rewardPopup, region, session, HELP_REWARD);
        current = new TabGroup(rewardPopup, helpWindow);
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
                                quitLine),
                        helpWindow);
    }

    @Override
    protected void showNavigation() {
        NavigationManager manager = session.getNavigationManager();
        TerminalRegion fullScreen = new TerminalRegion(halfCol, halfRow, halfCol, halfRow);
        String[] options = manager.getNavigationOptionsArray();
        for (int i = 0; i < options.length; i++) {
            options[i] = String.format("%d. %s", i + 1, options[i]);
        }
        Widget navigation = new Navigation(fullScreen, options);
        navigation = new TopBotLine(navigation, region, session, HELP_NAVIGATION);
        current = new TabGroup(navigation, helpWindow);
    }

    @Override
    protected void showMenu() {
        Widget mainMenu = new MainMenu(screen.getTerminalSize());
        Help about = new Help(region, "ABOUT");
        about.addSingle("Just a hobby project");
        current = new TabGroup(mainMenu, helpWindow, about);
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
                                dedLine),
                        helpWindow);
    }

    @Override
    protected void showBattle() {
        Widget battleTab = new Battle(arSerivce, session.getBattleManager(), paddedRegion);
        battleTab = new TopBotLine(battleTab, region, session, HELP_BATTLE);
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
        battleWindow = new TabGroup(battleTab, helpWindow, battleHelp);
        current = battleWindow;
    }

    @Override
    protected void showBalanceBattle() {
        current = new TabGroup(new MultiLine(halfCol, halfRow, "Battle"));
    }

    @Override
    protected void showBalanceDraft() {
        List<String> cards = new ArrayList<>();
        cards.addAll(CardFactory.SHOTS);
        cards.addAll(CardFactory.ARMORS);
        cards.addAll(CardFactory.STATUSES);
        current = new TabGroup(new MultiLine(halfCol, region.topRow(),
                    cards.stream().reduce((a,b) -> a + "\n" + b).orElse("")));
    }

    @Override
    protected void showBalanceNav() {
        List<String> enemies = new ArrayList<>();
        for (var c : EnemyFactory.enemyTypes) {
            enemies.add(c.getSimpleName());
        }
        current = new TabGroup(new MultiLine(halfCol, region.topRow(),
                    enemies.stream().reduce((a,b) -> a + "\n" + b).orElse("")));
    }

    @Override
    public void display(State state) {
        if (helpShow != 0) {
            current.at(helpShow).draw(screen);
        } else {
            super.display(state);
            current.at(0).draw(screen);
        }
        try {
            screen.refresh();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setTab(int helpShow) {
        this.helpShow = helpShow;
    }
}
