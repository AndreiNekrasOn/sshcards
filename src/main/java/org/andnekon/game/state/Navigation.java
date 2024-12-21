package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.entity.enemy.Enemy;
import org.andnekon.view.HelpType;

public class Navigation extends State {

    public Navigation(GameSession session) {
        super(session);
        if (!session.isNavigationInit()) {
            session.initNavigation();
        }
    }

    @Override
    public State handleInput(GameAction action) {
        if (action.action() != GameAction.Type.NAVIGATION) {
            throw new UnsupportedOperationException("Wrong action for Navigation state");
        }
        Enemy firstEnemy = session.getEnemyNavLeft();
        Enemy secondEnemy = session.getEnemyNavRight();
        switch (action.id()) {
            case 1:
                session.setEnemy(firstEnemy);
                break;
            case 2:
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
