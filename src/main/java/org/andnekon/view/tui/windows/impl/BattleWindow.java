package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.game.action.Card;
import org.andnekon.game.entity.Player;
import org.andnekon.view.Messages;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.ChoicesWindow;

import java.util.ArrayList;
import java.util.List;

public class BattleWindow extends ChoicesWindow {

    private AsciiReaderService asciiReaderService;
    private GameSession session;

    private Label playerData;
    private Label enemyData;
    private Panel playerVisual;
    private Label enemyVisual;
    private Label deckInfo;

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
        super.setup();

        Panel content = new Panel(new GridLayout(3));
        this.setComponent(content);

        Panel battleInfo = new Panel();
        setupBattleInfo(battleInfo);
        content.addComponent(battleInfo);

        Panel mainVisual = new Panel(new GridLayout(2)); // first column - ships, second - dice
        setupMainVisual(mainVisual);
        content.addComponent(mainVisual);

        Panel deckInfo = new Panel();
        setupActionsInfo(deckInfo);
        content.addComponent(deckInfo);
    }

    @Override
    public void prepare() {
        this.playerData.setText(formPlayerData());
        this.enemyData.setText(formEnemyData());

        this.enemyVisual.setText(formEnemyVisual());
        super.prepare();
    }

    private String formEnemyVisual() {
        return this.session.getEnemy().displayIntents();
    }

    private void setupActionsInfo(Panel actionsInfo) {
        deckInfo = new Label("");
        actionsInfo.addComponent(deckInfo);

        Label helpMessage = new Label("");
        StringBuilder helpText = new StringBuilder();
        for (String msg : Messages.BATTLE_OPTIONS) {
            helpText.append(msg);
            helpText.append('\n');
        }
        helpMessage.setText(helpText.toString());
        actionsInfo.addComponent(helpMessage);
    }

    private void setupMainVisual(Panel mainVisual) {
        enemyVisual = new Label("");
        playerVisual = menu;
        Label enemyText = new Label("Enemy is going to:");
        mainVisual.addComponent(enemyText);
        mainVisual.addComponent(enemyVisual);
        Label playerText = new Label("Your cards are");
        mainVisual.addComponent(playerText);
        mainVisual.addComponent(playerVisual);
    }

    private void setupBattleInfo(Panel battleInfo) {
        // Player health, defence, energy
        // Enemy healt, defence
        Panel vbox = new Panel();

        Panel playerBox = new Panel();
        vbox.addComponent(playerBox);

        Label playerPrompt = new Label("Player data");
        playerData = new Label("");
        playerBox.addComponent(playerPrompt);
        playerBox.addComponent(playerData);
        vbox.addComponent(playerBox);

        Panel enemyBox = new Panel();
        vbox.addComponent(enemyBox);

        Label enemyPrompt = new Label("Enemy data");
        enemyData = new Label("");
        enemyBox.addComponent(enemyPrompt);
        enemyBox.addComponent(enemyData);
        vbox.addComponent(enemyBox);

        battleInfo.addComponent(vbox);
    }

    private String formPlayerData() {
        StringBuilder res = new StringBuilder();
        res.append(session.getPlayer().getHp());
        res.append(" hp\n");
        res.append(session.getPlayer().getDefense());
        res.append(" def\n");
        res.append(session.getPlayer().getEnergy());
        res.append(" energy\n");
        return res.toString();
    }

    private String formEnemyData() {
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
    protected void setMenuOptions() {
        Player player = this.session.getPlayer();
        List<Card> deck = new ArrayList<>();
        deck.addAll(player.getShotDeck().getHand());
        deck.addAll(player.getArmorDeck().getHand());
        this.options = new String[deck.size()];
        for (int i = 0; i < deck.size(); i++) {
            this.options[i] = deck.get(i).toString();
        }
    }
}
