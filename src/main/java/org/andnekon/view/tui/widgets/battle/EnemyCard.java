package org.andnekon.view.tui.widgets.battle;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;

/** EnemyCard */
public class EnemyCard implements Widget {

    public static final int WIDTH = 20;

    private Widget stateWidget;
    private Widget artWidget;

    public EnemyCard(
            AsciiReaderService arService, int col, int row, String resource, String enemyState) {
        String art = "<DEFAULT>";
        try {
            art = arService.readFile(resource);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stateWidget = new MultiLine(col, row, enemyState);
        artWidget =
                new MultiLine(
                        stateWidget.getRegion().leftCol(), stateWidget.getRegion().botRow(), art);
    }

    @Override
    public void draw(Screen screen) {
        this.stateWidget.draw(screen);
        this.artWidget.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return new TerminalRegion(
                stateWidget.getRegion().leftCol(),
                stateWidget.getRegion().topRow(),
                artWidget.getRegion().rightCol(),
                artWidget.getRegion().botRow());
    }
}
