package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Window;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

import java.io.IOException;
import java.util.List;

public abstract class PopupWindow extends AbstractTuiWindow {

    public PopupWindow(StatefulMultiWindowTextGui gui) {
        super(gui); // setup is called here
        setHints(List.of(Hint.FIT_TERMINAL_WINDOW, Hint.CENTERED, Hint.MENU_POPUP));
        // TerminalSize size = gui.getScreen().getTerminalSize();
        // setFixedSize(new TerminalSize(size.getColumns() / 3, size.getRows() / 4));
        setCloseWindowWithEscape(true);
    }

    @Override
    public void show() {
        prepare();
        Window current = gui.getActiveWindow();
        if (current != null && current instanceof PopupWindow) {
            gui.removeWindow(current); // do not nest popups
        }
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
