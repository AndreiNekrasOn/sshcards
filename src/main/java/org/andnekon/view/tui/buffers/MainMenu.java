package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.SelectList;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

/** MainMenu */
public class MainMenu extends Buffer {
    private List<Widget> widgets = new ArrayList<>();

    public MainMenu(TerminalSize size) {
        super(new TerminalRegion(0, 0, size.getColumns(), size.getRows()));
        Widget title =
                new SingleLine(
                        "Menu", new TerminalPosition(size.getColumns() / 2, size.getRows() / 3));
        Widget borderWrap = new Border(title);

        // Widget menuList = new SelectList(borderWrap.getRegion().botRightRow(),
        // title.getRegion().topLeftCol(), new String[]{
        //     "1. Hello",
        //     "2. Goodbye"
        // });
        Widget menuList =
                new SelectList(
                        title.getRegion().leftCol(),
                        borderWrap.getRegion().botRow() + 1,
                        new String[] {
                            "1. New game", "4. Quit",
                        });
        Widget borderMenu = new Border(menuList);
        this.widgets.add(borderWrap);
        this.widgets.add(borderMenu);
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }
}
