package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.game.GameSession;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.Description;
import org.andnekon.view.tui.widgets.battle.EnemyCard;
import org.andnekon.view.tui.widgets.battle.PlayerPositionRow;
import org.andnekon.view.tui.widgets.battle.PlayerStats;

import com.googlecode.lanterna.TerminalPosition;

/**
 * Battle
 */
public class Battle extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Battle(AsciiReaderService arService, BattleManager manager, TerminalRegion region) {
        super(region);

        TerminalPosition start = new TerminalPosition(2, 2); // hardcoded
        Widget playerStats = new Border(new PlayerStats(manager, start));
        widgets.add(playerStats);

        Enemy enemy = manager.getEnemy();
        String resource = "tui/enemy/" + enemy.getClass().getSimpleName();
        String stats = String.format("%s\nhp %d(%d); def %d\nstatus: %s",
                enemy.getClass().getSimpleName(),
                enemy.getHp(), enemy.getMaxHp(), enemy.getDefense(),
                "");
        Widget enemyCard = new EnemyCard(arService,
                playerStats.getRegion().rightCol() + 2, playerStats.getRegion().topRow() + 1,
                resource, stats);
        enemyCard = new Border(enemyCard);
        widgets.add(enemyCard);


        TerminalRegion ecRegion = enemyCard.getRegion();
        // +1 here adjusts for border
        TerminalRegion playerCardRegion = new TerminalRegion(
                ecRegion.leftCol() + 1, ecRegion.botRow() + 2, ecRegion.leftCol(), ecRegion.botRow()
                );
        Widget playerCard = new PlayerPositionRow(arService, playerCardRegion, playerCardRegion.getTopLeft());
        playerCard = new Border(playerCard);
        widgets.add(playerCard);

        // add relics

        Widget description = new Description(manager, new TerminalRegion(
                    playerCardRegion.rightCol() + 2, ecRegion.topRow(),
                    150, playerCardRegion.botRow()
                    ));
        widgets.add(description);

        // Widget attackHand =
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }


}
