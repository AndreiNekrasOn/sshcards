package org.andnekon.view.tui.widgets.battle;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.game.entity.Player;
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
        String[] dummy =
                new String[] {"shield", "99", "energy", "4", "STATUSES", "cor.", "99", "cr.", "99"};
        resources = createWidget(dummy);
    }

    @Override
    public void draw(Screen screen) {
        Player p = manager.getPlayer();
        String[] recourcesString =
                new String[] {
                    "shield",
                    String.valueOf(p.getDefense()),
                    "energy",
                    String.valueOf(p.getEnergy()),
                    "STATUSES",
                    "cor.",
                    String.valueOf(p.getEffectValue("Corrosion")),
                    "cr.",
                    String.valueOf(p.getEffectValue("Crack")),
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
