package org.andnekon.view.tui.widgets;

import java.io.IOException;

import org.andnekon.view.tui.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * Border
 */
public class Border implements Widget {

    private Widget component;

    public Border(Widget component) {
        this.component = component;
    }

    @Override
    public void draw(Screen screen) {
        TerminalPosition top = getTopLeftPos();
        TerminalPosition bottom = getBottomRightPos();
        int length = bottom.getColumn() - top.getColumn() + 1;
        int height = bottom.getRow() - top.getRow()  + 1;

        TextCharacter[] debug = TextCharacter.fromString(bottom.toString() + " " + top.toString());
        for (int i = 0; i < debug.length; i++) {
            screen.setCharacter(i, 0, debug[i]);
        }

        component.draw(screen);
        TextCharacter[] lineHorizontal = TextCharacter.fromString("_".repeat(length));
        TextCharacter[] lineVertical = TextCharacter.fromString("|".repeat(height));
        TextCharacter[] corner = TextCharacter.fromCharacter('*');

        for (int i = 0; i < height; i++) {
            screen.setCharacter(top.getColumn(), top.getRow() + i, lineVertical[i]);
            screen.setCharacter(bottom.getColumn(), top.getRow() + i, lineVertical[i]);
        }
        for (int i = 0; i < length; i++) {
            screen.setCharacter(top.getColumn() + i, top.getRow(), lineHorizontal[i]);
            screen.setCharacter(top.getColumn() + i, bottom.getRow(), lineHorizontal[i]);
        }
        screen.setCharacter(top.getColumn(), top.getRow(), corner[0]);
        screen.setCharacter(top.getColumn(), bottom.getRow(), corner[0]);
        screen.setCharacter(bottom.getColumn(), top.getRow(), corner[0]);
        screen.setCharacter(bottom.getColumn(), bottom.getRow(), corner[0]);
        try {
            screen.refresh();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public TerminalPosition getTopLeftPos() {
        TerminalPosition p = this.component.getTopLeftPos();
        return new TerminalPosition(p.getColumn() - 1, p.getRow() - 1);
    }

    @Override
    public TerminalPosition getBottomRightPos() {
        TerminalPosition p = this.component.getBottomRightPos();
        return new TerminalPosition(p.getColumn() + 1, p.getRow() + 1);
    }

    private void drawHorizontalLine(Screen screen, int column, int row, int n) {
    }
}
