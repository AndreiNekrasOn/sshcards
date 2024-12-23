package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.Player;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.MainWindow;
import org.andnekon.view.tui.windows.MenuComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Main content - two {@code ChoicesWindow} menus, with {@code postRead} being called only for the
 * selected one.<br>
 * Menus are selected via horizontal navigation keys and selection is confirmed via `I` character.
 * <br>
 * To exit this selection, user can press `Return`
 */
public class BattleWindow extends MainWindow {

    private static final Logger logger = LoggerFactory.getLogger(BattleWindow.class);

    private class MenuComponentSelection {

        public MenuComponent menu;
        public boolean selected;

        public MenuComponentSelection(MenuComponent menu) {
            this.menu = menu;
            selected = false;
        }
    }

    private AsciiReaderService asciiReaderService;
    private GameSession session;

    private Label playerStats;
    private Label enemyStats;
    private Label enemyVisual;

    private MenuComponent shotMenu;
    private MenuComponent armorMenu;

    private List<MenuComponentSelection> menus;

    /** Selecting menu itself, rather than specific card within the menu */
    private boolean menuMode;

    public BattleWindow(
            StatefulMultiWindowTextGui gui,
            AsciiReaderService asciiReaderService,
            GameSession session) {
        super(gui);
        this.asciiReaderService = asciiReaderService;
        this.session = session;
    }

    /**
     * first column - battle info second column - content (ascii art for player/enemy, dice, intents
     * infographics, relics*) third column - deck and avaliable
     */
    @Override
    public void setup() {
        content = new Panel(new GridLayout(2));

        Panel battleInfo = new Panel();
        setupBattleInfo(battleInfo);
        content.addComponent(battleInfo);

        Panel mainVisual = new Panel(new GridLayout(2)); // first column - ships, second - dice
        setupMainVisual(mainVisual);
        content.addComponent(mainVisual);

        menus = new ArrayList<>();
        menus.add(new MenuComponentSelection(shotMenu));
        menus.add(new MenuComponentSelection(armorMenu));
        menus.get(0).selected = true;
    }

    @Override
    public void prepare() {
        this.enemyStats.setText(formEnemyStats());
        this.playerStats.setText(formPlayerStats());
        this.enemyVisual.setText(formEnemyVisual());
        prepareMenus();
        colorMenus();
    }

    private void prepareMenus() {
        List<Card> shotHand = session.getPlayer().getShotDeck().getHand();
        shotMenu.prepare(shotHand.stream().map(Card::toString).toArray(String[]::new));

        List<Card> armorHand = session.getPlayer().getArmorDeck().getHand();
        armorMenu.prepare(armorHand.stream().map(Card::toString).toArray(String[]::new));

        adjustEmptySelection();
    }

    /** If selected menu is empty, select first non-empty one or do nothing */
    private void adjustEmptySelection() {
        boolean nonEmptySelected =
                menus.stream()
                        .filter(ms -> ms.menu.getChildCount() > 0)
                        .anyMatch(ms -> ms.selected);
        if (!nonEmptySelected) {
            logger.info("no menu selected");
            Optional<MenuComponentSelection> candidate =
                    menus.stream().filter(ms -> ms.menu.getChildCount() > 0).findFirst();
            if (candidate.isPresent()) {
                menus.stream().forEach(ms -> ms.selected = false);
                candidate.get().selected = true;
            }
        }
    }

    private void colorMenus() {
        menus.stream().filter(ms -> !ms.selected).forEach(ms -> ms.menu.unfocus());
    }

    private String formEnemyVisual() {
        StringBuilder builder = new StringBuilder();
        for (Intent intent : this.session.getBattleManager().getEnemy().getCurrentIntents()) {
            builder.append(intent.toString()).append("\n");
        }
        return builder.toString();
    }

    private void setupMainVisual(Panel mainVisual) {
        Panel enemyPanel = new Panel();
        enemyVisual = new Label("");
        Label enemyText = new Label("Enemy is going to:");
        enemyPanel.addComponent(enemyText);
        enemyPanel.addComponent(enemyVisual);
        mainVisual.addComponent(enemyPanel);

        Panel playerPanel = new Panel();
        Label playerText = new Label("Your cards are");
        playerPanel.addComponent(playerText);

        Panel deckMenus = new Panel(new GridLayout(2));
        shotMenu = new MenuComponent();
        armorMenu = new MenuComponent();

        deckMenus.addComponent(shotMenu);
        deckMenus.addComponent(armorMenu);

        playerPanel.addComponent(deckMenus);

        mainVisual.addComponent(playerPanel);
    }

    private void setupBattleInfo(Panel battleInfo) {
        // Player health, defence, energy
        // Enemy healt, defence
        Panel vbox = new Panel();

        Panel playerBox = new Panel();
        vbox.addComponent(playerBox);

        Label playerPrompt = new Label("Player stats");
        playerStats = new Label("");
        playerBox.addComponent(playerPrompt);
        playerBox.addComponent(playerStats);
        vbox.addComponent(playerBox);

        Panel enemyBox = new Panel();
        vbox.addComponent(enemyBox);

        Label enemyPrompt = new Label("Enemy stats");
        enemyStats = new Label("");
        enemyBox.addComponent(enemyPrompt);
        enemyBox.addComponent(enemyStats);
        vbox.addComponent(enemyBox);

        battleInfo.addComponent(vbox);
    }

    private String formPlayerStats() {
        Player player = session.getPlayer();
        StringBuilder res = new StringBuilder();
        return res.append(player.getHp())
                .append(" hp\n")
                .append(player.getDefense())
                .append(" def\n")
                .append(player.getEnergy())
                .append(" energy\n")
                .toString();
    }

    private String formEnemyStats() {
        Enemy enemy = session.getBattleManager().getEnemy();
        StringBuilder res = new StringBuilder();
        return res.append(enemy.toString())
                .append("\n")
                .append(enemy.getHp())
                .append(" hp\n")
                .append(enemy.getDefense())
                .append(" def\n")
                .toString();
    }

    @Override
    protected void postRead() {
        List<KeyStroke> buffer = gui.getReader().getBuffer();
        if (buffer.size() != 1) {
            return;
        }
        KeyStroke key = buffer.get(0);
        logger.info(
                "recieved input, selected {}",
                menus.stream().filter(ms -> ms.selected).findFirst().orElseThrow().menu);
        if (KeyStrokeUtil.compareType(key, KeyType.Tab)) { // tab selects menu
            cycleMenu(1);
            buffer.clear();
            return;
        }
        processAsMenu(buffer);
    }

    private void processAsMenu(List<KeyStroke> buffer) {
        String suffix = "";
        MenuComponentSelection selected =
                menus.stream().filter(m -> m.selected).findFirst().orElseThrow();
        if (selected.menu == shotMenu) {
            suffix = "s";
        } else if (selected.menu == armorMenu) {
            suffix = "a";
        }
        if (selected.menu.processInput(buffer)) {
            buffer.add(0, KeyStroke.fromString(suffix));
        }
    }

    /**
     * Updates {@code menus} by changing selected menu element according to the given direction.
     *
     * @param direction negative for left, positive for right
     */
    private void cycleMenu(int direction) {
        int index =
                IntStream.range(0, menus.size())
                        .filter(i -> menus.get(i).selected)
                        .findFirst()
                        .orElse(-1);
        if (index == -1) { // no menu selected
            logger.info("no menu selected");
            menus.get(0).selected = true;
            return;
        }
        index = (index + direction + menus.size()) % menus.size();
        logger.info("updated index: {}", index);
        for (int i = 0; i < menus.size(); i++) {
            MenuComponentSelection ms = menus.get(i);
            if (i == index) {
                ms.selected = true;
                ms.menu.setCurrentIndex(0);
            } else {
                ms.selected = false;
            }
        }
    }

    @Override
    protected String getControls() {
        return "<Tab> to cycle selected \"hand\", [1-3] - choose a card, j/k/w/s/arrows -"
                + " navigation";
    }
}
