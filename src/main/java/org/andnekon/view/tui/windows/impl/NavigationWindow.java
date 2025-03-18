package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;

import org.andnekon.game.GameSession;
import org.andnekon.utils.KeyStrokeUtil;
import org.andnekon.view.tui.windows.MainWindow;
import org.andnekon.view.tui.windows.MenuComponent;

import java.util.List;

public class NavigationWindow extends MainWindow {

    private GameSession session;

    private MenuComponent menu;

    private Panel emptyContent;

    public NavigationWindow(GameSession session) {
        super();
        this.session = session;
    }

    @Override
    public void setup() {
        content = new Panel();

        Label prompt = new Label("Where do you go?");
        content.addComponent(prompt);

        menu = new MenuComponent();
        content.addComponent(menu);

        emptyContent = new Panel();
    }

    @Override
    protected void postRead() {
        // List<KeyStroke> buffer = gui.getReader().getBuffer();
        // if (buffer == null || buffer.isEmpty()) {
        //     return;
        // }
        // KeyStroke key = buffer.get(0);
        // if (KeyStrokeUtil.compareType(key, KeyType.Tab)) {
        //     if (getComponent().equals(container)) {
        //         setComponent(emptyContent);
        //     } else {
        //         setComponent(container);
        //     }
        //     buffer.clear();
        // }
        // menu.processInput(gui.getReader().getBuffer());
    }

    @Override
    public void prepare() {
        menu.prepare(session.getNavigationManager().getNavigationOptionsArray());
    }
}
