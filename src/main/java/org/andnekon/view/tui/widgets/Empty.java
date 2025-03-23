package org.andnekon.view.tui.widgets;

import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * Empty
 */
public class Empty implements Widget {

    private TerminalRegion region;

    public Empty(TerminalRegion region) {
        this.region = region;
    }

    @Override
    public void draw(Screen screen) {
        for (int i = region.leftCol(); i < region.rightCol(); i++) {
            for (int j = region.topRow(); j < region.botRow(); j++) {
                screen.setCharacter(i, j, TextCharacter.fromCharacter(' ')[0]);
            }
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }


}
