package org.andnekon.view.tui.buffers;

import java.util.ArrayList;
import java.util.List;

import org.andnekon.view.tui.Buffer;
import org.andnekon.view.tui.Widget;
import org.andnekon.view.tui.widgets.SingleLineWidget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * WelcomeBuffer
 */
public class WelcomeBuffer extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public WelcomeBuffer(TerminalSize size) {
        super(TerminalPosition.TOP_LEFT_CORNER,
                new TerminalPosition(size.getColumns(), size.getRows()));
        String title = "SSH Heroes (temporary title)";;
        this.widgets.add(new SingleLineWidget(title, TerminalPosition.TOP_LEFT_CORNER));
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }

}
