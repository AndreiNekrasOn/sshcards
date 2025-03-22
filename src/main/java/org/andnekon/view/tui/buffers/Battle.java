package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.game.GameSession;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;
import org.andnekon.view.tui.widgets.battle.EnemyCard;
import org.andnekon.view.tui.widgets.battle.PlayerStats;

/**
 * Battle
 */
public class Battle extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Battle(AsciiReaderService arService, BattleManager manager, TerminalRegion region) {
        super(region);

        Widget playerStats = new PlayerStats(manager, region.getTopLeft());

        widgets.add(playerStats);

        Enemy enemy = manager.getEnemy();
        String resource = "tui/enemy/" + enemy.getClass().getSimpleName();
        String stats = String.format("%s\nhp %d(%d); def %d\nstatus: %s",
                enemy.getClass().getSimpleName(),
                enemy.getHp(), enemy.getMaxHp(), enemy.getDefense(),
                "");
        Widget enemyCard = new EnemyCard(arService,
                playerStats.getRegion().rightCol() + 1, playerStats.getRegion().topRow(),
                resource, stats);

        widgets.add(enemyCard);
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }


}
