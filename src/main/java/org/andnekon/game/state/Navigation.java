package org.andnekon.game.state;

import org.andnekon.game.GameSession;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.ui.HelpType;

public class Navigation extends State {

    public Navigation(GameSession session) {
        super(session);
        if (!session.isNavigationInit()) {
            session.initNavigation();
        }
    }

    @Override
    public State handleInput(String input) {
        Enemy firstEnemy = session.getEnemyNavLeft();
        Enemy secondEnemy = session.getEnemyNavRight();
        switch (input) {
            case "1":
                session.setEnemy(firstEnemy);
                break;
            case "2":
                session.setEnemy(secondEnemy);
                break;
            default:
                session.setHelpType(HelpType.WRONG_INPUT);
                return this;
        }
        session.setNavigationInit(false);
        return new Battle(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.NAVIGATION;
    }
}

