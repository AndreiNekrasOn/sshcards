package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.AbstractTuiWindow;
import org.andnekon.view.tui.windows.MenuComponent;

public class MainMenuWindow extends AbstractTuiWindow {

    private MenuComponent menu;

    public MainMenuWindow(final StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    protected void postRead() {
        menu.processInput(gui.getReader().getBuffer());
    }

    @Override
    public void prepare() {
        menu.prepare("Start", "Continue", "About", "Quit");
    }

    @Override
    public void setup() {
        final Panel content = new Panel();
        menu = new MenuComponent();
        content.addComponent(menu);
        this.setComponent(content);
    }
}
