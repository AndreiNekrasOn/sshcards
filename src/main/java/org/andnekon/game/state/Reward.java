package org.andnekon.game.state;

import org.andnekon.game.GameAction;
import org.andnekon.game.GameSession;

public class Reward extends State {

    public Reward(GameSession session) {
        super(session);
    }

    @Override
    public State handleInput(GameAction action) {
        if (action.action() != GameAction.Type.NAVIGATION) {
            throw new UnsupportedOperationException("Wrong input for Reward state");
        }
        return new Navigation(session);
    }

    @Override
    protected void setType() {
        this.type = State.Type.REWARD;
    }
}
