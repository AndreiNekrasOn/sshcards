package org.andnekon.view.tui.widgets;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;

/**
 * Widget represents a rectange on the screen, filled with some content and drawn on the specified
 * position.
 */
public interface Widget {

    /** Draw widget on the set position on the screen */
    void draw(Screen screen);

    TerminalRegion getRegion();
}
