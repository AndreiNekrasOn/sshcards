package org.andnekon.view.tui.widgets.battle;

import java.io.IOException;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.screen.Screen;

/**
 * EnemyCard
 */
public class EnemyCard implements Widget {

    private Widget stateWidget;
    private Widget artWidget;

    public EnemyCard(AsciiReaderService arService, int col, int row, String resource, String enemyState) {
        String art = "<DEFAULT>";
        try {
            art = arService.readFile(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateWidget = new MultiLine(col + 1, row + 1, enemyState);
        stateWidget = new Border(stateWidget);
        artWidget = new MultiLine(
                stateWidget.getRegion().leftCol(), stateWidget.getRegion().botRow() + 2, art);
    }

    @Override
    public void draw(Screen screen) {
        this.stateWidget.draw(screen);
        this.artWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return new TerminalRegion(stateWidget.getRegion().leftCol(), stateWidget.getRegion().topRow(),
                artWidget.getRegion().rightCol(), artWidget.getRegion().botRow());
    }


}
