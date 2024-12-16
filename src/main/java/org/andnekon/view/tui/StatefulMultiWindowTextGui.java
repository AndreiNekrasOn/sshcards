package org.andnekon.view.tui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.components.TuiWindow;

// TODO: smells bad
public class StatefulMultiWindowTextGui extends MultiWindowTextGUI {

    private TuiWindow currentWindow;

    public StatefulMultiWindowTextGui(Screen screen) {
        super(screen);
    }

    public TuiWindow getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(TuiWindow currentWindow) {
        this.currentWindow = currentWindow;
    }
}
