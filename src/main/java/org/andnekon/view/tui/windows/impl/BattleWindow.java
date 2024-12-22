package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.SimpleTheme;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.action.Intent;
import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.MainWindow;
import org.andnekon.view.tui.windows.MenuComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Main content - two {@code ChoicesWindow} menus, with {@code postRead} being called only for the
 * selected one.<br>
 * Menus are selected via horizontal navigation keys and selection is confirmed via `I` character.
 * <br>
 * To exit this selection, user can press `Return`
 */
public class BattleWindow extends MainWindow {

    private static final Logger logger = LoggerFactory.getLogger(BattleWindow.class);

    private AsciiReaderService asciiReaderService;
    private GameSession session;

    private MenuComponent shotMenu;
    private MenuComponent armorMenu;

    private Label playerStats;
    private Label enemyStats;
    private Label enemyVisual;
    private boolean shotMenuSelected;
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
    }

    private void colorMenus() {
        MenuComponent selected;
        MenuComponent unselected;
        if (shotMenuSelected) {
            selected = shotMenu;
            unselected = armorMenu;
        } else {
            selected = armorMenu;
            unselected = shotMenu;
        }
        selected.setTheme(gui.getTheme());
        unselected.setTheme(gui.getTheme());
        unselected.unfocus();
        if (!menuMode) {
            selected.unfocus();
            selected.setTheme(new SimpleTheme(TextColor.ANSI.RED, TextColor.ANSI.DEFAULT));
        }
    }

    private String formEnemyVisual() {
        StringBuilder builder = new StringBuilder();
        for (Intent intent : this.session.getEnemy().getCurrentIntents()) {
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
        StringBuilder res = new StringBuilder();
        res.append(session.getPlayer().getHp());
        res.append(" hp\n");
        res.append(session.getPlayer().getDefense());
        res.append(" def\n");
        res.append(session.getPlayer().getEnergy());
        res.append(" energy\n");
        return res.toString();
    }

    private String formEnemyStats() {
        StringBuilder res = new StringBuilder();
        res.append(session.getEnemy().toString());
        res.append("\n");
        res.append(session.getEnemy().getHp());
        res.append(" hp\n");
        res.append(session.getEnemy().getDefense());
        ;
        res.append(" def\n");
        return res.toString();
    }

    @Override
    protected void postRead() {
        List<KeyStroke> buffer = gui.getReader().getBuffer();
        if (buffer.size() != 1) {
            return;
        }
        logger.info("recieved input, menu {}, shot {}", menuMode, shotMenuSelected);
        if (menuMode) {
            if (KeyStrokeUtil.isControl(buffer.get(0))) {
                menuMode = false;
                buffer.clear();
                return;
            }
            processAsMenu(buffer);
            return;
        }

        KeyStroke key = buffer.get(0);
        if (KeyStrokeUtil.isLeftMotion(key)) { // TODO: proper cycling
            shotMenuSelected = true;
            buffer.clear();
        } else if (KeyStrokeUtil.isRightMotion(key)) {
            shotMenuSelected = false;
            buffer.clear();
        } else if (KeyStrokeUtil.compareChar(key, 'i') || KeyStrokeUtil.compareChar(key, 'I')) {
            menuMode = true;
            buffer.clear();
        }
    }

    private void processAsMenu(List<KeyStroke> buffer) {
        MenuComponent currentMenu;
        String suffix;
        if (shotMenuSelected) {
            currentMenu = shotMenu;
            suffix = "s";
        } else {
            currentMenu = armorMenu;
            suffix = "a";
        }
        if (currentMenu.processInput(buffer)) {
            buffer.add(0, KeyStroke.fromString(suffix));
        }
    }
}
