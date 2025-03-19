package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.view.tui.Buffer;
import org.andnekon.view.tui.Widget;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.SingleLineWidget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * MainMenu
 */
public class MainMenu extends Buffer {
    private List<Widget> widgets = new ArrayList<>();

    public MainMenu(TerminalSize size) {
        super(TerminalPosition.TOP_LEFT_CORNER,
                new TerminalPosition(size.getColumns(), size.getRows()));
        Widget title = new SingleLineWidget("Menu",
                new TerminalPosition(size.getColumns() / 2, size.getRows() / 3));
        Widget borderWrap = new Border(title);
        this.widgets.add(borderWrap);
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }



}
