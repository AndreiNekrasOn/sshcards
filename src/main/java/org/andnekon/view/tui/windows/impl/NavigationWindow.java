package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.MainWindow;
import org.andnekon.view.tui.windows.MenuComponent;

public class NavigationWindow extends MainWindow {

    private GameSession session;

    private MenuComponent menu;

    public NavigationWindow(StatefulMultiWindowTextGui gui, GameSession session) {
        super(gui);
        this.session = session;
    }

    @Override
    public void setup() {
        content = new Panel();

        Label prompt = new Label("Where do you go?");
        content.addComponent(prompt);

        menu = new MenuComponent();
        content.addComponent(menu);
    }

    @Override
    protected void postRead() {
        menu.processInput(gui.getReader().getBuffer());
    }

    @Override
    public void prepare() {
        menu.prepare(session.getEnemyNavLeft().toString(), session.getEnemyNavRight().toString());
    }
}
