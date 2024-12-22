package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Window;

import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.io.IOException;

// Abstract so it's not initialized directly
public abstract class AbstractTuiWindow extends BasicWindow implements TuiWindow {

    protected StatefulMultiWindowTextGui gui;

    public AbstractTuiWindow(StatefulMultiWindowTextGui gui) {
        this.gui = gui;
        setup();
    }

    public abstract void setup();

    public void prepare() {
        // default pass
    }

    /**
     * Default implementation, that removes the current active window and adds this to the gui<br>
     * BUG: catches SocketException
     */
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
    @Override
    public String read() {
        gui.getReader().readKeys();
        postRead();
        return KeyStrokeUtil.keysToString(gui.getReader().getBuffer());
    }

    protected void postRead() {
        // default pass
    }
}
