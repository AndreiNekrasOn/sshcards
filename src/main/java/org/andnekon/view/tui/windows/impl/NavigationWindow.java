package org.andnekon.view.tui.windows.impl;

import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;
import org.andnekon.view.tui.windows.ChoicesWindow;

public class NavigationWindow extends ChoicesWindow {

    private GameSession session;

    public NavigationWindow(StatefulMultiWindowTextGui gui, GameSession session) {
        super(gui);
        this.session = session;
    }

    @Override
    public void setup() {
        super.setup();
        Panel contnet = new Panel();

        Label prompt = new Label("Where do you go?");
        contnet.addComponent(prompt);

        contnet.addComponent(menu);

        this.setComponent(contnet);
    }

    @Override
    protected void setMenuOptions() {
        this.options =
                new String[] {
                    session.getEnemyNavLeft().toString(), session.getEnemyNavRight().toString()
                };
    }
}
