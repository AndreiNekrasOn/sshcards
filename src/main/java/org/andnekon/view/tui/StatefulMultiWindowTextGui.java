package org.andnekon.view.tui;

import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.screen.Screen;

import org.andnekon.view.tui.components.AbstractTuiWindow;

// TODO: smells bad
public class StatefulMultiWindowTextGui extends MultiWindowTextGUI {

    private AbstractTuiWindow currentWindow;

    public StatefulMultiWindowTextGui(Screen screen) {
        super(screen);
    }

    public AbstractTuiWindow getCurrentWindow() {
        return currentWindow;
    }

    public void setCurrentWindow(AbstractTuiWindow currentWindow) {
        this.currentWindow = currentWindow;
    }
}
