package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TuiReader implements Reader {

    private List<KeyStroke> buffer;
    private TuiManager manager;

    public TuiReader(TuiManager manager) {
        this.buffer = new ArrayList<>();
        this.manager = manager;
    }

    @Override
    public String read() {
        String result = this.manager.getGui().getCurrentWindow().read();
        if (Objects.equals(result, "?")) {
            this.manager.getView().setHelpShown(true);
            return null;
        } else if (this.manager.getView().isHelpShown()) {
            this.manager.getView().setHelpShown(false);
            return null;
        }
        if (Objects.equals(result, "r")) {
            this.manager.getView().setRefresh();
            return null;
        }
        return result;
    }

    /**
     * Interactively reads key strokes from the screen, storring the result in the internal buffer
     */
    public void readKeys() {
        buffer.clear();
        Screen screen = this.manager.getGui().getScreen();
        // can't hide cursor over telnet
        screen.setCursorPosition(new TerminalPosition(0, screen.getTerminalSize().getRows() - 1));
        try {
            screen.refresh();
            KeyStroke key = screen.readInput(); // blocks
            if (key != null) {
                buffer.add(key);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<KeyStroke> getBuffer() {
        return this.buffer;
    }
}
