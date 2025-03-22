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

/**
 * Battle
 */
public class Battle extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Battle(AsciiReaderService arService, TerminalRegion region, BattleManager manager) {
        super(region);

        Enemy enemy = manager.getEnemy();
        String resource = "tui/enemy/" + enemy.getClass().getTypeName();
        String stats = String.format("%s\nhp %d(%d); def %d\nstatus: %s",
                enemy.getClass().getTypeName(),
                enemy.getHp(), enemy.getMaxHp(), enemy.getDefense(),
                "");
        EnemyCard enemyCard = new EnemyCard(arService, region.leftCol(), region.topRow(),
                resource, stats);

        this.widgets.add(enemyCard);
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }


}
