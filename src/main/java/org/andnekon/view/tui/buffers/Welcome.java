package org.andnekon.view.tui.buffers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.andnekon.view.tui.AsciiReaderService;
import org.andnekon.view.tui.TerminalRegion;
import org.andnekon.view.tui.widgets.Border;
import org.andnekon.view.tui.widgets.MultiLine;
import org.andnekon.view.tui.widgets.SingleLine;
import org.andnekon.view.tui.widgets.Widget;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;

/**
 * WelcomeBuffer
 */
public class Welcome extends Buffer {

    private List<Widget> widgets = new ArrayList<>();

    public Welcome(TerminalSize size, AsciiReaderService asciiReaderService) {
        super(new TerminalRegion(0, 0, size.getColumns(), size.getRows()));
        String line = "<ERROR>";
        try {
            line = asciiReaderService.readFile("tui/logo.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Widget ascii = new MultiLine(size.getColumns() / 3, size.getRows() / 4, line);
        this.widgets.add(new Border(ascii));
    }

    @Override
    protected List<Widget> widgets() {
        return widgets;
    }

}
