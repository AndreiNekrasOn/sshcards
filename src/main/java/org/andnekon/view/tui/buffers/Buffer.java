package org.andnekon.view.tui.buffers;

import java.util.List;
import java.util.function.BiFunction;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;

import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;

/**
  * Buffer is a {@code Widget} that holds other widgets and fills the position with them
  */
public abstract class Buffer implements Widget {

    protected TerminalRegion region;

    public Buffer(TerminalRegion region) {
        this.region = region;
    }

    protected abstract List<Widget> widgets();

    @Override
    public void draw(Screen screen) {
        // TODO: Make this smarter with BufferManager
        screen.clear();
        List<Widget> widgets = widgets();
        for (Widget w : widgets) {
            System.err.println("Trying to draw widget? " + w.getClass().getName());
            // TODO: fix; warning instead of error
            // if (isOverlap(topLeft, w.getTopLeftPos(), (f,s) -> f < s) ||
            //         isOverlap(bottomRight, w.getBottomRightPos(), (f,s) -> f < s)) {
            //     throw new IllegalStateException("Widget overlaps buffer");
            // }
            w.draw(screen);
        }
        try {
            screen.refresh();
        } catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public TerminalRegion getRegion() {
        return region;
    }
}
