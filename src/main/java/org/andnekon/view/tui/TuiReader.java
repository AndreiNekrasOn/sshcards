package org.andnekon.view.tui;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.Reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TuiReader implements Reader {

    private List<KeyStroke> buffer;
    private TuiManager manager;

    public TuiReader(TuiManager manager) {
        this.buffer = new ArrayList<>();
        this.manager = manager;
    }

    @Override
    public String read() {
        String result = this.manager.getScreenName();
        this.manager.processSpecialInput(result);
        return result;
    }

    /**
     * Interactively reads key strokes from the screen, storring the result in the internal buffer
     */
    public void readKeys() {
        buffer.clear();
        Screen screen = this.manager.getScreen();
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
