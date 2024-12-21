package org.andnekon.view.tui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.windows.TuiWindow;

// TODO: smells bad
public class StatefulMultiWindowTextGui extends MultiWindowTextGUI {

    private TuiWindow currentWindow;

    private TuiManager manager;

    public StatefulMultiWindowTextGui(Screen screen, TuiManager manager) {
        super(screen);
        this.manager = manager;
    }

    public TuiWindow getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(TuiWindow currentWindow) {
        this.currentWindow = currentWindow;
    }

    public TuiReader getReader() {
        return this.manager.getReader();
    }
}
