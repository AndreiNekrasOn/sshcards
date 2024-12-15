package org.andnekon.view.tui.components;

import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Window;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Abstract so it's not initialized directly
public abstract class AbstractTuiWindow extends BasicWindow implements TuiWindow {

    protected StatefulMultiWindowTextGui gui;

    protected List<KeyStroke> buffer;

    public AbstractTuiWindow(StatefulMultiWindowTextGui gui) {
        this.gui = gui;
        this.buffer = new ArrayList<>();
        setup();
    }

    public abstract void setup();

    public void prepare() {
        // default pass
    }

    public void show() {
        prepare();
        Window current = gui.getActiveWindow();
        if (current != null) {
            gui.removeWindow(current);
        }
        gui.addWindow(this);
        gui.setCurrentWindow(this);
        gui.setActiveWindow(this);
        try {
            gui.updateScreen();
        } catch (IOException e) {
            // TODO: WTF?! SocketException: BrokenPipe, should it be handled in the
            // controller level?
            System.exit(1);
            e.printStackTrace();
        }
    }

    /** Reads one keystroke from the screens input queue, blocking */
    public String read() {
        buffer.clear();
        KeyStroke key = null;
        Screen screen = gui.getScreen();
        // can't hide cursor over telnet
        screen.setCursorPosition(new TerminalPosition(0, screen.getTerminalSize().getRows() - 1));
        try {
            // while (!KeyStrokeUtil.compareType(key, KeyType.Enter)
            //         && !KeyStrokeUtil.compareType(key, KeyType.EOF)) {
            screen.refresh();
            key = screen.readInput(); // blocks
            if (key != null) {
                buffer.add(key);
            }
            // }
        } catch (IOException e) {
            e.printStackTrace();
        }
        postRead();
        return KeyStrokeUtil.keysToString(buffer);
    }

    protected void postRead() {
        // default pass
    }
}
