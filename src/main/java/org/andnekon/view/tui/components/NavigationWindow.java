package org.andnekon.view.tui.components;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.input.KeyStroke;

import org.andnekon.game.GameSession;
import org.andnekon.utils.KeyStrokeUtil;
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
        leftEnemy.setText("1. " + session.getEnemyNavLeft().display());
        rightEnemy.setText("2. " + session.getEnemyNavRight().display());
    }

    @Override
    public void setup() {
        Panel contnet = new Panel();

        Panel choises = new Panel();
        contnet.addComponent(choises);

        Label prompt = new Label("Where do you go?");
        contnet.addComponent(prompt);

        leftEnemy = new Label("");
        choises.addComponent(leftEnemy);
        leftEnemy.addStyle(SGR.BOLD);
        rightEnemy = new Label("");
        choises.addComponent(rightEnemy);

        this.setComponent(contnet);
    }

    @Override
    protected void postRead() {
        for (KeyStroke key : buffer) {
            if (KeyStrokeUtil.isLeftMotion(key) || KeyStrokeUtil.isUpMotion(key)) {
                leftEnemy.addStyle(SGR.BOLD);
                rightEnemy.removeStyle(SGR.BOLD);
            } else if (KeyStrokeUtil.isRightMotion(key) || KeyStrokeUtil.isDownMotion(key)) {
                leftEnemy.removeStyle(SGR.BOLD);
                rightEnemy.addStyle(SGR.BOLD);
            }
        }
    }
}
