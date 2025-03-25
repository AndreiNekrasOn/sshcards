package org.andnekon.view.tui.buffers;

import com.googlecode.lanterna.TerminalPosition;

import org.andnekon.utils.StringUtil;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import java.util.ArrayList;
import java.util.List;

/** Help */
public class Help extends Buffer {

    private List<Widget> widgets;

    private final int column;

    public Help(TerminalRegion region, String helpTitle) {
        super(region);
        column = region.leftCol();

        widgets = new ArrayList<>();
        widgets.add(new SingleLine(helpTitle, region.getTopLeft()));
        addSingle("-".repeat(helpTitle.length()));
    }

    @Override
    protected List<Widget> widgets() {
        return this.widgets;
    }

    private int lastRow() {
        return widgets.get(widgets.size() - 1).getRegion().botRow();
    }

    public void addSingle(String line) {
        widgets.add(new SingleLine(line, new TerminalPosition(column, lastRow() + 1)));
    }

    public void addMultiline(String line, int width) {
        line = StringUtil.wrap(line, width);
        widgets.add(new MultiLine(column, lastRow(), line));
    }
}
