package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Widget;

import java.util.List;

/** TabGroup */
public class TabGroup implements Widget {

    private int tab;

    private List<Widget> buffers;

    public TabGroup(List<Widget> widgets) {
        this.buffers = widgets;
    }

    public TabGroup(Widget... widgets) {
        this.buffers = List.of(widgets);
    }

    @Override
    public void draw(Screen screen) {
        screen.clear(); // TODO: is this the right place to do it?
        this.buffers.get(tab).draw(screen);
    }

    @Override
    public TerminalRegion getRegion() {
        return buffers.get(tab).getRegion();
    }

    public boolean isCurrentChildTabbed() {
        return this.buffers.get(tab) instanceof TabGroup;
    }

    public TabGroup at(int tab) {
        this.tab = tab;
        return this;
    }
}
