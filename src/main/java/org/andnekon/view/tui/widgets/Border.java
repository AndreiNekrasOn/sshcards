package org.andnekon.view.tui.widgets;

import java.io.IOException;

import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * Border decorator
 */
public class Border implements Widget {

    private Widget component;
    private TerminalRegion region;

    public Border(Widget component) {
        this.component = component;
        TerminalRegion r = this.component.getRegion();
        this.region = new TerminalRegion(r.leftCol() - 1, r.topRow() - 1,
                r.rightCol() + 1, r.botRow() + 1); // why botRightCol +0?
    }

    @Override
    public void draw(Screen screen) {
        int length = region.rightCol() - region.leftCol() + 1;
        int height = region.botRow() - region.topRow() + 1;

        TextCharacter[] lineHorizontal = TextCharacter.fromString("-".repeat(length));
        TextCharacter[] lineVertical = TextCharacter.fromString("|".repeat(height));
        TextCharacter[] corner = TextCharacter.fromCharacter('*');

        for (int i = 0; i < height; i++) {
            screen.setCharacter(region.leftCol(), region.topRow() + i, lineVertical[i]);
            screen.setCharacter(region.rightCol(), region.topRow() + i, lineVertical[i]);
        }
        for (int i = 0; i < length; i++) {
            screen.setCharacter(region.leftCol() + i, region.topRow(), lineHorizontal[i]);
            screen.setCharacter(region.leftCol() + i, region.botRow(), lineHorizontal[i]);
        }

        screen.setCharacter(region.leftCol(), region.topRow(), corner[0]);
        screen.setCharacter(region.leftCol(), region.botRow(), corner[0]);
        screen.setCharacter(region.rightCol(), region.topRow(), corner[0]);
        screen.setCharacter(region.rightCol(), region.botRow(), corner[0]);

        component.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }
}
