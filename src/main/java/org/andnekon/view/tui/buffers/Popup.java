package org.andnekon.view.tui.buffers;

import java.io.IOException;
import java.util.List;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Empty;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.screen.Screen;

/**
 * Popup is a buffer that clears screen only in its' region
 */
public abstract class Popup extends Buffer {

    public Popup(TerminalRegion region) {
        super(region);
    }

    @Override
    public void draw(Screen screen) {
        Widget clear = new Empty(region);
        clear.draw(screen);
        List<Widget> widgets = widgets();
        for (Widget w : widgets) {
            w.draw(screen);
        }
    }

}
