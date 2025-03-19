package org.andnekon.view.tui.widgets;

import java.io.IOException;

import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * Border
 */
public class Border implements Widget {

    private Widget component;
    private TerminalRegion region;

    public Border(Widget component) {
        this.component = component;
        TerminalRegion r = this.component.getRegion();
        this.region = new TerminalRegion(r.topLeftCol() - 1, r.topLeftRow() - 1,
                r.botRightCol() + 1, r.botRightRow() + 1); // why botRightCol +0?
    }

    @Override
    public void draw(Screen screen) {
        int length = region.botRightCol() - region.topLeftCol() + 1;
        int height = region.botRightRow() - region.topLeftRow() + 1;

        TextCharacter[] lineHorizontal = TextCharacter.fromString("-".repeat(length));
        TextCharacter[] lineVertical = TextCharacter.fromString("|".repeat(height));
        TextCharacter[] corner = TextCharacter.fromCharacter('*');

        for (int i = 0; i < height; i++) {
            screen.setCharacter(region.topLeftCol(), region.topLeftRow() + i, lineVertical[i]);
            screen.setCharacter(region.botRightCol(), region.topLeftRow() + i, lineVertical[i]);
        }
        for (int i = 0; i < length; i++) {
            screen.setCharacter(region.topLeftCol() + i, region.topLeftRow(), lineHorizontal[i]);
            screen.setCharacter(region.topLeftCol() + i, region.botRightRow(), lineHorizontal[i]);
        }

        screen.setCharacter(region.topLeftCol(), region.topLeftRow(), corner[0]);
        screen.setCharacter(region.topLeftCol(), region.botRightRow(), corner[0]);
        screen.setCharacter(region.botRightCol(), region.topLeftRow(), corner[0]);
        screen.setCharacter(region.botRightCol(), region.botRightRow(), corner[0]);

        component.draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }
}
