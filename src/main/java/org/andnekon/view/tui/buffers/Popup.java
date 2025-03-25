package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.Empty;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import java.util.List;

/** Popup is a buffer that clears screen only in its' region */
public class Popup extends Buffer {

    private String text;

    public Popup(TerminalRegion region) {
        super(region);
    }

    public Popup(TerminalRegion region, String text) {
        super(region);
        this.text = text;
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

    @Override
    protected List<Widget> widgets() {
        Widget empty = new Empty(region);
        Widget popup = new SingleLine(text, region.getTopLeft());
        popup = new Border(popup);
        return List.of(empty, popup);
    }
}
