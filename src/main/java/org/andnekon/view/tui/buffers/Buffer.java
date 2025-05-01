package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;

import java.util.List;

/**
 * Buffer is a {@code Widget} that holds other widgets and fills the position with them
 *
 * <p>Clears screen when draws
 */
public abstract class Buffer implements Widget {

    protected TerminalRegion region;

    public Buffer(TerminalRegion region) {
        this.region = region;
    }

    protected abstract List<Widget> widgets();

    @Override
    public void draw(Screen screen) {
        screen.clear();
        List<Widget> widgets = widgets();
        for (Widget w : widgets) {
            w.draw(screen);
        }
    }

    @Override
    public TerminalRegion getRegion() {
        return region;
    }
}
