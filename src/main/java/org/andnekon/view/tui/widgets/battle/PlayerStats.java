package org.andnekon.view.tui.widgets.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.manage.BattleManager;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.Widget;

import java.util.Arrays;

/** PlayerStats */
public class PlayerStats implements Widget {

    private TerminalPosition topLeft;

    private BattleManager manager;

    private Widget resources;

    // private Widget upgrades; // TODO: rethink if it's even needed

    public PlayerStats(BattleManager manager, TerminalPosition topLeft) {
        this.manager = manager;
        this.topLeft = topLeft;
        // offset by 1 for border
        String[] dummy = new String[] {"shield", "99", "energy", "4"};
        resources = createWidget(dummy);
    }

    @Override
    public void draw(Screen screen) {
        String[] recourcesString =
                new String[] {
                    "shield",
                    String.valueOf(manager.getPlayer().getDefense()),
                    "energy",
                    String.valueOf(manager.getPlayer().getEnergy())
                };
        resources = createWidget(recourcesString);
        resources.draw(screen);
    }

    private MultiLine createWidget(String[] lines) {
        return new MultiLine(
                topLeft.getColumn(),
                topLeft.getRow(),
                Arrays.stream(lines).reduce((a, b) -> a + "\n" + b).get().toString());
    }

    @Override
    public TerminalRegion getRegion() {
        return resources.getRegion();
    }
}
