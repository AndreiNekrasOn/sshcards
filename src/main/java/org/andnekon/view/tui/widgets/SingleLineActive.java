package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.screen.Screen;

/** SingleLineActive */
public class SingleLineActive extends SingleLine implements ActiveWidget {

    private boolean active;

    public SingleLineActive(String text, TerminalPosition topLeft) {
        super(text, topLeft);
        active = false;
    }

    @Override
    public void draw(Screen screen) {
        TextCharacter[] tcs;
        if (active) {
            tcs = TextCharacter.fromString(text, TextColor.ANSI.RED, null, SGR.BOLD);
        } else {
            tcs = TextCharacter.fromString(text, null, null);
        }

        for (int i = 0; i < text.length(); i++) {
            screen.setCharacter(region.leftCol() + i, region.topRow(), tcs[i]);
        }
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean state) {
        this.active = state;
    }
}
