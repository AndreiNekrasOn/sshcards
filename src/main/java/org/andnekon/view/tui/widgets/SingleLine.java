package org.andnekon.view.tui.widgets;

import org.andnekon.view.tui.TerminalRegion;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * A widget with a single line of text
 */
public class SingleLine implements Widget {

    private String text;
    private TerminalRegion region;

    public SingleLine(String text, TerminalPosition topLeft) {
        this.text = text;
        this.region = new TerminalRegion(topLeft.getColumn(), topLeft.getRow(),
                topLeft.getColumn() + text.length() - 1, topLeft.getRow());
    }

    @Override
    public void draw(Screen screen) {
        TextCharacter[] tcs = TextCharacter.fromString(text);
        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(region.topLeftCol() + i, region.topLeftRow(), tcs[i]);
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return this.region;
    }
}

