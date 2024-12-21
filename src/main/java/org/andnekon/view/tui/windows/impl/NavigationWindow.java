package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.AbstractTuiWindow;
import org.andnekon.view.tui.windows.MenuComponent;

public class NavigationWindow extends AbstractTuiWindow {

    private GameSession session;

    private MenuComponent menu;

    public NavigationWindow(StatefulMultiWindowTextGui gui, GameSession session) {
        super(gui);
        this.session = session;
    }

    @Override
    public void setup() {
        Panel contnet = new Panel();

        Label prompt = new Label("Where do you go?");
        contnet.addComponent(prompt);

        menu = new MenuComponent();
        contnet.addComponent(menu);

        this.setComponent(contnet);
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
