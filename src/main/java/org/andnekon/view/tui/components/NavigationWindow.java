package org.andnekon.view.tui.components;

import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;

import org.andnekon.game.GameSession;
import org.andnekon.view.tui.StatefulMultiWindowTextGui;

public class NavigationWindow extends AbstractTuiWindow {

    private GameSession session;

    Label leftEnemy;
    Label rightEnemy;

    public NavigationWindow(StatefulMultiWindowTextGui gui, GameSession session) {
        super(gui);
        this.session = session;
    }

    @Override
    public void prepare() {
        leftEnemy.setText(session.getEnemyNavLeft().display());
        rightEnemy.setText(session.getEnemyNavRight().display());
    }

    @Override
    public void setup() {
        Panel contnet = new Panel(new GridLayout(2));

        Panel choises = new Panel(new GridLayout(2));
        contnet.addComponent(choises);

        leftEnemy = new Label("");
        choises.addComponent(leftEnemy);
        rightEnemy = new Label("");
        choises.addComponent(rightEnemy);

        Label prompt = new Label("Where do you go?");
        contnet.addComponent(prompt);
    }
}
