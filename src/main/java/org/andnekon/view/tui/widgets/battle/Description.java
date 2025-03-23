package org.andnekon.view.tui.widgets.battle;

import java.util.List;

import org.andnekon.game.action.Intent;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.screen.Screen;

/**
 * Description
 */
public class Description implements Widget {

    private BattleManager manager;

    private TerminalRegion region;

    public Description(BattleManager manager, TerminalRegion region) {
        this.manager = manager;
        this.region = region;
    }

    @Override
    public void draw(Screen screen) {
        Enemy enemy = this.manager.getEnemy();
        List<Intent> intents = enemy.getCurrentIntents();
        StringBuilder sb = new StringBuilder();
        sb.append("Enemies are going to:\n");
        sb.append("\t" + enemy.getClass().getSimpleName() + ":\n");
        for (Intent i : intents) {
            sb.append("\t\t" + i.toString() + "\n");
        }
        Widget out = new MultiLine(this.region.leftCol(), this.region.topRow(), sb.toString());
        out.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return region;
    }


}
