package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * WelcomeBuffer
 */
public class Welcome extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Welcome(TerminalSize size) {
        super(new TerminalRegion(0, 0, size.getColumns(), size.getRows()));
        String title = "SSH Heroes (temporary title)";;
        this.widgets.add(new SingleLine(title, TerminalPosition.TOP_LEFT_CORNER));
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }

}
