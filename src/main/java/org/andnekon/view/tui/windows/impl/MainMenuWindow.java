package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.ChoicesWindow;

public class MainMenuWindow extends ChoicesWindow {

    public MainMenuWindow(final StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    public void setup() {
        super.setup();
        final Panel content = new Panel();
        content.addComponent(menu);
        this.setComponent(content);
    }

    @Override
    protected void setMenuOptions() {
        this.options = new String[] {"Start", "Continue", "About", "Quit"};
    }
}
