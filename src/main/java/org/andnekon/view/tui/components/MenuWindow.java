package org.andnekon.view.tui.components;

import com.googlecode.lanterna.gui2.ActionListBox;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.view.tui.StatefulMultiWindowTextGui;

public class MenuWindow extends AbstractTuiWindow {

    public MenuWindow(final StatefulMultiWindowTextGui gui) {
        super(gui);
    }

    @Override
    public void prepare() {}

    @Override
    public void setup() {
        final Panel content = new Panel();
        final ActionListBox menu = new ActionListBox();
        // TODO: resolve text -> action in GameLogic, fire events
        // to TuiView, which will pass them to the controller through `read`?
        // Or maybe controller itself listens to the events, TuiView just passes them through.
        menu.addItem("1. Start", () -> {});
        menu.addItem("2. Continue", () -> {});
        menu.addItem("3. About", () -> {});
        menu.addItem("4. Quit", () -> {});
        content.addComponent(menu);
    }
}
