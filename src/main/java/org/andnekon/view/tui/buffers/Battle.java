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
    Widget enemyCard;
    Widget playerCard;
    Widget description;

    public Battle(AsciiReaderService arService, BattleManager manager, TerminalRegion region) {
        super(region);
        this.manager = manager;
        this.arService = arService;

        setupStats();
        setupEnemyCard();
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

    private void setupEnemyCard() {
        Enemy enemy = manager.getEnemy();
        String resource = "tui/enemy/" + enemy.getClass().getSimpleName();
        String stats =
                String.format(
                        "%s\nhp %d(%d); def %d\nstatus: %s",
                        enemy.getClass().getSimpleName(),
                        enemy.getHp(),
                        enemy.getMaxHp(),
                        enemy.getDefense(),
                        "");
        enemyCard =
                new EnemyCard(
                        arService,
                        playerStats.getRegion().rightCol() + 1,
                        region.topRow(),
                        resource,
                        stats);
        enemyCard = new Border(enemyCard);
        widgets.add(enemyCard);
    }

    private void setupPlayerArt() {
        TerminalRegion ecRegion = enemyCard.getRegion();
        // +1 here adjusts for border
        TerminalRegion playerCardRegion =
                new TerminalRegion(
                        playerStats.getRegion().rightCol() + 1,
                        ecRegion.botRow() + 1,
                        playerStats.getRegion().rightCol() + 1,
                        ecRegion.botRow() + 1);
        playerCard =
                new PlayerPositionRow(arService, playerCardRegion, playerCardRegion.getTopLeft());
        playerCard = new Border(playerCard);
        widgets.add(playerCard);
    }

    private void setupDescription() {
        TerminalRegion playerCardRegion = playerCard.getRegion();
        TerminalRegion ecRegion = enemyCard.getRegion();
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
        // fill with dummies
        // can't fill with dummies, gotta reinitialize
        String[] attackResources =
                manager.getPlayer().getShotDeck().getHand().stream()
                        .map(c -> "tui/cards/" + c.getName())
                        .toList()
                        .toArray(String[]::new);
        TerminalRegion skillRegion;
        TerminalRegion attackRegion =
                new TerminalRegion(
                        region.leftCol(),
                        playerCard.getRegion().botRow() + 2, // for border
                        region.leftCol(),
                        playerCard.getRegion().botRow() + 2);
        if (attackResources.length > 0) {
            Widget attackHand = new CardHand(arService, attackResources, attackRegion);
            attackHand = new Border(attackHand);
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

        String[] skillResources =
                manager.getPlayer().getArmorDeck().getHand().stream()
                        .map(c -> "tui/cards/" + c.getName())
                        .toList()
                        .toArray(String[]::new);
        if (skillResources.length > 0) {
            Widget skillHand =
                    new CardHand(arService, skillResources, skillRegion, attackResources.length);
            skillHand = new Border(skillHand);
            widgets.add(skillHand);
        }
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
