package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * MainMenu
 */
public class MainMenu extends Buffer {
    private List<Widget> widgets = new ArrayList<>();

    public MainMenu(TerminalSize size) {
        super(new TerminalRegion(0, 0, size.getColumns(), size.getRows()));
        Widget title = new SingleLine("Menu",
                new TerminalPosition(size.getColumns() / 2, size.getRows() / 3));
        Widget borderWrap = new Border(title);
        this.widgets.add(borderWrap);
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }



}
