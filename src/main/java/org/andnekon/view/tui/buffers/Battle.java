package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.TerminalPosition;

import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.CardHand;
import org.andnekon.view.tui.widgets.battle.Description;
import org.andnekon.view.tui.widgets.battle.EnemyCard;
import org.andnekon.view.tui.widgets.battle.PlayerPositionRow;
import org.andnekon.view.tui.widgets.battle.PlayerStats;

import java.util.ArrayList;
import java.util.List;

/** Battle */
public class Battle extends Buffer {

    private List<Widget> widgets = new ArrayList<>();
    private BattleManager manager;
    private AsciiReaderService arService;

    Widget playerStats;
    List<Widget> enemyCards;
    Widget playerCard;
    Widget description;

    TerminalRegion enemyRegion;

    public Battle(AsciiReaderService arService, BattleManager manager, TerminalRegion region) {
        super(region);
        this.manager = manager;
        this.arService = arService;
        enemyCards = new ArrayList<>();

        setupStats();
        TerminalRegion psRegion = playerStats.getRegion();
        enemyRegion =
                new TerminalRegion(
                        psRegion.rightCol() + 1,
                        psRegion.topRow(),
                        psRegion.rightCol() + EnemyCard.WIDTH,
                        psRegion.topRow() + EnemyCard.HEIGHT);
        setupEnemyCards();
        setupPlayerArt();
        setupDescription();
        // add relics
        setupHand();
    }

    private void setupStats() {
        TerminalPosition start = new TerminalPosition(region.leftCol(), region.topRow());
        playerStats = new Border(new PlayerStats(manager, start));
        widgets.add(playerStats);
    }

    private void setupEnemyCards() {
        Enemy[] enemies = manager.getEnemies();

        int prevCol = playerStats.getRegion().rightCol() + 1;
        for (Enemy e : enemies) {
            String stats =
                    String.format(
                            "%s\nhp %d(%d) def %d\ns: %dcor;%dcr",
                            e.toString(),
                            e.getHp(),
                            e.getMaxHp(),
                            e.getDefense(),
                            e.getEffectValue("Corrosion"),
                            e.getEffectValue("Crack"));
            Widget enemyCard =
                    new EnemyCard(arService, prevCol, region.topRow(), e.getAscii(), stats);
            prevCol = enemyCard.getRegion().rightCol() + 1;
            enemyCard = new Border(enemyCard);
            enemyCards.add(enemyCard);
        }
        widgets.addAll(enemyCards);
    }

    private void setupPlayerArt() {
        int i = manager.getCombat().getIdx();
        TerminalRegion previousRegion;
        TerminalPosition artTopLeft;
        if (manager.getEnemies().length > 0) {
            previousRegion =
                    enemyCards.get(manager.getCombat().getEnemies().length - 1).getRegion();
            TerminalRegion selectedERegion = enemyCards.get(i).getRegion();
            artTopLeft =
                    new TerminalPosition(
                            selectedERegion.leftCol() + 1, selectedERegion.botRow() + 1);
        } else {
            previousRegion = playerStats.getRegion();
            // lets pretend that going up when no enemies is a feature
            artTopLeft =
                    new TerminalPosition(
                            previousRegion.rightCol() + 1, previousRegion.topRow() + 1);
        }
        // +1 here adjusts for border
        TerminalRegion playerCardRegion =
                new TerminalRegion(
                        playerStats.getRegion().rightCol() + 1,
                        previousRegion.botRow() + 1,
                        playerStats.getRegion().rightCol() + 1,
                        previousRegion.botRow() + 1);
        playerCard = new PlayerPositionRow(arService, playerCardRegion, artTopLeft);
        playerCard = new Border(playerCard);
        widgets.add(playerCard);
    }

    private void setupDescription() {
        TerminalRegion playerCardRegion = playerCard.getRegion();
        TerminalRegion ecRegion = enemyRegion;
        description =
                new Description(
                        manager,
                        new TerminalRegion(
                                playerCardRegion.rightCol() + 2,
                                ecRegion.topRow() + 1,
                                150,
                                playerCardRegion.botRow()));
        description = new Border(description);
        widgets.add(description);
    }

    private void setupHand() {
        TerminalRegion skillRegion;
        TerminalRegion attackRegion =
                new TerminalRegion(
                        region.leftCol(),
                        playerCard.getRegion().botRow() + 2, // for border
                        region.leftCol(),
                        playerCard.getRegion().botRow() + 2);

        // manager.getPlayer().getShotDeck().getHand().stream()
        //         .map(Card::getArt)
        //         .toList()
        // .toArray(String[]::new);
        int aSize = manager.getPlayer().getShotDeck().getHand().size();
        if (aSize > 0) {
            Widget attackHand =
                    new CardHand(
                            arService, manager.getPlayer().getShotDeck().getHand(), attackRegion);
            widgets.add(attackHand);
            skillRegion =
                    new TerminalRegion(
                            attackHand.getRegion().rightCol(),
                            playerCard.getRegion().botRow() + 2,
                            attackHand.getRegion().rightCol(),
                            playerCard.getRegion().botRow() + 2);
        } else {
            skillRegion = attackRegion;
        }

        if (manager.getPlayer().getArmorDeck().getHand().size() > 0) {
            Widget skillHand =
                    new CardHand(
                            arService,
                            manager.getPlayer().getArmorDeck().getHand(),
                            skillRegion,
                            aSize);
            widgets.add(skillHand);
        }
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
