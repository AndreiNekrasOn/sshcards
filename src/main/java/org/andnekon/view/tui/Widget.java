package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.screen.Screen;

/**
  * Widget represents a rectange on the screen, filled with some content and
  * drawn on the specified position.
  */
public interface Widget {

    /** Draw widget on the set position on the screen */
    void draw(Screen screen);

    TerminalPosition getTopLeftPos();

    TerminalPosition getBottomRightPos();
}
