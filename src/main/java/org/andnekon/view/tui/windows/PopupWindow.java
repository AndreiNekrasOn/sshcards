package org.andnekon.view.tui.windows;

import com.googlecode.lanterna.gui2.Window;

import java.io.IOException;
import java.util.List;

public abstract class PopupWindow extends AbstractTuiWindow {

    public PopupWindow() {
        super(); // setup is called here
        setHints(List.of(Hint.FIT_TERMINAL_WINDOW, Hint.CENTERED, Hint.MENU_POPUP));
        // TerminalSize size = gui.getScreen().getTerminalSize();
        // setFixedSize(new TerminalSize(size.getColumns() / 3, size.getRows() / 4));
        setCloseWindowWithEscape(true);
    }

    @Override
    public void show() {
        prepare();
    }
}
