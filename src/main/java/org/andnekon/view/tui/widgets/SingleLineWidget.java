package org.andnekon.view.tui.widgets;

import org.andnekon.view.tui.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.screen.Screen;

/**
 * A widget with a single line of text
 */
public class SingleLineWidget implements Widget {

    private String text;
	private TerminalPosition topLeft;
	private TerminalPosition bottomRight;

    public SingleLineWidget(String text, TerminalPosition topLeft) {
        this.text = text;
        this.topLeft = topLeft;
        this.bottomRight = new TerminalPosition(topLeft.getColumn(),
                topLeft.getRow() + text.length());
    }

    @Override
    public void draw(Screen screen) {
        TextCharacter[] tcs = TextCharacter.fromString(text);
        for (int i = 0; i < text.length(); i++) {
            System.err.println("drawing single line");
            screen.setCharacter(topLeft.getColumn() + i, topLeft.getRow(), tcs[i]);
        }
        screen.setCursorPosition(new TerminalPosition(topLeft.getColumn() + text.length(),
                    topLeft.getRow()));
    }

    @Override
    public TerminalPosition getTopLeftPos() {
        return topLeft;
    }

    @Override
    public TerminalPosition getBottomRightPos() {
        return bottomRight;
    }
}

