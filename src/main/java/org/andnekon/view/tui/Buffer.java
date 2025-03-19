package org.andnekon.view.tui;

import java.util.List;
import java.util.function.BiFunction;
import java.io.IOException;
import java.util.ArrayList;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.screen.Screen;

/**
  * Buffer is a {@code Widget} that holds other widgets and fills the position with them
  */
public abstract class Buffer implements Widget {

    protected TerminalPosition topLeft;
    protected TerminalPosition bottomRight;

    public Buffer(TerminalPosition topLeft, TerminalPosition bottomRight) {
        this.topLeft = topLeft;
        this.bottomRight = bottomRight;
    }

    protected abstract List<Widget> widgets();

    @Override
    public void draw(Screen screen) {
        List<Widget> widgets = widgets();
        for (Widget w : widgets) {
            System.err.println("Trying to draw widget?");
            if (isOverlap(topLeft, w.getTopLeftPos(), (f,s) -> f < s) ||
                    isOverlap(bottomRight, w.getBottomRightPos(), (f,s) -> f < s)) {
                throw new IllegalStateException("Widget overlaps buffer");
            }
            w.draw(screen);
        }
        try {
			screen.refresh();
		} catch (IOException e) { e.printStackTrace(); }
    }

    @Override
    public TerminalPosition getTopLeftPos() {
        return topLeft;
    }

    @Override
    public TerminalPosition getBottomRightPos() {
        return bottomRight;
    }

    private static boolean isOverlap(TerminalPosition first, TerminalPosition second,
            BiFunction<Integer, Integer, Boolean> cmp) {
        return cmp.apply(first.getRow(), second.getRow())
            && cmp.apply(first.getColumn(), second.getColumn());
    }
}
