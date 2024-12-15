package org.andnekon.view.tui.components;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.Window;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.io.IOException;

public abstract class PopupWindow extends AbstractTuiWindow {

    public PopupWindow(StatefulMultiWindowTextGui gui) {
        super(gui);
        TerminalSize size = gui.getScreen().getTerminalSize();
        setFixedSize(new TerminalSize(size.getColumns() / 4, size.getRows() / 4));
        setCloseWindowWithEscape(true);
    }

    @Override
    public void show() {
        prepare();
        Window current = gui.getActiveWindow();
        if (current != null && current instanceof PopupWindow) {
            gui.removeWindow(current); // do not nest popups
        }
        // TODO: copypaste
        gui.addWindow(this);
        gui.setCurrentWindow(this);
        gui.setActiveWindow(this);
        try {
            gui.updateScreen();
        } catch (IOException e) {
            System.exit(1);
            e.printStackTrace();
        }
    }
}
