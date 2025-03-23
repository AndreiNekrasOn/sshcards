package org.andnekon.view.tui.buffers;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.SelectList;
import org.andnekon.view.tui.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

/** Navigation */
public class Navigation extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Navigation(TerminalRegion region, String[] options) {
        super(region);
        this.widgets.add(new SelectList(1, 1, options));
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }
}
