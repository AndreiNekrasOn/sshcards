package org.andnekon.view.tui.components;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Window;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.io.IOException;

// Abstract so it's not initialized directly
public abstract class AbstractTuiWindow extends BasicWindow implements TuiWindow {

    protected StatefulMultiWindowTextGui gui;

    protected StringBuilder buffer;

    public AbstractTuiWindow(StatefulMultiWindowTextGui gui) {
        this.gui = gui;
        this.buffer = new StringBuilder();
        setup();
    }

    public abstract void setup();

    // If the child doesn't need anything prepared it will leave this empty
    public abstract void prepare();

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
}
