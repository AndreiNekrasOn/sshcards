package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;
import org.andnekon.game.entity.Combat;
import org.andnekon.view.HelpType;

import java.util.List;

public class Navigation extends State {

    public Navigation(GameSession session) {
        super(session);
        if (!session.getNavigationManager().isInit()) {
            session.getNavigationManager().init();
        }
    }

    @Override
    public State handleInput(GameAction action) {
        if (action.action() != GameAction.Type.NAVIGATION) {
            throw new UnsupportedOperationException("Wrong action for Navigation state");
        }
        int actionId = action.id() - 1;
        List<Combat> navigationOptions = session.getNavigationManager().getNavigationOptions();
        if (actionId < 0 || actionId >= navigationOptions.size()) {
            session.setHelpType(HelpType.WRONG_INPUT);
            return this;
        }
        session.getBattleManager().setEnemies(navigationOptions.get(actionId).getEnemies());
        session.getNavigationManager().setInit(false);
        return new Battle(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.NAVIGATION;
    }
}
