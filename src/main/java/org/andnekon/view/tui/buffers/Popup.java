package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Empty;
import org.andnekon.view.tui.widgets.Widget;

import java.util.List;

/** Popup is a buffer that clears screen only in its' region */
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
